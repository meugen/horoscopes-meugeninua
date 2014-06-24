package controllers.content.get;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.content.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.db.DB;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for horoscope.
 *
 * @author meugen
 */
public final class Horoscope extends Controller {

    private static final Log LOG = LogFactory.getLog(Horoscope.class);

    private static final String GET_CONTENT_SQL = "SELECT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=?";
    private static final String GET_CONTENT_PERIOD_SQL = "SELECT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t2.period=?";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_KIND = "kind";
    private static final String PARAM_SIGN = "sign";

    public static Result all() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                final PreparedStatement statement = DB.getConnection().prepareStatement(GET_CONTENT_SQL);
                bindBaseParams(statement, json);
                result = ok(Response.content(fetchContent(statement.executeQuery(), json)).asJson());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    public static Result byPeriod(final String period) {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                final PreparedStatement statement = DB.getConnection().prepareStatement(GET_CONTENT_PERIOD_SQL);
                bindBaseParams(statement, json);
                statement.setString(4, period);
                result = ok(Response.content(fetchContent(statement.executeQuery(), json)).asJson());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    private static void bindBaseParams(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_TYPE).textValue());
        statement.setString(2, json.get(PARAM_KIND).textValue());
        statement.setString(3, json.get(PARAM_SIGN).textValue());
    }

    private static ObjectNode fetchContent(final ResultSet resultSet, final JsonNode json) throws SQLException {
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
        return content;
    }
}
