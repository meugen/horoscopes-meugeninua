package controllers.content;

import helpers.controllers.content.translate.TranslateHelperFactory;
import play.mvc.Result;
import play.libs.F;
import play.mvc.Controller;

/**
 * Created by meugen on 14.01.15.
 */
public final class Translate extends Controller {

    public static F.Promise<Result> flowersTo(final String lang) {
        return TranslateHelperFactory.forFlowers(lang).execute();
    }
}
