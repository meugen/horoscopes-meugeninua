package ua.meugen.horoscopes.actions.controllers.content.update;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.controllers.Response;
import play.libs.Json;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 24.10.2014.
 */
@Component
public final class UpdateAllAction extends AbstractUpdateAction {

    /**
     * {@inheritDoc}
     */
    public Response internalAction(final Connection connection) throws SQLException {
        final Map<String, AbstractUpdateAction> updates = new HashMap<>();
        updates.put("daily", new UpdateDailyAction("akka /content/update/daily"));
        final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.SATURDAY) {
            updates.put("weekly", new UpdateWeeklyAction("akka /content/update/weekly"));
        }

        final ObjectNode content = Json.newObject();
        for (Map.Entry<String, AbstractUpdateAction> entry : updates.entrySet()) {
            final Response response = entry.getValue().internalAction(connection);
            content.set(entry.getKey(), response.asJson());
        }
        return Response.content(content);
    }
}
