package helpers.controllers.content.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.controllers.Response;
import helpers.controllers.content.AbstractJsonControllerHelper;
import helpers.controllers.content.OnFillObjectListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 23.10.14.
 */
final class InterpretationSearchHelper extends AbstractJsonControllerHelper {

    private static final Log LOG = LogFactory.getLog(InterpretationSearchHelper.class);

    private static final String PARAM_SEARCH = "search";
    private static final String ITEMS_KEY = "items";

    private String sql;
    private OnFillObjectListener onFillObjectListener;

    /**
     * Constructor.
     *
     * @param json Json
     */
    public InterpretationSearchHelper(final JsonNode json) {
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
    protected JsonNode action(final JsonNode json) {
        JsonNode result;
        try {
            result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<JsonNode>() {
                public JsonNode onAction(PreparedStatement statement) throws SQLException {
                    return InterpretationSearchHelper.this.internalAction(statement, json);
                }
            }, this.sql);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Response.error(e).asJson();
        }
        return result;
    }

    private JsonNode internalAction(final PreparedStatement statement, final JsonNode json) throws SQLException {
        statement.setString(1, json.get(PARAM_SEARCH).textValue().toUpperCase());
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
