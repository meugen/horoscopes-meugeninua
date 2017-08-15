package ua.meugen.horoscopes.controllers.content;

import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.content.image.ImageIndexAction;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;

@Singleton
public final class Image {

    @Inject
    private ImageIndexAction imageIndexAction;

    public CompletionStage<Result> index(final String name) {
        return this.imageIndexAction.execute(name);
    }
}
