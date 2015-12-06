package ua.meugen.horoscopes.actions.actions;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;

public abstract class AbstractJsonControllerAction<Req> {

    private final Class<Req> reqClazz;

    /**
     * Constructor.
     *
     * @param reqClazz Request class
     */
    public AbstractJsonControllerAction(final Class<Req> reqClazz) {
        this.reqClazz = reqClazz;
    }

    /**
     * Promise for action with json body.
     *
     * @param json Json body
     * @return Promise result
     */
    public F.Promise<Result> execute(final JsonNode json) {
        return F.Promise.promise(() -> internalAction(json));
    }

    /**
     * Promise for action with request.
     *
     * @param request Request
     * @return Promise result
     */
    public F.Promise<Result> execute(final Req request) {
        return F.Promise.promise(() -> action(request));
    }

    Result internalAction(final JsonNode json) {
        final Req request = Json.fromJson(json, this.reqClazz);
        return this.action(request);
    }

    /**
     * Action.
     *
     * @param request Request
     * @return Result
     */
    protected abstract Result action(final Req request);
}
