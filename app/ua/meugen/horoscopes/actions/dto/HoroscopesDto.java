package ua.meugen.horoscopes.actions.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.meugen.horoscopes.actions.actions.serializers.HoroscopesSerializer;

import java.util.Map;

/**
 * Created by admin on 05.06.2015.
 */
@JsonSerialize(using = HoroscopesSerializer.class)
public final class HoroscopesDto {

    private String type;
    private String kind;
    private String sign;
    private Map<String, String> horoscopes;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(final String kind) {
        this.kind = kind;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(final String sign) {
        this.sign = sign;
    }

    public Map<String, String> getHoroscopes() {
        return horoscopes;
    }

    public void setHoroscopes(final Map<String, String> horoscopes) {
        this.horoscopes = horoscopes;
    }
}
