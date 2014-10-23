package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.content.Response;
import helpers.DatabaseHelper;
import helpers.controllers.content.AbstractJsonControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
final class GetDreamHelper extends AbstractJsonControllerHelper {

    private static final Log LOG = LogFactory.getLog(GetDreamHelper.class);

    private static final String GET_DREAM_SQL = "select content from horo_dreams_v2 where updream=?";
    private static final String PARAM_DREAM = "dream";
    private static final String TEXT_KEY = "text";

    /**
     * Constructor.
     * @param json Json
     */
    public GetDreamHelper(final JsonNode json) {
        super(json, new String[] { PARAM_DREAM });
    }

    /**
     * {@inheritDoc}
     */
    protected JsonNode action(final JsonNode json) {
        JsonNode result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(PreparedStatement statement) throws SQLException {
                    return GetDreamHelper.this.internalAction(statement, json);
                }
            }, GET_DREAM_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Response.error(e).asJson();
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_DREAM).textValue().toUpperCase());
        final ResultSet resultSet = statement.executeQuery();

        JsonNode content = NullNode.getInstance();
        if (resultSet.next()) {
            final ObjectNode object = Json.newObject();
            object.put(TEXT_KEY, resultSet.getString(1));
            content = object;
        }
        return Response.content(content).asJson();
    }
}
