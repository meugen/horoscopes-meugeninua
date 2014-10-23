package controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.controllers.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public final class Search extends Controller {

    private static final Log LOG = LogFactory.getLog(Search.class);

    private static final String SEARCH_AMULET_SQL = "select type, amulet from horo_amulets_v2 where upamulet like concat(?, '%')";
    private static final String SEARCH_CHINAS_SQL = "select t1.china, t2.name, t1.period from horo_chinas_v2 t1," +
            " horo_uploads t2 where t1.icon_id=t2.id order by t1.order";
    private static final String SEARCH_DREAMS_SQL = "select type, dream from horo_dreams_v2 where updream like concat(?, '%')";
    private static final String SEARCH_DRUIDS_SQL = "select t1.druid, t2.name, t1.period from horo_druids t1," +
            " horo_uploads t2 where t1.icon_id=t2.id order by t1.order";
    private static final String SEARCH_FLOWERS_SQL = "select t1.flower, t2.name, t1.period from horo_flowers t1," +
            " horo_uploads t2 where t1.icon_id=t2.id order by t1.order";
    private static final String SEARCH_JAPANS_SQL = "select t1.japan, t2.name, t1.period from horo_japans t1," +
            " horo_uploads t2 where t1.icon_id=t2.id order by t1.order";
    private static final String SEARCH_NAMES_SQL = "select sex, name from horo_names_v2 where upname like concat(?, '%')";
    private static final String NAME_KEY = "name";
    private static final String ICON_KEY = "icon";
    private static final String PERIOD_KEY = "period";
    private static final String PARAM_SEARCH = "search";
    private static final String ITEMS_KEY = "items";
    private static final String TYPE_KEY = "type";
    private static final String AMULET_KEY = "amulet";
    private static final String DREAM_KEY = "dream";
    private static final String SEX_KEY = "sex";

    @BodyParser.Of(BodyParser.Json.class)
    public static Result amulets() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        statement.setString(1, json.get(PARAM_SEARCH).textValue().toUpperCase());
                        return ok(Response.content(fetchInterpretationContent(statement.executeQuery(),
                                TYPE_KEY, AMULET_KEY)).asJson());
                    }
                }, SEARCH_AMULET_SQL);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    public static Result chinas() {
        Result result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                public Result onAction(PreparedStatement statement) throws SQLException {
                    return ok(Response.content(fetchContent(statement.executeQuery())).asJson());
                }
            }, SEARCH_CHINAS_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result dreams() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        statement.setString(1, json.get(PARAM_SEARCH).textValue());
                        return ok(Response.content(fetchInterpretationContent(statement.executeQuery(),
                                TYPE_KEY, DREAM_KEY)).asJson());
                    }
                }, SEARCH_DREAMS_SQL);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    public static Result druids() {
        Result result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                public Result onAction(PreparedStatement statement) throws SQLException {
                    return ok(Response.content(fetchContent(statement.executeQuery())).asJson());
                }
            }, SEARCH_DRUIDS_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    public static Result flowers() {
        Result result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                public Result onAction(PreparedStatement statement) throws SQLException {
                    return ok(Response.content(fetchContent(statement.executeQuery())).asJson());
                }
            }, SEARCH_FLOWERS_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    public static Result japans() {
        Result result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                public Result onAction(PreparedStatement statement) throws SQLException {
                    return ok(Response.content(fetchContent(statement.executeQuery())).asJson());
                }
            }, SEARCH_JAPANS_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result names() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        statement.setString(1, json.get(PARAM_SEARCH).textValue().toUpperCase());
                        return ok(Response.content(fetchInterpretationContent(statement.executeQuery(),
                                SEX_KEY, NAME_KEY)).asJson());
                    }
                }, SEARCH_NAMES_SQL);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    private static ObjectNode fetchContent(final ResultSet resultSet) throws SQLException {
        final ObjectNode content = Json.newObject();
        final ArrayNode items = content.putArray(ITEMS_KEY);
        while (resultSet.next()) {
            final ObjectNode item = Json.newObject();
            item.put(NAME_KEY, resultSet.getString(1));
            item.put(ICON_KEY, resultSet.getString(2));
            item.put(PERIOD_KEY, resultSet.getString(3));
            items.add(item);
        }
        return content;
    }

    private static ObjectNode fetchInterpretationContent(final ResultSet resultSet, final String key1,
                                                         final String key2) throws SQLException {
        final ObjectNode content = Json.newObject();
        final ArrayNode items = content.putArray(ITEMS_KEY);
        while (resultSet.next()) {
            final ObjectNode item = Json.newObject();
            item.put(key1, resultSet.getInt(1));
            item.put(key2, resultSet.getString(2));
            items.add(item);
        }
        return content;
    }
}
