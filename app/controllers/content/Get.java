package controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.controllers.content.get.GetHelpersFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.F;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 02.07.14.
 */
public final class Get extends Controller {

    private static final Log LOG = LogFactory.getLog(Get.class);

    private static final String GET_HOROSCOPE_SQL = "SELECT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=?";
    private static final String GET_HOROSCOPE_PERIOD_SQL = "SELECT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t2.period=?";
    private static final String GET_NAME_SQL = "select content from horo_names_v2 where upname=?";
    private static final String GET_JAPAN_SQL = "select content from horo_japans where upjapan=?";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_KIND = "kind";
    private static final String PARAM_SIGN = "sign";
    private static final String NAME_PARAM = "name";
    private static final String PARAM_JAPAN = "japan";
    private static final String PARAM_FLOWER = "flower";
    private static final String TEXT_KEY = "text";

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> amulet() {
        return GetHelpersFactory.newGetAmuletHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> china() {
        return GetHelpersFactory.newGetChinaHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> dream() {
        return GetHelpersFactory.newGetDreamHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> druid() {
        return GetHelpersFactory.newGetDruidHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result flower() {

    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result horoscope() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        bindBaseParams(statement, json);
                        return ok(Response.content(fetchHoroscopeContent(statement.executeQuery(), json)).asJson());
                    }
                }, GET_HOROSCOPE_SQL);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result horoscopeByPeriod(final String period) {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        bindBaseParams(statement, json);
                        statement.setString(4, period);
                        return ok(Response.content(fetchHoroscopeContent(statement.executeQuery(), json)).asJson());
                    }
                }, GET_HOROSCOPE_PERIOD_SQL);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result japan() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        statement.setString(1, json.get(PARAM_JAPAN).textValue().toUpperCase());
                        return ok(Response.content(fetchContent(statement.executeQuery())).asJson());
                    }
                }, GET_JAPAN_SQL);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result name() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        statement.setString(1, json.get(NAME_PARAM).textValue().toUpperCase());
                        return ok(Response.content(fetchContent(statement.executeQuery())).asJson());
                    }
                }, GET_NAME_SQL);
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

    private static ObjectNode fetchHoroscopeContent(final ResultSet resultSet, final JsonNode json) throws SQLException {
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

    private static JsonNode fetchContent(final ResultSet resultSet) throws SQLException {
        JsonNode content = NullNode.getInstance();
        if (resultSet.next()) {
            final ObjectNode object = Json.newObject();
            object.put(TEXT_KEY, resultSet.getString(1));
            content = object;
        }
        return content;
    }
}
