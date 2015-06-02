package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by meugen on 22.03.15.
 */
public final class GetKeysAction extends AbstractJsonControllerAction {

    private static final Logger.ALogger LOG = Logger.of(GetKeysAction.class);

    private static final String PARAM_TYPE = "type";
    private static final String PARAM_VERSION = "version";

    private static final String GET_KEYS_SQL = "SELECT DISTINCT key FROM horo_periods WHERE type=? AND version=?";
    private static final String GET_KEYS_PERIOD_SQL = "SELECT DISTINCT key FROM horo_periods WHERE type=? AND version=? AND period=?";

    private String period;

    /**
     * Constructor.
     *
     * @param json Json
     */
    public GetKeysAction(final JsonNode json) {
        super(json);
    }

    /**
     * Getter for period.
     *
     * @return Period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Setter for period.
     *
     * @param period Period
     */
    public void setPeriod(final String period) {
        this.period = period;
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final JsonNode json) {
        try {
            final String sql = this.period == null ? GET_KEYS_SQL : GET_KEYS_PERIOD_SQL;
            final JsonNode result = DatabaseHelper.actionWithStatement((statement) ->
                    internalAction(statement, json), sql);
            return Controller.ok(result);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return Controller.internalServerError(BaseResponse.error(e).asJson());
        }
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_TYPE).asText());
        statement.setInt(2, json.get(PARAM_VERSION).asInt());
        if (this.period != null) {
            statement.setString(3, this.period);
        }
        try (ResultSet resultSet = statement.executeQuery()) {
            final Set<String> result = new HashSet<>();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
            return Json.toJson(result);
        }
    }

    /**
     * {@inheritDoc}
     */
    protected String[] getNotNullFields() {
        return new String[]{PARAM_TYPE, PARAM_VERSION};
    }
}
