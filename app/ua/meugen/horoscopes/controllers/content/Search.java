package ua.meugen.horoscopes.controllers.content;

import org.springframework.stereotype.Service;
import ua.meugen.horoscopes.actions.controllers.content.search.SearchHelpersFactory;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 02.07.14.
 */
@Service
public final class Search extends Controller {

    private static final String DEFAULT_LOCALE = "ru";

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> amulets() {
        return SearchHelpersFactory.newSearchAmuletsHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> dreams() {
        return SearchHelpersFactory.newSearchDreamsHelper(request().body().asJson()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> names() {
        return SearchHelpersFactory.newSearchNamesHelper(request().body().asJson()).execute();
    }

    public F.Promise<Result> chinas() {
        return chinasByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> chinasByLocale(final String locale) {
        return SearchHelpersFactory.newSearchChinasHelper(locale).execute();
    }

    public F.Promise<Result> druids() {
        return druidsByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> druidsByLocale(final String locale) {
        return SearchHelpersFactory.newSearchDruidsHelper(locale).execute();
    }

    public F.Promise<Result> flowers() {
        return flowersByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> flowersByLocale(final String locale) {
        return SearchHelpersFactory.newSearchFlowersHelper(locale).execute();
    }

    public F.Promise<Result> japans() {
        return japansByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> japansByLocale(final String locale) {
        return SearchHelpersFactory.newSearchJapansHelper(locale).execute();
    }
}
