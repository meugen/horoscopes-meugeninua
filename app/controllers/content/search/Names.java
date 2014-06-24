package controllers.content.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.content.Response;
import helpers.DatabaseHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 24.06.14.
 */
public final class Names extends Controller {

    private static final Log LOG = LogFactory.getLog(Names.class);

    private static final String SEARCH_NAMES_SQL = "select sex, name from horo_names_v2 where upname like concat(?, '%')";
    private static final String PARAM_SEARCH = "search";
    private static final String ITEMS_KEY = "items";
    private static final String SEX_KEY = "sex";
    private static final String NAME_KEY = "name";

    public static Result index() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        statement.setString(1, json.get(PARAM_SEARCH).textValue().toUpperCase());
                        return ok(Response.content(fetchContent(statement.executeQuery())).asJson());
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
            item.put(SEX_KEY, resultSet.getInt(1));
            item.put(NAME_KEY, resultSet.getString(2));
            items.add(item);
        }
        return content;
    }
}
