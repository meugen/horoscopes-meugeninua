package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractSimpleControllerAction;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
final class SimpleGetByIdAction extends AbstractSimpleControllerAction {

    private static final Logger.ALogger LOG = Logger.of(SimpleGetByIdAction.class);

    private static final String DEFAULT_LOCALE = "ru";
    private static final String PARAM_LOCALE = "locale";

    private final Integer id;
    private String sql;
    private OnFillObjectListener onFillObjectListener;

    /**
     * Constructor.
     *
     * @param id Id
     */
    public SimpleGetByIdAction(final Integer id) {
        this.id = id;
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
    protected Result action() {
        Result result;
        try {
            final JsonNode response = DatabaseHelper.actionWithStatement(this::internalAction, this.sql);
            result = Controller.ok(response);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(BaseResponse.error(e).asJson());
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement) throws SQLException {
        statement.setInt(1, this.id);
        final ResultSet resultSet = statement.executeQuery();

        JsonNode content = NullNode.getInstance();
        if (resultSet.next()) {
            final ObjectNode object = Json.newObject();
            this.onFillObjectListener.onFillObject(object, resultSet);
            content = object;
        }
        return BaseResponse.content(content).asJson();
    }

}
