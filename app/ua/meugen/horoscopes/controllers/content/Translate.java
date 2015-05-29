package ua.meugen.horoscopes.controllers.content;

import org.springframework.stereotype.Service;
import ua.meugen.horoscopes.actions.controllers.content.translate.TranslateHelperFactory;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 14.01.15.
 */
@Service
public final class Translate extends Controller {

    public F.Promise<Result> flowersTo(final String lang) {
        return TranslateHelperFactory.forFlowers(lang).execute();
    }

    public F.Promise<Result> druidsTo(final String lang) {
        return TranslateHelperFactory.forDruids(lang).execute();
    }

    public F.Promise<Result> japansTo(final String lang) {
        return TranslateHelperFactory.forJapans(lang).execute();
    }

    public F.Promise<Result> chinasTo(final String lang) {
        return TranslateHelperFactory.forChinas(lang).execute();
    }

    public F.Promise<Result> dreamsTo(final String lang, final Integer max) {
        return TranslateHelperFactory.forDreams(lang, max).execute();
    }

    public F.Promise<Result> amuletsTo(final String lang, final Integer max) {
        return TranslateHelperFactory.forAmulets(lang, max).execute();
    }
}
