package controllers;

import helpers.controllers.application.ApplicationCrashHelper;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public final class Application extends Controller {


    public static Result index() {
        return redirect("https://google.com");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> crash() {
        return new ApplicationCrashHelper(request().body().asJson()).execute();
    }

}
