package ua.meugen.horoscopes.actions.actions;

import ua.meugen.horoscopes.actions.responses.BaseResponse;

/**
 * Created by admin on 05.06.2015.
 */
public interface ResponseCreator<Resp extends BaseResponse> {

    /**
     * Create new response.
     *
     * @return New response
     */
    Resp newResponse();
}
