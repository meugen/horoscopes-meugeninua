package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.controllers.AbstractControllerHelper;
import helpers.controllers.Response;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 22.03.15.
 */
public final class GetKeysForHelper extends AbstractControllerHelper {

    private static final Logger.ALogger LOG = Logger.of(GetKeysForHelper.class);

    private static final String GET_KEYS_FOR_SQL = "SELECT DISTINCT type, key FROM horo_periods p1 WHERE p1.period IN" +
            " ('today', 'cur', 'second') AND (p1.type='weekly' AND p1.version=2 OR p1.type<>'weekly')";

    /**
     * {@inheritDoc}
     */
    protected Result action() {
        try {
            final JsonNode result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(final PreparedStatement statement) throws SQLException {
                    return internalAction(statement);
                }
            }, GET_KEYS_FOR_SQL);
            return Controller.ok(result);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return Controller.internalServerError(Response.error(e).asJson());
        }
    }

    private JsonNode internalAction(final PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            final ObjectNode result = Json.newObject();
            while (resultSet.next()) {
                result.put(resultSet.getString(1), resultSet.getString(2));
            }
            return result;
        }
    }
}
