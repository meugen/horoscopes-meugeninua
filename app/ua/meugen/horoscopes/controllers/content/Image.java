package ua.meugen.horoscopes.controllers.content;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.image.ImageIndexAction;

import javax.inject.Inject;

public final class Image {

    @Inject
    private ImageIndexAction imageIndexAction;

    public F.Promise<Result> index(final String name) {
        return this.imageIndexAction.execute(name);
    }
}
