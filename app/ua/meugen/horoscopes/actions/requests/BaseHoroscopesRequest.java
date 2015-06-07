package ua.meugen.horoscopes.actions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meugen on 07.06.15.
 */
public class BaseHoroscopesRequest {

    @JsonProperty(required = true)
    private String sign;
    private String locale = "ru";
    private List<String> periods = new ArrayList<>();

    public final String getSign() {
        return sign;
    }

    public final void setSign(String sign) {
        this.sign = sign;
    }

    public final String getLocale() {
        return locale;
    }

    public final void setLocale(String locale) {
        this.locale = locale;
    }

    public final List<String> getPeriods() {
        return periods;
    }

    public final void setPeriods(List<String> periods) {
        this.periods = periods;
    }
}
