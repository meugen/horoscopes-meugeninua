package ua.meugen.horoscopes.actions.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"version"})
public final class HoroscopesRequest extends BaseHoroscopesRequest {

    @JsonProperty(required = true)
    private String type;
    @JsonProperty(required = true)
    private String kind;

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

}
