package ua.meugen.horoscopes.controllers.portal;

import org.springframework.stereotype.Service;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by admin on 28.10.2014.
 */
@Service
public final class Signin extends Controller {

    public Result index() {
        return ok(views.html.portal.signin.render());
    }
}
