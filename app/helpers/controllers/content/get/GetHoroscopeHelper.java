package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.TranslateHelper;
import helpers.controllers.Response;
import helpers.controllers.content.AbstractJsonControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 23.10.2014.
 */
final class GetHoroscopeHelper extends AbstractJsonControllerHelper {

    private static final Logger.ALogger LOG = Logger.of(GetHoroscopeHelper.class);
    private static final String DEFAULT_LOCALE = "ru";

    private static final String GET_HOROSCOPE_SQL = "SELECT DISTINCT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=?";
    private static final String GET_HOROSCOPE_PERIOD_SQL = "SELECT DISTINCT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.period=?";
    private static final String TRANSLATE_HOROSCOPE_SQL = "SELECT DISTINCT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t1.period not in (SELECT t1.period FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=?)";
    private static final String TRANSLATE_HOROSCOPE_PERIOD_SQL = "SELECT  DISTINCT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t1.period not in (SELECT t1.period FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.period=?) and t2.period=?";
    private static final String INSERT_TRANSLATED_HOROSCOPE = "INSERT INTO horo_texts" +
            " (type, kind, sign, period, locale, content) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_KIND = "kind";
    private static final String PARAM_SIGN = "sign";
    private static final String PARAM_LOCALE = "locale";

    private String period;

    /**
     * Constructor.
     *
     * @param json Json
     */
    public GetHoroscopeHelper(final JsonNode json) {
        super(json);
    }

    /**
     * Getter for period.
     *
     * @return Period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Setter for period.
     *
     * @param period Period
     */
    public void setPeriod(final String period) {
        this.period = period;
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final JsonNode json) {
        Result result;
        try {
            final JsonNode response = DatabaseHelper.actionWithDatabase(new DatabaseHelper.ConnectionAction<JsonNode>() {
                public JsonNode onAction(Connection statement) throws SQLException {
                    return GetHoroscopeHelper.this.internalAction(statement, json);
                }
            });
            result = Controller.ok(response);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(Response.error(e).asJson());
        }
        return result;
    }

    private void translateAll(final Connection connection, final JsonNode json) throws SQLException {
        final String locale = json.has(PARAM_LOCALE) ? json.get(PARAM_LOCALE).textValue() : DEFAULT_LOCALE;
        if (DEFAULT_LOCALE.equals(locale)) {
            return;
        }

        final String sql = this.period == null ? TRANSLATE_HOROSCOPE_SQL : TRANSLATE_HOROSCOPE_PERIOD_SQL;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, json.get(PARAM_TYPE).textValue());
            statement.setString(2, json.get(PARAM_KIND).textValue());
            statement.setString(3, json.get(PARAM_SIGN).textValue());
            statement.setString(4, DEFAULT_LOCALE);
            statement.setString(5, json.get(PARAM_TYPE).textValue());
            statement.setString(6, json.get(PARAM_KIND).textValue());
            statement.setString(7, json.get(PARAM_SIGN).textValue());
            statement.setString(8, locale);
            if (this.period != null) {
                statement.setString(9, this.period);
                statement.setString(10, this.period);
            }
            final ResultSet resultSet = statement.executeQuery();

            final List<String> periods = new ArrayList<>();
            final List<String> contents = new ArrayList<>();
            while (resultSet.next()) {
                periods.add(resultSet.getString(1));
                contents.add(resultSet.getString(2));
            }

            if (!contents.isEmpty()) {
                final TranslateHelper helper = new TranslateHelper();
                helper.setTarget(locale);
                final List<String> translatedContents = helper.translateAll(contents);

                try (PreparedStatement insert = connection.prepareStatement(INSERT_TRANSLATED_HOROSCOPE)) {
                    final int count = translatedContents.size();
                    for (int i = 0; i < count; i++) {
                        insert.clearParameters();
                        insert.setString(1, json.get(PARAM_TYPE).textValue());
                        insert.setString(2, json.get(PARAM_KIND).textValue());
                        insert.setString(3, json.get(PARAM_SIGN).textValue());
                        insert.setString(4, periods.get(i));
                        insert.setString(5, locale);
                        insert.setString(6, translatedContents.get(i));
                        insert.execute();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode internalAction(final Connection connection, final JsonNode json) throws SQLException {
        connection.setAutoCommit(false);
        try {
            this.translateAll(connection, json);

            final String sql = this.period == null ? GET_HOROSCOPE_SQL : GET_HOROSCOPE_PERIOD_SQL;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                return this.internalAction(statement, json);
            }
        } finally {
            connection.commit();
        }
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_TYPE).textValue());
        statement.setString(2, json.get(PARAM_KIND).textValue());
        statement.setString(3, json.get(PARAM_SIGN).textValue());
        statement.setString(4, json.has(PARAM_LOCALE) ? json.get(PARAM_LOCALE).textValue() : DEFAULT_LOCALE);
        if (this.period != null) {
            statement.setString(5, this.period);
        }
        final ResultSet resultSet = statement.executeQuery();

        final ObjectNode sign = Json.newObject();
        while (resultSet.next()) {
            sign.put(resultSet.getString(1), resultSet.getString(2));
        }
        final ObjectNode kind = Json.newObject();
        kind.put(json.get(PARAM_SIGN).textValue(), sign);
        final ObjectNode type = Json.newObject();
        type.put(json.get(PARAM_KIND).textValue(), kind);
        final ObjectNode content = Json.newObject();
        content.put(json.get(PARAM_TYPE).textValue(), type);
        return Response.content(content).asJson();
    }

    /**
     * {@inheritDoc}
     */
    protected String[] getNotNullFields() {
        return new String[]{PARAM_TYPE, PARAM_KIND, PARAM_SIGN};
    }
}
