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
import java.util.concurrent.CompletionStage;

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
    public CompletionStage<Result> amulets() {
        return this.amuletsSearchAction.execute(Controller.request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> dreams() {
        return this.dreamsSearchAction.execute(Controller.request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> names() {
        return this.namesSearchAction.execute(Controller.request().body().asJson());
    }

    public CompletionStage<Result> chinas() {
        return chinasByLocale(DEFAULT_LOCALE);
    }

    public CompletionStage<Result> chinasByLocale(final String locale) {
        return this.searchChinasAction.execute(locale);
    }

    public CompletionStage<Result> druids() {
        return druidsByLocale(DEFAULT_LOCALE);
    }

    public CompletionStage<Result> druidsByLocale(final String locale) {
        return this.searchDruidsAction.execute(locale);
    }

    public CompletionStage<Result> flowers() {
        return flowersByLocale(DEFAULT_LOCALE);
    }

    public CompletionStage<Result> flowersByLocale(final String locale) {
        return this.searchFlowersAction.execute(locale);
    }

    public CompletionStage<Result> japans() {
        return japansByLocale(DEFAULT_LOCALE);
    }

    public CompletionStage<Result> japansByLocale(final String locale) {
        return this.searchJapansAction.execute(locale);
    }
}
