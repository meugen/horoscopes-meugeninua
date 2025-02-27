package ua.meugen.horoscopes.actions.actions.content.update;

import ua.meugen.horoscopes.actions.responses.UpdateAllResponse;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public final class UpdateAllAction extends AbstractUpdateAction<UpdateAllResponse> {

    @Inject
    private UpdateDailyAction updateDailyAction;
    @Inject
    private UpdateWeeklyAction updateWeeklyAction;

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
    public UpdateAllResponse internalAction() {
        final Map<String, AbstractUpdateAction> updates = new HashMap<>();
        updates.put("daily", this.updateDailyAction);
        final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.SATURDAY) {
            updates.put("weekly", this.updateWeeklyAction);
        }

        final UpdateAllResponse response = this.factory.newOkResponse();
        for (Map.Entry<String, AbstractUpdateAction> entry : updates.entrySet()) {
            response.getContent().put(entry.getKey(), entry.getValue().internalAction());
        }
        return response;
    }
}
