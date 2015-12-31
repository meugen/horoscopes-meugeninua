package ua.meugen.horoscopes.controllers;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.actions.actions.application.ApplicationCrashAction;
import ua.meugen.horoscopes.helpers.ObfuscateHelper;
import ua.meugen.horoscopes.template.bean.WelcomeTemplateBean;
import views.html.welcome;

import javax.inject.Inject;

public final class Application {

    @Inject
    private WelcomeTemplateBean welcomeTemplateBean;

    @Inject
    private ApplicationCrashAction applicationCrashAction;

    @Inject
    private ObfuscateHelper obfuscateHelper;

    public Result index() {
        return Results.redirect(ua.meugen.horoscopes.controllers.routes.Application.welcome());
    }

    public F.Promise<Result> welcome() {
        return F.Promise.promise(() -> obfuscateHelper.ok(welcome.render(this.welcomeTemplateBean)));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> crash() {
        return this.applicationCrashAction.execute(Controller.request().body().asJson());
    }

}
