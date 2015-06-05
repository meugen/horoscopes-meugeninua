package ua.meugen.horoscopes.actions.controllers.content.update;

import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.responses.UpdateAllResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 24.10.2014.
 */
@Component
public final class UpdateAllAction extends AbstractUpdateAction<UpdateAllResponse> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected UpdateAllResponse newResponse() {
        return new UpdateAllResponse();
    }

    /**
     * {@inheritDoc}
     */
    public UpdateAllResponse internalAction(final Connection connection) throws SQLException {
        final Map<String, AbstractUpdateAction> updates = new HashMap<>();
        updates.put("daily", new UpdateDailyAction("akka /content/update/daily"));
        final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.SATURDAY) {
            updates.put("weekly", new UpdateWeeklyAction("akka /content/update/weekly"));
        }

        final UpdateAllResponse response = this.factory.newOkResponse();
        for (Map.Entry<String, AbstractUpdateAction> entry : updates.entrySet()) {
            response.getContent().put(entry.getKey(), entry.getValue().internalAction(connection));
        }
        return response;
    }
}
