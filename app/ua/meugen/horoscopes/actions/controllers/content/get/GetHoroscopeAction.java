package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
final class GetHoroscopeAction extends TranslateHoroscopesAction {

    private static final Logger.ALogger LOG = Logger.of(GetHoroscopeAction.class);
    private static final int DEFAULT_VERSION = 1;

    private static final String GET_HOROSCOPE_SQL = "SELECT DISTINCT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=?";
    private static final String GET_HOROSCOPE_PERIOD_SQL = "SELECT DISTINCT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=? and t2.period=?";
    private static final String TRANSLATE_HOROSCOPE_SQL = "SELECT DISTINCT t1.type, t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=? and t1.period not in (SELECT t1.period FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=?)";
    private static final String TRANSLATE_HOROSCOPE_PERIOD_SQL = "SELECT  DISTINCT t1.type, t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=? and t1.period not in (SELECT t1.period FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=? and t2.period=?) and t2.period=?";
    private static final String PARAM_VERSION = "version";
    private static final String PARAM_PERIODS = "periods";

    private String period;

    /**
     * Constructor.
     *
     * @param json Json
     */
    public GetHoroscopeAction(final JsonNode json) {
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
            final ArrayNode periods = (ArrayNode) json.get("periods");

            StringBuilder builder = null;
            if (periods != null && periods.size() > 0) {
                builder = new StringBuilder(" AND (1=0");
                for (JsonNode period : periods) {
                    builder.append(String.format(" OR t1.period='%s'", period.asText()));
                }
                builder.append(")");
            }
            final String where = builder == null ? "" : builder.toString();

            translateAll(json, where);
            final JsonNode response = DatabaseHelper.actionWithDatabase((connection) ->
                    internalAction(connection, json, where));
            result = Controller.ok(response);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(BaseResponse.error(e).asJson());
        }
        return result;
    }

    private void translateAll(final JsonNode json, final String where) throws SQLException {
        final String sql = this.period == null ? TRANSLATE_HOROSCOPE_SQL : TRANSLATE_HOROSCOPE_PERIOD_SQL;
        DatabaseHelper.actionWithDatabase((connection) -> translateAll(connection, sql + where, json));
    }

    /**
     * {@inheritDoc}
     */
    protected void bindStatement(final PreparedStatement statement, final JsonNode json,
                                 final String locale) throws SQLException {
        final int version = json.has(PARAM_VERSION) ? json.get(PARAM_VERSION).asInt() : DEFAULT_VERSION;
        statement.setString(1, json.get(PARAM_TYPE).textValue());
        statement.setString(2, json.get(PARAM_KIND).textValue());
        statement.setString(3, json.get(PARAM_SIGN).textValue());
        statement.setString(4, DEFAULT_LOCALE);
        statement.setInt(5, version);
        statement.setString(6, json.get(PARAM_TYPE).textValue());
        statement.setString(7, json.get(PARAM_KIND).textValue());
        statement.setString(8, json.get(PARAM_SIGN).textValue());
        statement.setString(9, locale);
        statement.setInt(10, version);
        if (this.period != null) {
            statement.setString(11, this.period);
            statement.setString(12, this.period);
        }
    }

    private JsonNode internalAction(final Connection connection, final JsonNode json,
                                    final String where) throws SQLException {
        final String sql = this.period == null ? GET_HOROSCOPE_SQL : GET_HOROSCOPE_PERIOD_SQL;
        try (PreparedStatement statement = connection.prepareStatement(sql + where)) {
            return this.internalAction(statement, json);
        }
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_TYPE).textValue());
        statement.setString(2, json.get(PARAM_KIND).textValue());
        statement.setString(3, json.get(PARAM_SIGN).textValue());
        statement.setString(4, this.getLocale(json));
        statement.setInt(5, json.has(PARAM_VERSION) ? json.get(PARAM_VERSION).asInt() : DEFAULT_VERSION);
        if (this.period != null) {
            statement.setString(6, this.period);
        }

        try (ResultSet resultSet = statement.executeQuery()) {
            final ObjectNode sign = Json.newObject();
            while (resultSet.next()) {
                sign.put(resultSet.getString(1), resultSet.getString(2));
            }
            final ObjectNode kind = Json.newObject();
            kind.set(json.get(PARAM_SIGN).textValue(), sign);
            final ObjectNode type = Json.newObject();
            type.set(json.get(PARAM_KIND).textValue(), kind);
            final ObjectNode content = Json.newObject();
            content.set(json.get(PARAM_TYPE).textValue(), type);
            return BaseResponse.content(content).asJson();
        }
    }

    /**
     * {@inheritDoc}
     */
    protected String[] getNotNullFields() {
        return new String[]{PARAM_TYPE, PARAM_KIND, PARAM_SIGN};
    }
}
