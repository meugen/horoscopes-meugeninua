package ua.meugen.horoscopes.controllers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.search.AmuletsSearchAction;
import ua.meugen.horoscopes.actions.controllers.content.search.DreamsSearchAction;
import ua.meugen.horoscopes.actions.controllers.content.search.NamesSearchAction;
import ua.meugen.horoscopes.actions.controllers.content.search.SimpleSearchAction;

/**
 * Created by meugen on 02.07.14.
 */
@Service
public final class Search extends Controller {

    private static final String DEFAULT_LOCALE = "ru";

    private static final String SEARCH_CHINAS_SQL = "select t1.china, t2.name, t1.period from horo_chinas_v2 t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_DRUIDS_SQL = "select t1.druid, t2.name, t1.period from horo_druids t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_FLOWERS_SQL = "select t1.flower, t2.name, t1.period from horo_flowers t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_JAPANS_SQL = "select t1.japan, t2.name, t1.period from horo_japans t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";

    @Autowired
    private AmuletsSearchAction amuletsSearchAction;

    @Autowired
    private DreamsSearchAction dreamsSearchAction;

    @Autowired
    private NamesSearchAction namesSearchAction;

    @Autowired
    private SimpleSearchAction simpleSearchAction;

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
        this.simpleSearchAction.setSql(SEARCH_CHINAS_SQL);
        this.simpleSearchAction.setLocale(locale);
        return this.simpleSearchAction.execute();
    }

    public F.Promise<Result> druids() {
        return druidsByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> druidsByLocale(final String locale) {
        this.simpleSearchAction.setSql(SEARCH_DRUIDS_SQL);
        this.simpleSearchAction.setLocale(locale);
        return this.simpleSearchAction.execute();
    }

    public F.Promise<Result> flowers() {
        return flowersByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> flowersByLocale(final String locale) {
        this.simpleSearchAction.setSql(SEARCH_FLOWERS_SQL);
        this.simpleSearchAction.setLocale(locale);
        return this.simpleSearchAction.execute();
    }

    public F.Promise<Result> japans() {
        return japansByLocale(DEFAULT_LOCALE);
    }

    public F.Promise<Result> japansByLocale(final String locale) {
        this.simpleSearchAction.setSql(SEARCH_JAPANS_SQL);
        this.simpleSearchAction.setLocale(locale);
        return this.simpleSearchAction.execute();
    }
}
