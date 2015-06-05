package ua.meugen.horoscopes.actions.responses;

import ua.meugen.horoscopes.actions.dto.HoroscopesDto;

/**
 * Created by admin on 05.06.2015.
 */
public class HoroscopesResponse extends BaseResponse {

    private HoroscopesDto content;

    /**
     * Getter for content.
     * @return Content
     */
    public HoroscopesDto getContent() {
        return content;
    }

    /**
     * Setter for content.
     * @param content Content
     */
    public void setContent(final HoroscopesDto content) {
        this.content = content;
    }
}
