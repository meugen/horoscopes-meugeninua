package controllers;

import helpers.controllers.application.ApplicationCrashHelper;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public final class Application extends Controller {


    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> crash() {
        return new ApplicationCrashHelper(request().body().asJson()).execute();
    }

}
