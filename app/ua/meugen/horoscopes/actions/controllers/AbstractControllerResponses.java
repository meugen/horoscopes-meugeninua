package ua.meugen.horoscopes.actions.controllers;

import ua.meugen.horoscopes.actions.responses.BaseResponse;

/**
 * Created by admin on 02.06.2015.
 */
public abstract class AbstractControllerResponses<Resp extends BaseResponse> {

    /**
     * Create response with error.
     * @param throwable Throwable
     * @return Response
     */
    protected final Resp newErrorResponse(final Throwable throwable) {
        final Resp response = this.newResponse();
        response.setMessage(throwable.getMessage());
        response.setStatus(BaseResponse.Status.ERROR);
        return response;
    }

    /**
     * Create ok response.
     * @return Response
     */
    protected final Resp newOkResponse() {
        final Resp response = this.newResponse();
        response.setMessage("");
        response.setStatus(BaseResponse.Status.OK);
        return response;
    }

    /**
     * Create new response.
     * @return New response
     */
    protected abstract Resp newResponse();
}
