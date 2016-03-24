package ua.meugen.horoscopes.actions.actions;

import play.libs.F;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by admin on 23.10.2014.
 */
public abstract class AbstractSimpleControllerAction {

    /**
     * {@inheritDoc}
     */
    public final CompletionStage<Result> execute() {
        return CompletableFuture.supplyAsync(this::action);
    }

    /**
     * Do action and return result.
     *
     * @return Result
     */
    protected abstract Result action();
}
