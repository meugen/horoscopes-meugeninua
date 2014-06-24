package controllers.content.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.content.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.db.DB;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 24.06.14.
 */
public final class Amulets extends Controller {

    private static final Log LOG = LogFactory.getLog(Amulets.class);

    private static final String SEARCH_AMULET_SQL = "select type, amulet from horo_amulets_v2 where upamulet like concat(?, '%')";
    private static final String PARAM_SEARCH = "search";
    private static final String ITEMS_KEY = "items";
    private static final String TYPE_KEY = "type";
    private static final String AMULET_KEY = "amulet";

    public static Result index() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                final PreparedStatement statement = DB.getConnection().prepareStatement(SEARCH_AMULET_SQL);
                statement.setString(1, json.get(PARAM_SEARCH).textValue().toUpperCase());
                result = ok(Response.content(fetchContent(statement.executeQuery())).asJson());
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
            item.put(TYPE_KEY, resultSet.getInt(1));
            item.put(AMULET_KEY, resultSet.getString(2));
            items.add(item);
        }
        return content;
    }
}
