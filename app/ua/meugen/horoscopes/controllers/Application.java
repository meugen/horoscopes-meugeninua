package ua.meugen.horoscopes.controllers;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.application.ApplicationCrashAction;
import ua.meugen.horoscopes.template.bean.WelcomeTemplateBean;
import views.html.welcome;
import ua.meugen.horoscopes.controllers.portal.routes;

import javax.inject.Inject;

public final class Application extends Controller {

    @Inject
    private WelcomeTemplateBean welcomeTemplateBean;

    @Inject
    private ApplicationCrashAction applicationCrashAction;

    public Result index() {
        return redirect(routes.Horoscopes.daily());
    }

    public F.Promise<Result> welcome() {
        return F.Promise.promise(() -> ok(welcome.render(this.welcomeTemplateBean)));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> crash() {
        return this.applicationCrashAction.execute(request().body().asJson());
    }

}
