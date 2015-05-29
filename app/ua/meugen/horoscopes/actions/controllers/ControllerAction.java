package ua.meugen.horoscopes.actions.controllers;

import play.libs.F;
import play.mvc.Result;

/**
 * Created by admin on 23.10.2014.
 */
public interface ControllerAction {

    /**
     * Execute controller action.
     *
     * @return Result
     */
    F.Promise<Result> execute();
}
