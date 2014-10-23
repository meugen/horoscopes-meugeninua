package helpers.controllers.content;

import helpers.controllers.ControllerHelper;
import play.libs.F;
import play.mvc.Result;

/**
 * Created by admin on 23.10.2014.
 */
public abstract class AbstractControllerHelper implements ControllerHelper {

    /**
     * {@inheritDoc}
     */
    public final F.Promise<Result> execute() {
        return F.Promise.promise(new F.Function0<Result>() {
            public Result apply() throws Throwable {
                return AbstractControllerHelper.this.action();
            }
        });
    }

    /**
     * Do action and return result.
     * @return Result
     */
    protected abstract Result action();
}
