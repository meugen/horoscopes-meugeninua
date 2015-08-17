package ua.meugen.horoscopes.controllers;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.application.ApplicationCrashAction;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;
import ua.meugen.horoscopes.template.bean.WelcomeTemplateBean;
import views.html.index;
import views.html.welcome;

import javax.inject.Inject;

public final class Application extends Controller {

    @Inject
    private IndexTemplateBean indexTemplateBean;

    @Inject
    private WelcomeTemplateBean welcomeTemplateBean;

    @Inject
    private ApplicationCrashAction applicationCrashAction;

    public F.Promise<Result> index() {
        return F.Promise.promise(() -> ok(index.render(Application.this.indexTemplateBean)));
    }

    public F.Promise<Result> welcome() {
        return F.Promise.promise(() -> ok(welcome.render(this.welcomeTemplateBean)));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> crash() {
        return this.applicationCrashAction.execute(request().body().asJson());
    }

}
