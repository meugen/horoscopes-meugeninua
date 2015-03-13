package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import helpers.DatabaseHelper;
import helpers.controllers.AbstractControllerHelper;
import helpers.controllers.Response;
import helpers.controllers.content.AbstractJsonControllerHelper;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper for get horoscopes for sign.
 * @author meugen
 */
final class GetHoroscopesForHelper extends TranslateHoroscopesHelper {

    private static final Logger.ALogger LOG = Logger.of(GetHoroscopesForHelper.class);

    private static final String SELECT = "SELECT type, period, content FROM horo_texts t WHERE kind=?" +
            " AND sign=? AND locale=? AND period IN (SELECT key FROM horo_periods p WHERE p.type=t.type AND" +
            " p.period IN ('today', 'cur', 'second') AND (p.type='weekly' AND p.version=2 OR p.type<>'weekly'))";
    private static final String TRANSLATE = "SELECT t1.type, t1.period, t1.content FROM horo_texts t1 WHERE t1.kind=?" +
            " AND t1.sign=? AND t1.locale=? AND t1.period IN (SELECT p1.key FROM horo_periods p1 WHERE p1.type=t1.type AND" +
            " p1.period IN ('today', 'cur', 'second') AND (p1.type='weekly' AND p1.version=2 OR p1.type<>'weekly'))" +
            " AND t1.period NOT IN (SELECT t2.period FROM horo_texts t2 WHERE t2.kind=?" +
            " AND t2.sign=? AND t2.locale=? AND t2.period IN (SELECT p2.key FROM horo_periods p2 WHERE p2.type=t2.type AND" +
            " p2.period IN ('today', 'cur', 'second') AND (p2.type='weekly' AND p2.version=2 OR p2.type<>'weekly')))";

    private static final String DEFAULT_KIND = "common";

    /**
     * Constructor.
     */
    public GetHoroscopesForHelper(final JsonNode json) {
        super(json);
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final JsonNode json) {
        try {
            this.translateAll(json);
            final JsonNode response = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(final PreparedStatement statement) throws SQLException {
                    return GetHoroscopesForHelper.this.internalAction(statement, json);
                }
            }, SELECT);
            return Controller.ok(response);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return Controller.internalServerError(Response.error(e).asJson());
        }
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, DEFAULT_KIND);
        statement.setString(2, json.get(PARAM_SIGN).textValue());
        statement.setString(3, this.getLocale(json));

        try (ResultSet resultSet = statement.executeQuery()) {
            final ObjectNode content = Json.newObject();
            while (resultSet.next()) {
                final ObjectNode sign = Json.newObject();
                sign.put(resultSet.getString(2), resultSet.getString(3));
                final ObjectNode kind = Json.newObject();
                kind.put(json.get(PARAM_SIGN).textValue(), sign);
                final ObjectNode type = Json.newObject();
                type.put(DEFAULT_KIND, kind);
                content.put(resultSet.getString(1), type);
            }
            return Response.content(content).asJson();
        }
    }

    private void translateAll(final JsonNode json) throws SQLException {
        final ObjectNode object = (ObjectNode) json;
        object.put(PARAM_KIND, DEFAULT_KIND);
        DatabaseHelper.actionWithDatabase(new DatabaseHelper.ConnectionAction<Void>() {
            public Void onAction(final Connection connection) throws SQLException {
                translateAll(connection, TRANSLATE, object);
                return null;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    protected void bindStatement(final PreparedStatement statement, final JsonNode json,
                                 final String locale) throws SQLException {
        statement.setString(1, DEFAULT_KIND);
        statement.setString(2, json.get(PARAM_SIGN).textValue());
        statement.setString(3, DEFAULT_LOCALE);
        statement.setString(4, DEFAULT_KIND);
        statement.setString(5, json.get(PARAM_SIGN).textValue());
        statement.setString(6, locale);
    }

    /**
     * {@inheritDoc}
     */
    protected String[] getNotNullFields() {
        return new String[] { PARAM_LOCALE, PARAM_SIGN };
    }
}
