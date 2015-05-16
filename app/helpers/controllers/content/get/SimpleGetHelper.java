package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.controllers.Response;
import helpers.controllers.content.AbstractJsonControllerHelper;
import helpers.controllers.content.OnFillObjectListener;
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
final class SimpleGetHelper extends AbstractJsonControllerHelper {

    private static final Logger.ALogger LOG = Logger.of(SimpleGetHelper.class);

    private static final String DEFAULT_LOCALE = "ru";
    private static final String PARAM_LOCALE = "locale";

    private String sql;
    private String param;
    private OnFillObjectListener onFillObjectListener;

    /**
     * Constructor.
     *
     * @param json Json
     */
    public SimpleGetHelper(final JsonNode json) {
        super(json);
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
     * Getter for param.
     *
     * @return Param
     */
    public String getParam() {
        return param;
    }

    /**
     * Setter for param.
     *
     * @param param Param
     */
    public void setParam(final String param) {
        this.param = param;
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
            final JsonNode response = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(PreparedStatement statement) throws SQLException {
                    return SimpleGetHelper.this.internalAction(statement, json);
                }
            }, this.sql);
            result = Controller.ok(response);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(Response.error(e).asJson());
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(this.param).textValue().toUpperCase());
        statement.setString(2, json.has(PARAM_LOCALE) ? json.get(PARAM_LOCALE).textValue() : DEFAULT_LOCALE);
        final ResultSet resultSet = statement.executeQuery();

        JsonNode content = NullNode.getInstance();
        if (resultSet.next()) {
            final ObjectNode object = Json.newObject();
            this.onFillObjectListener.onFillObject(object, resultSet);
            content = object;
        }
        return Response.content(content).asJson();
    }

    /**
     * {@inheritDoc}
     */
    protected String[] getNotNullFields() {
        return new String[]{this.param};
    }

}
