package ua.meugen.horoscopes.actions.controllers.content.search;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractControllerAction;
import ua.meugen.horoscopes.actions.controllers.Response;
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
public final class SimpleSearchAction extends AbstractControllerAction {

    private static final Logger.ALogger LOG = Logger.of(SimpleSearchAction.class);

    private static final String ITEMS_KEY = "items";

    private String locale;
    private String sql;
    private OnFillObjectListener onFillObjectListener;

    /**
     * Getter for locale.
     *
     * @return Locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Setter for locale.
     *
     * @param locale Locale
     */
    public void setLocale(final String locale) {
        this.locale = locale;
    }

    /**
     * Getter for sql.
     *
     * @return Sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * Setter for sql.
     *
     * @param sql Sql
     */
    public void setSql(final String sql) {
        this.sql = sql;
    }

    /**
     * Getter for on fill object listener.
     *
     * @return On fill object listener
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
    protected Result action() {
        Result result;
        try {
            result = DatabaseHelper.actionWithStatement(this::internalAction, this.sql);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.ok(Response.error(e).asJson());
        }
        return result;
    }

    private Result internalAction(final PreparedStatement statement) throws SQLException {
        statement.setString(1, this.locale);
        final ResultSet resultSet = statement.executeQuery();

        final ObjectNode content = Json.newObject();
        final ArrayNode items = content.putArray(ITEMS_KEY);
        while (resultSet.next()) {
            final ObjectNode item = Json.newObject();
            this.onFillObjectListener.onFillObject(item, resultSet);
            items.add(item);
        }
        return Controller.ok(Response.content(content).asJson());
    }
}
