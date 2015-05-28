package helpers.controllers.content.update;

import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.controllers.Response;
import play.libs.Json;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 24.10.2014.
 */
final class UpdateAllHelper extends AbstractUpdateHelper {

    /**
     * Constructor.
     *
     * @param uri URI
     */
    UpdateAllHelper(final String uri) {
        super(uri);
    }

    /**
     * {@inheritDoc}
     */
    public Response internalAction(final Connection connection) throws SQLException {
        final Map<String, AbstractUpdateHelper> updates = new HashMap<>();
        updates.put("daily", new UpdateDailyHelper("akka /content/update/daily"));
        final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.SATURDAY) {
            updates.put("weekly", new UpdateWeeklyHelper("akka /content/update/weekly"));
        }

        final ObjectNode content = Json.newObject();
        for (Map.Entry<String, AbstractUpdateHelper> entry : updates.entrySet()) {
            final Response response = entry.getValue().internalAction(connection);
            content.set(entry.getKey(), response.asJson());
        }
        return Response.content(content);
    }
}
