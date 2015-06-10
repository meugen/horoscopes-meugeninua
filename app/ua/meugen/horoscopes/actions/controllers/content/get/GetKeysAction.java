package ua.meugen.horoscopes.actions.controllers.content.get;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.requests.KeysRequest;
import ua.meugen.horoscopes.actions.responses.KeysResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meugen on 22.03.15.
 */
public final class GetKeysAction extends AbstractJsonControllerAction<KeysRequest> {

    private static final Logger.ALogger LOG = Logger.of(GetKeysAction.class);

    private static final String GET_KEYS_SQL = "SELECT DISTINCT key FROM horo_periods WHERE type=? AND version=?";
    private static final String GET_KEYS_PERIOD_SQL = "SELECT DISTINCT key FROM horo_periods WHERE type=? AND version=? AND period=?";

    private final ControllerResponsesFactory<KeysResponse> factory;

    /**
     * Default constructor.
     */
    public GetKeysAction() {
        super(KeysRequest.class);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    private KeysResponse newResponse() {
        return new KeysResponse();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final KeysRequest request) {
        try {
            final String sql = request.getPeriod() == null ? GET_KEYS_SQL : GET_KEYS_PERIOD_SQL;
            final KeysResponse response = DatabaseHelper.actionWithStatement((statement) ->
                    internalAction(statement, request), sql);
            return Controller.ok(response.asJson());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
    }

    private KeysResponse internalAction(final PreparedStatement statement, final KeysRequest request) throws SQLException {
        statement.setString(1, request.getType());
        statement.setInt(2, request.getVersion());
        final String period = request.getPeriod();
        if (period != null) {
            statement.setString(3, period);
        }
        try (ResultSet resultSet = statement.executeQuery()) {
            final List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            final KeysResponse response = this.factory.newOkResponse();
            response.setKeys(result);
            return response;
        }
    }
}
