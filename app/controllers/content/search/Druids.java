package controllers.content.search;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.content.Response;
import helpers.DatabaseHelper;
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
public final class Druids extends Controller {

    private static final Log LOG = LogFactory.getLog(Druids.class);

    private static final String SEARCH_DRUIDS_SQL = "select t1.druid, t2.name, t1.period from horo_druids t1," +
            " horo_uploads t2 where t1.icon_id=t2.id order by t1.order";
    private static final String ITEMS_KEY = "items";
    private static final String NAME_KEY = "name";
    private static final String ICON_KEY = "icon";
    private static final String PERIOD_KEY = "period";

    public static Result index() {
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
}
