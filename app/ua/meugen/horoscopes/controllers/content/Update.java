package ua.meugen.horoscopes.controllers.content;

import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.content.update.UpdateAllAction;
import ua.meugen.horoscopes.actions.actions.content.update.UpdateDailyAction;
import ua.meugen.horoscopes.actions.actions.content.update.UpdateWeeklyAction;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;

@Singleton
public final class Update {

    @Inject
    private UpdateAllAction updateAllAction;

    @Inject
    private UpdateDailyAction updateDailyAction;

    @Inject
    private UpdateWeeklyAction updateWeeklyAction;

    public CompletionStage<Result> all() {
        return this.updateAllAction.execute(Controller.request().uri());
    }

    public CompletionStage<Result> daily() {
        return this.updateDailyAction.execute(Controller.request().uri());
    }

    public CompletionStage<Result> weekly() {
        return this.updateWeeklyAction.execute(Controller.request().uri());
    }
}
