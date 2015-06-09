package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractSimpleControllerAction;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.responses.KeysForResponse;
import ua.meugen.horoscopes.actions.responses.KeysResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meugen on 22.03.15.
 */
@Component
public final class GetKeysForAction extends AbstractSimpleControllerAction {

    private static final Logger.ALogger LOG = Logger.of(GetKeysForAction.class);

    private static final String GET_KEYS_FOR_SQL = "SELECT DISTINCT type, key FROM horo_periods p1 WHERE p1.period IN" +
            " ('today', 'cur', 'second') AND (p1.type='weekly' AND p1.version=2 OR p1.type<>'weekly')";

    private final ControllerResponsesFactory<KeysForResponse> factory;

    /**
     * Default constructor.
     */
    public GetKeysForAction() {
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    private KeysForResponse newResponse() {
        return new KeysForResponse();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action() {
        try {
            final KeysForResponse response = DatabaseHelper.actionWithStatement(this::internalAction,
                    GET_KEYS_FOR_SQL);
            return Controller.ok(response.asJson());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
    }

    private KeysForResponse internalAction(final PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            final Map<String, String> keys = new HashMap<>();
            while (resultSet.next()) {
                keys.put(resultSet.getString(1), resultSet.getString(2));
            }
            final KeysForResponse response = this.factory.newOkResponse();
            response.setKeys(keys);
            return response;
        }
    }
}
