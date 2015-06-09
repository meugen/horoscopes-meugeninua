package ua.meugen.horoscopes.actions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by meugen on 08.06.15.
 */
public final class KeysRequest {

    @JsonProperty(required = true)
    private String type;
    private int version = 1;
    private String period;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(final String period) {
        this.period = period;
    }
}
