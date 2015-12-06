package ua.meugen.horoscopes.controllers.content;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.content.update.UpdateAllAction;
import ua.meugen.horoscopes.actions.actions.content.update.UpdateDailyAction;
import ua.meugen.horoscopes.actions.actions.content.update.UpdateWeeklyAction;

import javax.inject.Inject;

public final class Update {

    @Inject
    private UpdateAllAction updateAllAction;

    @Inject
    private UpdateDailyAction updateDailyAction;

    @Inject
    private UpdateWeeklyAction updateWeeklyAction;

    public F.Promise<Result> all() {
        return this.updateAllAction.execute(Controller.request().uri());
    }

    public F.Promise<Result> daily() {
        return this.updateDailyAction.execute(Controller.request().uri());
    }

    public F.Promise<Result> weekly() {
        return this.updateWeeklyAction.execute(Controller.request().uri());
    }
}
