package ua.meugen.horoscopes.actions.actions;

import ua.meugen.horoscopes.actions.responses.BaseResponse;

/**
 * Created by admin on 02.06.2015.
 */
public final class ControllerResponsesFactory<Resp extends BaseResponse> {

    private final ResponseCreator<Resp> creator;

    /**
     * Constructor.
     *
     * @param creator Response creator
     */
    public ControllerResponsesFactory(final ResponseCreator<Resp> creator) {
        this.creator = creator;
    }

    /**
     * Create response with error.
     *
     * @param throwable Throwable
     * @return Response
     */
    public final Resp newErrorResponse(final Throwable throwable) {
        final Resp response = this.creator.newResponse();
        response.setMessage(throwable.getMessage());
        response.setStatus(BaseResponse.Status.ERROR);
        return response;
    }

    /**
     * Create ok response.
     *
     * @return Response
     */
    public final Resp newOkResponse() {
        final Resp response = this.creator.newResponse();
        response.setMessage("");
        response.setStatus(BaseResponse.Status.OK);
        return response;
    }

    /**
     * Create not found response.
     *
     * @return Response
     */
    public final Resp newNotFoundResponse() {
        final Resp response = this.creator.newResponse();
        response.setMessage("");
        response.setStatus(BaseResponse.Status.NOT_FOUND);
        return response;
    }
}
