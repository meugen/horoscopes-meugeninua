package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.content.Response;
import helpers.DatabaseHelper;
import helpers.controllers.content.AbstractControllerHelper;
import helpers.controllers.content.AbstractJsonControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
final class GetAmuletHelper extends AbstractJsonControllerHelper {

    private static final Log LOG = LogFactory.getLog(GetAmuletHelper.class);

    private static final String GET_AMULET_SQL = "select t2.name, t1.content from horo_amulets_v2 t1," +
            " horo_uploads t2 where t1.image_id=t2.id and t1.upamulet=?";
    private static final String PARAM_AMULET = "amulet";
    private static final String IMAGE_KEY = "image";
    private static final String TEXT_KEY = "text";

    /**
     * Constructor.
     * @param json Json
     */
    public GetAmuletHelper(final JsonNode json) {
        super(json, new String[] { PARAM_AMULET });
    }

    /**
     * {@inheritDoc}
     */
    protected JsonNode action(final JsonNode json) {
        JsonNode result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(PreparedStatement statement) throws SQLException {
                    return GetAmuletHelper.this.internalAction(statement, json);
                }
            }, GET_AMULET_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Response.error(e).asJson();
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_AMULET).textValue());
        final ResultSet resultSet = statement.executeQuery();

        JsonNode content = NullNode.getInstance();
        if (resultSet.next()) {
            final ObjectNode object = Json.newObject();
            object.put(IMAGE_KEY, resultSet.getString(1));
            object.put(TEXT_KEY, resultSet.getString(2));
            content = object;
        }
        return Response.content(content).asJson();
    }
}
