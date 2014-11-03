package helpers.controllers.portal.add;

import helpers.controllers.AbstractControllerHelper;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by admin on 31.10.2014.
 */
final class HoroscopeMonthlyHelper extends AbstractControllerHelper {

    /**
     * {@inheritDoc}
     */
    protected Result action() {
        return Controller.ok(views.html.portal.add.horoscope.monthly.render("aaa"));
    }
}
