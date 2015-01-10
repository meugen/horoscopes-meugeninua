package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.TranslateHelper;
import helpers.controllers.Response;
import helpers.controllers.application.ApplicationCrashHelper;
import play.libs.F;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class Application extends Controller {


    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> crash() {
        return new ApplicationCrashHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> translate() {
        return F.Promise.promise(new F.Function0<Result>() {
            @Override
            public Result apply() throws Throwable {
                return internalTranslate(request().body().asJson());
            }
        });
    }

    private static Result internalTranslate(final JsonNode json) {
        try {
            final TranslateHelper helper = new TranslateHelper();
            final List<String> translated = helper.translateAll(Arrays.asList(json.get("word").textValue()));

            final ObjectNode objectNode = Json.newObject();
            objectNode.put("translated", translated.get(0));
            return ok(objectNode);
        } catch (IOException e) {
            return internalServerError(Response.error(e).asJson());
        }
    }

}
