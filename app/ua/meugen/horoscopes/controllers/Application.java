package ua.meugen.horoscopes.controllers;

import org.springframework.stereotype.Service;
import ua.meugen.horoscopes.actions.controllers.application.ApplicationCrashAction;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

@Service
public final class Application extends Controller {


    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> crash() {
        return new ApplicationCrashAction(request().body().asJson()).execute();
    }

}
