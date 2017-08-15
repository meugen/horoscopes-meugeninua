package ua.meugen.horoscopes.controllers;

import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.actions.actions.application.ApplicationAssetsAction;
import ua.meugen.horoscopes.actions.actions.application.ApplicationCrashAction;
import ua.meugen.horoscopes.template.bean.WelcomeTemplateBean;
import views.html.welcome;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public final class Application {

    @Inject
    private WelcomeTemplateBean welcomeTemplateBean;

    @Inject
    private ApplicationCrashAction applicationCrashAction;

    @Inject @Named("css")
    private ApplicationAssetsAction applicationCssAction;

    @Inject @Named("js")
    private ApplicationAssetsAction applicationJsAction;

    public Result index() {
        return Results.redirect(ua.meugen.horoscopes.controllers.routes.Application.welcome());
    }

    public CompletionStage<Result> welcome() {
        return CompletableFuture.supplyAsync(() -> Results.ok(welcome.render(this.welcomeTemplateBean)));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> crash() {
        return this.applicationCrashAction.execute(Controller.request().body().asJson());
    }

    public CompletionStage<Result> js(final String name) {
        return applicationJsAction.execute(name);
    }

    public CompletionStage<Result> css(final String name) {
        return applicationCssAction.execute(name);
    }

}
