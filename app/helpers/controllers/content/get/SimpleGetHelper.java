package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.controllers.Response;
import helpers.DatabaseHelper;
import helpers.controllers.content.AbstractJsonControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
final class SimpleGetHelper extends AbstractJsonControllerHelper {

    private static final Log LOG = LogFactory.getLog(SimpleGetHelper.class);

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
    protected JsonNode action(final JsonNode json) {
        JsonNode result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(PreparedStatement statement) throws SQLException {
                    return SimpleGetHelper.this.internalAction(statement, json);
                }
            }, this.sql);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Response.error(e).asJson();
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(this.param).textValue());
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

    interface OnFillObjectListener {

        void onFillObject(ObjectNode object, ResultSet resultSet) throws SQLException;
    }
}
