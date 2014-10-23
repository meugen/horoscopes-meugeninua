package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.content.Response;
import helpers.DatabaseHelper;
import helpers.controllers.content.AbstractJsonControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
public final class GetHoroscopeHelper extends AbstractJsonControllerHelper {

    private static final Log LOG = LogFactory.getLog(GetHoroscopeHelper.class);

    private static final String GET_HOROSCOPE_SQL = "SELECT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=?";
    private static final String GET_HOROSCOPE_PERIOD_SQL = "SELECT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t2.period=?";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_KIND = "kind";
    private static final String PARAM_SIGN = "sign";
    private static final String TEXT_KEY = "text";

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
    protected JsonNode action(final JsonNode json) {
        JsonNode result;
        try {
            final String sql = this.period == null ? GET_HOROSCOPE_SQL : GET_HOROSCOPE_PERIOD_SQL;
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(PreparedStatement statement) throws SQLException {
                    return GetHoroscopeHelper.this.internalAction(statement, json);
                }
            }, sql);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Response.error(e).asJson();
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_TYPE).textValue());
        statement.setString(2, json.get(PARAM_KIND).textValue());
        statement.setString(3, json.get(PARAM_SIGN).textValue());
        if (this.period != null) {
            statement.setString(4, this.period);
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
