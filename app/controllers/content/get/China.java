package controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
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
public final class China extends Controller {

    private static final Log LOG = LogFactory.getLog(China.class);

    private static final String GET_CHINA_SQL = "select t1.china, t2.name, t1.period, t1.content" +
            " from horo_chinas_v2 t1, horo_uploads t2 where t1.icon_id=t2.id and upchina=?";
    private static final String PARAM_CHINA = "china";
    private static final String NAME_KEY = "name";
    private static final String ICON_KEY = "icon";
    private static final String PERIOD_KEY = "period";
    private static final String CONTENT_KEY = "content";

    public static Result index() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                final PreparedStatement statement = DB.getConnection().prepareStatement(GET_CHINA_SQL);
                statement.setString(1, json.get(PARAM_CHINA).textValue().toUpperCase());
                result = ok(Response.content(fetchContent(statement.executeQuery())).asJson());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    private static JsonNode fetchContent(final ResultSet resultSet) throws SQLException {
        JsonNode content = NullNode.getInstance();
        if (resultSet.next()) {
            final ObjectNode object = Json.newObject();
            object.put(NAME_KEY, resultSet.getString(1));
            object.put(ICON_KEY, resultSet.getString(2));
            object.put(PERIOD_KEY, resultSet.getString(3));
            object.put(CONTENT_KEY, resultSet.getString(4));
            content = object;
        }
        return content;
    }
}
