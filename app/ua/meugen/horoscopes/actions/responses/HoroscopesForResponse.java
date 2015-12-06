package ua.meugen.horoscopes.actions.responses;

import ua.meugen.horoscopes.actions.dto.HoroscopesForDto;

public final class HoroscopesForResponse extends BaseResponse {

    private HoroscopesForDto content;

    public HoroscopesForDto getContent() {
        return content;
    }

    public void setContent(final HoroscopesForDto content) {
        this.content = content;
    }
}
