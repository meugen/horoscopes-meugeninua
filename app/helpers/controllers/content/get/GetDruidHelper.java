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
final class GetDruidHelper extends AbstractJsonControllerHelper {

    private static final Log LOG = LogFactory.getLog(GetDruidHelper.class);

    private static final String GET_DRUID_SQL = "select content from horo_druids where updruid=?";
    private static final String PARAM_DRUID = "druid";
    private static final String TEXT_KEY = "text";

    /**
     * Constructor.
     * @param json Json
     */
    public GetDruidHelper(final JsonNode json) {
        super(json, new String[] { PARAM_DRUID });
    }

    /**
     * {@inheritDoc}
     */
    protected JsonNode action(final JsonNode json) {
        JsonNode result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(PreparedStatement statement) throws SQLException {
                    return GetDruidHelper.this.internalAction(statement, json);
                }
            }, GET_DRUID_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Response.error(e).asJson();
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_DRUID).textValue().toUpperCase());
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
