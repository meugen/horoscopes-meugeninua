package controllers.portal.add;

import helpers.controllers.portal.add.HoroscopeHelperFactory;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by admin on 31.10.2014.
 */
public final class Horoscope extends Controller {

    public static F.Promise<Result> monthly() {
        return HoroscopeHelperFactory.newHoroscopeMonthly().execute();
    }

    public static F.Promise<Result> monthlyBy(final String month, final String year) {
        return HoroscopeHelperFactory.newHoroscopeMonthly(month, year).execute();
    }

    public static F.Promise<Result> yearly() {
        return HoroscopeHelperFactory.newHoroscopeYearly().execute();
    }

    public static F.Promise<Result> yearlyBy(final String year) {
        return HoroscopeHelperFactory.newHoroscopeYearly(year).execute();
    }
}
