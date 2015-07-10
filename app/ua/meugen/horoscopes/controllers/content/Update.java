package ua.meugen.horoscopes.controllers.content;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateAllAction;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateDailyAction;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateWeeklyAction;

import javax.inject.Inject;

/**
 * Created by meugen on 03.07.14.
 */
public final class Update extends Controller {

    @Inject
    private UpdateAllAction updateAllAction;

    @Inject
    private UpdateDailyAction updateDailyAction;

    @Inject
    private UpdateWeeklyAction updateWeeklyAction;

    public F.Promise<Result> all() {
        return this.updateAllAction.execute(request().uri());
    }

    public F.Promise<Result> daily() {
        return this.updateDailyAction.execute(request().uri());
    }

    public F.Promise<Result> weekly() {
        return this.updateWeeklyAction.execute(request().uri());
    }
}
