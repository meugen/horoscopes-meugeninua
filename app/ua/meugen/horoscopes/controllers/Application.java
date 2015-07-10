package ua.meugen.horoscopes.controllers;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.application.ApplicationCrashAction;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;
import views.html.index;

import javax.inject.Inject;

public final class Application extends Controller {

    @Inject
    private IndexTemplateBean indexTemplateBean;

    @Inject
    private ApplicationCrashAction applicationCrashAction;

    public Result index() {
        return ok(index.render(this.indexTemplateBean));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> crash() {
        return this.applicationCrashAction.execute(request().body().asJson());
    }

}
