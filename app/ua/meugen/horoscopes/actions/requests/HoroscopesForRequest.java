package ua.meugen.horoscopes.actions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meugen on 07.06.15.
 */
public final class HoroscopesForRequest {

    @JsonProperty(required = true)
    private String sign;
    private String locale = "ru";
    private List<String> periods = new ArrayList<>();

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<String> getPeriods() {
        return periods;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }
}
