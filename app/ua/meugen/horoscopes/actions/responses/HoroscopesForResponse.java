package ua.meugen.horoscopes.actions.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.meugen.horoscopes.actions.controllers.serializers.HoroscopesForSerializer;
import ua.meugen.horoscopes.actions.dto.HoroscopesForDto;

/**
 * Created by meugen on 07.06.15.
 */
public final class HoroscopesForResponse extends BaseResponse {

    private HoroscopesForDto content;

    public HoroscopesForDto getContent() {
        return content;
    }

    public void setContent(final HoroscopesForDto content) {
        this.content = content;
    }
}
