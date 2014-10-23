package controllers.content;

import helpers.controllers.content.search.SearchHelpersFactory;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 02.07.14.
 */
public final class Search extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> amulets() {
        return SearchHelpersFactory.newSearchAmuletsHelper(request().body().asJson()).execute();
    }

    public static F.Promise<Result> chinas() {
        return SearchHelpersFactory.newSearchChinasHelper().execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> dreams() {
        return SearchHelpersFactory.newSearchDreamsHelper(request().body().asJson()).execute();
    }

    public static F.Promise<Result> druids() {
        return SearchHelpersFactory.newSearchDruidsHelper().execute();
    }

    public static F.Promise<Result> flowers() {
        return SearchHelpersFactory.newSearchFlowersHelper().execute();
    }

    public static F.Promise<Result> japans() {
        return SearchHelpersFactory.newSearchJapansHelper().execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> names() {
        return SearchHelpersFactory.newSearchNamesHelper(request().body().asJson()).execute();
    }
}
