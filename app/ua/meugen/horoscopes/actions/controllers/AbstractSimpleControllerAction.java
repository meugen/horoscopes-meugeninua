package ua.meugen.horoscopes.actions.controllers;

import play.libs.F;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.responses.BaseResponse;

/**
 * Created by admin on 23.10.2014.
 */
public abstract class AbstractSimpleControllerAction {

    /**
     * {@inheritDoc}
     */
    public final F.Promise<Result> execute() {
        return F.Promise.promise(this::action);
    }

    /**
     * Do action and return result.
     *
     * @return Result
     */
    protected abstract Result action();
}
