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
public final class Amulet extends Controller {

    private static final Log LOG = LogFactory.getLog(Amulet.class);

    private static final String GET_AMULET_SQL = "select t2.name, t1.content from horo_amulets_v2 t1," +
            " horo_uploads t2 where t1.image_id=t2.id and t1.upamulet=?";
    private static final String PARAM_AMULET = "amulet";
    private static final String IMAGE_KEY = "image";
    private static final String TEXT_KEY = "text";

    public static Result index() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                final PreparedStatement statement = DB.getConnection().prepareStatement(GET_AMULET_SQL);
                statement.setString(1, json.get(PARAM_AMULET).textValue());
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
            object.put(IMAGE_KEY, resultSet.getString(1));
            object.put(TEXT_KEY, resultSet.getString(2));
            content = object;
        }
        return content;
    }
}
