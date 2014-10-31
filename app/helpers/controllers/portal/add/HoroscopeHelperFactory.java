package helpers.controllers.portal.add;

import helpers.controllers.ControllerHelper;

/**
 * Created by admin on 31.10.2014.
 */
public final class HoroscopeHelperFactory {

    private HoroscopeHelperFactory() {
        // Nothing to do
    }

    public static ControllerHelper newHoroscopeMonthly() {
        return new HoroscopeMonthlyHelper();
    }

    public static ControllerHelper newHoroscopeMonthly(final String month, final String year) {
        final HoroscopeMonthlyByHelper helper = new HoroscopeMonthlyByHelper();
        helper.setMonth(month);
        helper.setYear(year);
        return helper;
    }

    public static ControllerHelper newHoroscopeYearly() {
        return new HoroscopeYearlyHelper();
    }

    public static ControllerHelper newHoroscopeYearly(final String year) {
        final HoroscopeYearlyByHelper helper = new HoroscopeYearlyByHelper();
        helper.setYear(year);
        return helper;
    }
}
