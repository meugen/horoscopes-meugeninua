package controllers.content;

import helpers.controllers.content.translate.TranslateHelperFactory;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 14.01.15.
 */
public final class Translate extends Controller {

    public static F.Promise<Result> flowersTo(final String lang) {
        return TranslateHelperFactory.forFlowers(lang).execute();
    }

    public static F.Promise<Result> druidsTo(final String lang) {
        return TranslateHelperFactory.forDruids(lang).execute();
    }

    public static F.Promise<Result> japansTo(final String lang) {
        return TranslateHelperFactory.forJapans(lang).execute();
    }

    public static F.Promise<Result> chinasTo(final String lang) {
        return TranslateHelperFactory.forChinas(lang).execute();
    }

    public static F.Promise<Result> dreamsTo(final String lang, final Integer max) {
        return TranslateHelperFactory.forDreams(lang, max).execute();
    }

    public static F.Promise<Result> amuletsTo(final String lang, final Integer max) {
        return TranslateHelperFactory.forAmulets(lang, max).execute();
    }
}
