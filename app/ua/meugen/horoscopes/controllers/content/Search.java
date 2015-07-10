package ua.meugen.horoscopes.controllers.content;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.search.AmuletsSearchAction;
import ua.meugen.horoscopes.actions.controllers.content.search.DreamsSearchAction;
import ua.meugen.horoscopes.actions.controllers.content.search.NamesSearchAction;
import ua.meugen.horoscopes.actions.controllers.content.search.SimpleSearchAction;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by meugen on 02.07.14.
 */
public final class Search extends Controller {

    private static final String DEFAULT_LOCALE = "ru";

    @Inject
    private AmuletsSearchAction amuletsSearchAction;

    @Inject
    private DreamsSearchAction dreamsSearchAction;

    @Inject
    private NamesSearchAction namesSearchAction;

    @Inject
    @Named("searchChinasAction")
    private SimpleSearchAction searchChinasAction;

    @Inject
    @Named("searchDruidsAction")
    private SimpleSearchAction searchDruidsAction;

    @Inject
    @Named("searchFlowersAction")
    private SimpleSearchAction searchFlowersAction;

    @Inject
    @Named("searchJapansAction")
    private SimpleSearchAction searchJapansAction;

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> amulets() {
        return this.amuletsSearchAction.execute(request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> dreams() {
        return this.dreamsSearchAction.execute(request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> names() {
        return this.namesSearchAction.execute(request().body().asJson());
    }

    public F.Promise<Result> chinas() {
        return chinasByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> chinasByLocale(final String locale) {
        return this.searchChinasAction.execute(locale);
    }

    public F.Promise<Result> druids() {
        return druidsByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> druidsByLocale(final String locale) {
        return this.searchDruidsAction.execute(locale);
    }

    public F.Promise<Result> flowers() {
        return flowersByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> flowersByLocale(final String locale) {
        return this.searchFlowersAction.execute(locale);
    }

    public F.Promise<Result> japans() {
        return japansByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> japansByLocale(final String locale) {
        return this.searchJapansAction.execute(locale);
    }
}
