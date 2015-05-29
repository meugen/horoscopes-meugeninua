package ua.meugen.horoscopes.controllers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateAllAction;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateDailyAction;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateWeeklyAction;

/**
 * Created by meugen on 03.07.14.
 */
@Service
public final class Update extends Controller {

    @Autowired
    private UpdateAllAction updateAllAction;

    @Autowired
    private UpdateDailyAction updateDailyAction;

    @Autowired
    private UpdateWeeklyAction updateWeeklyAction;

    public F.Promise<Result> all() {
        this.updateAllAction.setUri(request().uri());
        return this.updateAllAction.execute();
    }

    public F.Promise<Result> daily() {
        this.updateDailyAction.setUri(request().uri());
        return this.updateDailyAction.execute();
    }

    public F.Promise<Result> weekly() {
        this.updateWeeklyAction.setUri(request().uri());
        return this.updateWeeklyAction.execute();
    }
}
