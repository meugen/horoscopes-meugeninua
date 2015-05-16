package helpers.controllers.content.update;

import helpers.controllers.ControllerHelper;

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
        return new UpdateAllHelper(uri);
    }
}
