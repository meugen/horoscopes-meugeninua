package helpers.controllers.content.update;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.controllers.Response;
import helpers.controllers.content.AbstractControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by admin on 24.10.2014.
 */
final class MultipleUpdatesHelper extends AbstractUpdateHelper {

    private final List<AbstractUpdateHelper> updates = Collections.synchronizedList(
            new ArrayList<AbstractUpdateHelper>());

    /**
     * Constructor.
     * @param uri URI
     */
    MultipleUpdatesHelper(final String uri) {
        super(uri);
    }

    /**
     * Add new update.
     * @param update Update instance
     */
    public void addUpdate(final AbstractUpdateHelper update) {
        this.updates.add(update);
    }

    /**
     * Remove update by it's key.
     * @param update Update instance
     */
    public void removeUpdate(final AbstractUpdateHelper update) {
        this.updates.remove(update);
    }

    /**
     * {@inheritDoc}
     */
    public void internalAction(final Connection connection) throws SQLException {
        for (AbstractUpdateHelper update : this.updates) {
            update.internalAction(connection);
        }
    }
}
