package ua.meugen.horoscopes.actions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 05.06.2015.
 */
public class HoroscopesRequest {

    private String locale = "ru";
    private int version = 1;
    @JsonProperty(required = true)
    private String type;
    @JsonProperty(required = true)
    private String kind;
    @JsonProperty(required = true)
    private String sign;
    private List<String> periods = new ArrayList<>();

    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

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

    public List<String> getPeriods() {
        return periods;
    }

    public void setPeriods(final List<String> periods) {
        this.periods = periods;
    }
}
