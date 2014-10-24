package helpers.controllers.content.update;

import helpers.controllers.ControllerHelper;

import java.util.Calendar;

/**
 * Created by admin on 24.10.2014.
 */
public class UpdateHelpersFactory {

    public static ControllerHelper newUpdateDailyHelper(final String uri) {
        return new UpdateDailyHelper(uri);
    }

    public static ControllerHelper newUpdateWeeklyHelper(final String uri) {
        return new UpdateWeeklyHelper(uri);
    }

    public static ControllerHelper newUpdateAllHelper(final String uri) {
        final MultipleUpdatesHelper helper = new MultipleUpdatesHelper(uri);
        helper.addUpdate(new UpdateDailyHelper(uri));
        final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.SATURDAY) {
            helper.addUpdate(new UpdateWeeklyHelper(uri));
        }
        return helper;
    }
}
