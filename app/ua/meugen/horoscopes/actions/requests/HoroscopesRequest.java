package ua.meugen.horoscopes.actions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by admin on 05.06.2015.
 */
public final class HoroscopesRequest extends BaseHoroscopesRequest {

    private int version = 1;
    @JsonProperty(required = true)
    private String type;
    @JsonProperty(required = true)
    private String kind;
    private String period;

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(final String period) {
        this.period = period;
    }
}
