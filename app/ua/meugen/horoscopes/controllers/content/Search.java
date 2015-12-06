package ua.meugen.horoscopes.controllers.content;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.content.search.AmuletsSearchAction;
import ua.meugen.horoscopes.actions.actions.content.search.DreamsSearchAction;
import ua.meugen.horoscopes.actions.actions.content.search.NamesSearchAction;
import ua.meugen.horoscopes.actions.actions.content.search.SimpleSearchAction;
import ua.meugen.horoscopes.entities.China;
import ua.meugen.horoscopes.entities.Druid;
import ua.meugen.horoscopes.entities.Flower;
import ua.meugen.horoscopes.entities.Japan;

import javax.inject.Inject;

public final class Search {

    private static final String DEFAULT_LOCALE = "ru";

    @Inject
    private AmuletsSearchAction amuletsSearchAction;

    @Inject
    private DreamsSearchAction dreamsSearchAction;

    @Inject
    private NamesSearchAction namesSearchAction;

    @Inject
    private SimpleSearchAction<China> searchChinasAction;

    @Inject
    private SimpleSearchAction<Druid> searchDruidsAction;

    @Inject
    private SimpleSearchAction<Flower> searchFlowersAction;

    @Inject
    private SimpleSearchAction<Japan> searchJapansAction;

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> amulets() {
        return this.amuletsSearchAction.execute(Controller.request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> dreams() {
        return this.dreamsSearchAction.execute(Controller.request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> names() {
        return this.namesSearchAction.execute(Controller.request().body().asJson());
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
