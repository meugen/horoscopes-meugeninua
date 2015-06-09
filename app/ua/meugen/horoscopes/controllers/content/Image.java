package ua.meugen.horoscopes.controllers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.meugen.horoscopes.actions.controllers.content.image.ImageIndexAction;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 24.06.14.
 */
@Service
public final class Image extends Controller {

    @Autowired
    private ImageIndexAction imageIndexAction;

    public F.Promise<Result> index(final String name) {
        return this.imageIndexAction.execute(name);
    }
}
