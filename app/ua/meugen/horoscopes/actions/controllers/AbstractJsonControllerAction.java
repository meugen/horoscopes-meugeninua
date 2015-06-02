package ua.meugen.horoscopes.actions.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.F;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.responses.BaseResponse;

/**
 * Created by admin on 02.06.2015.
 */
public abstract class AbstractJsonControllerAction<Resp extends BaseResponse, Req>
        extends AbstractControllerResponses<Resp> {

    private final Class<Req> reqClazz;

    /**
     * Constructor.
     * @param reqClazz Request class
     */
    public AbstractJsonControllerAction(final Class<Req> reqClazz) {
        this.reqClazz = reqClazz;
    }

    /**
     * Promise for action with json body.
     * @param json Json body
     * @return Promise result
     */
    public F.Promise<Result> execute(final JsonNode json) {
        return F.Promise.promise(() -> internalAction(json));
    }

    private Result internalAction(final JsonNode json) {
        final Req request = Json.fromJson(json, this.reqClazz);
        return this.action(request);
    }

    /**
     * Action.
     * @param request Request
     * @return Result
     */
    protected abstract Result action(final Req request);
}
