package controllers.content;

import helpers.controllers.content.image.ImageIndexHelper;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 24.06.14.
 */
public final class Image extends Controller {

    public static F.Promise<Result> index(final String name) {
        return new ImageIndexHelper(name).execute();
    }
}
