package controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.controllers.content.CheckJsonHelper;
import helpers.controllers.content.update.UpdateHelpersFactory;
import helpers.controllers.content.update.UpdateMonthlyHelper;
import helpers.controllers.content.update.UpdateYearlyHelper;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 03.07.14.
 */
public final class Update extends Controller {

    public static F.Promise<Result> all() {
        return UpdateHelpersFactory.newUpdateAllHelper(request().uri()).execute();
    }

    public static F.Promise<Result> daily() {
        return UpdateHelpersFactory.newUpdateDailyHelper(request().uri()).execute();
    }

    public static F.Promise<Result> weekly() {
        return UpdateHelpersFactory.newUpdateWeeklyHelper(request().uri()).execute();
    }
}
