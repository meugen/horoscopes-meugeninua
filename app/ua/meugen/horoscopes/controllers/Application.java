package ua.meugen.horoscopes.controllers;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import ua.meugen.horoscopes.actions.actions.application.ApplicationCrashAction;
import ua.meugen.horoscopes.controllers.portal.routes;
import ua.meugen.horoscopes.template.bean.WelcomeTemplateBean;
import views.html.welcome;

import javax.inject.Inject;
import java.util.regex.Pattern;

public final class Application {

    private static final Pattern START = Pattern.compile(">\\s+");
    private static final Pattern END = Pattern.compile("\\s+<");

    @Inject
    private WelcomeTemplateBean welcomeTemplateBean;

    @Inject
    private ApplicationCrashAction applicationCrashAction;

    public Result index() {
        return Results.redirect(routes.Horoscopes.daily());
    }

    public F.Promise<Result> welcome() {
        return F.Promise.promise(() -> Application.ok(welcome.render(this.welcomeTemplateBean)));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> crash() {
        return this.applicationCrashAction.execute(Controller.request().body().asJson());
    }

    public static Results.Status ok(final Content content) {
        final String body = START.matcher(content.body()).replaceAll(">");
        return Results.ok(END.matcher(body).replaceAll("<")).as(content.contentType());
        // return Results.ok(content.body()).as(content.contentType());
    }

}
