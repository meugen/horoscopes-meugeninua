package controllers.content;

import helpers.controllers.content.get.GetHelpersFactory;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 02.07.14.
 */
public final class Get extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> amulet() {
        return GetHelpersFactory.newGetAmuletHelper(request().body().asJson()).execute();
    }

    public static F.Promise<Result> amuletById(final Integer id) {
        return GetHelpersFactory.newGetAmuletHelper(id).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> china() {
        return GetHelpersFactory.newGetChinaHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> dream() {
        return GetHelpersFactory.newGetDreamHelper(request().body().asJson()).execute();
    }

    public static F.Promise<Result> dreamById(final Integer id) {
        return GetHelpersFactory.newGetDreamHelper(id).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> druid() {
        return GetHelpersFactory.newGetDruidHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> flower() {
        return GetHelpersFactory.newGetFlowerHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> horoscope() {
        return GetHelpersFactory.newGetHoroscopeHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> horoscopeByPeriod(final String period) {
        return GetHelpersFactory.newGetHoroscopeHelper(request().body().asJson(), period).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> horoscopesFor() {
        return GetHelpersFactory.newGetHoroscopesForHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> japan() {
        return GetHelpersFactory.newGetJapanHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> name() {
        return GetHelpersFactory.newGetNameHelper(request().body().asJson()).execute();
    }

    public static F.Promise<Result> nameById(final Integer id) {
        return GetHelpersFactory.newGetNameHelper(id).execute();
    }
}
