package ua.meugen.horoscopes.actions.controllers.content.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.Response;
import ua.meugen.horoscopes.actions.controllers.content.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.controllers.content.OnFillObjectListener;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 23.10.14.
 */
final class InterpretationSearchAction extends AbstractJsonControllerAction {

    private static final Logger.ALogger LOG = Logger.of(InterpretationSearchAction.class);

    private static final String DEFAULT_LOCALE = "ru";
    private static final String PARAM_SEARCH = "search";
    private static final String PARAM_LOCALE = "locale";
    private static final String ITEMS_KEY = "items";

    private String sql;
    private OnFillObjectListener onFillObjectListener;

    /**
     * Constructor.
     *
     * @param json Json
     */
    public InterpretationSearchAction(final JsonNode json) {
        super(json);
    }

    /**
     * Getter for sql.
     *
     * @return SQL
     */
    public String getSql() {
        return sql;
    }

    /**
     * Setter for sql.
     *
     * @param sql SQL
     */
    public void setSql(final String sql) {
        this.sql = sql;
    }

    /**
     * Getter for on fill object listener.
     *
     * @return Listener
     */
    public OnFillObjectListener getOnFillObjectListener() {
        return onFillObjectListener;
    }

    /**
     * Setter for on fill object listener.
     *
     * @param onFillObjectListener Listener
     */
    public void setOnFillObjectListener(final OnFillObjectListener onFillObjectListener) {
        this.onFillObjectListener = onFillObjectListener;
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final JsonNode json) {
        Result result;
        try {
            final JsonNode response = DatabaseHelper.actionWithStatement((statement) ->
                    internalAction(statement, json), this.sql);
            result = Controller.ok(response);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(Response.error(e).asJson());
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_SEARCH).textValue().toUpperCase());
        statement.setString(2, json.has(PARAM_LOCALE) ? json.get(PARAM_LOCALE).textValue() : DEFAULT_LOCALE);
        final ResultSet resultSet = statement.executeQuery();

        final ObjectNode content = Json.newObject();
        final ArrayNode items = content.putArray(ITEMS_KEY);
        while (resultSet.next()) {
            final ObjectNode item = Json.newObject();
            this.onFillObjectListener.onFillObject(item, resultSet);
            items.add(item);
        }
        return Response.content(content).asJson();
    }

    /**
     * {@inheritDoc}
     */
    protected String[] getNotNullFields() {
        return new String[]{PARAM_SEARCH};
    }
}
