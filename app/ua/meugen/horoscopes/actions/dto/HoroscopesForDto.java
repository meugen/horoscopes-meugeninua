package ua.meugen.horoscopes.actions.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.meugen.horoscopes.actions.controllers.serializers.HoroscopesForSerializer;

import java.util.List;

/**
 * Created by meugen on 07.06.15.
 */
@JsonSerialize(using = HoroscopesForSerializer.class)
public final class HoroscopesForDto {

    private String sign;
    private List<Container> items;

    public String getSign() {
        return sign;
    }

    public void setSign(final String sign) {
        this.sign = sign;
    }

    public List<Container> getItems() {
        return items;
    }

    public void setItems(final List<Container> items) {
        this.items = items;
    }

    public static class Container {

        private String type;
        private String kind;
        private String period;
        private String horoscope;

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

        public String getHoroscope() {
            return horoscope;
        }

        public void setHoroscope(final String horoscope) {
            this.horoscope = horoscope;
        }
    }
}
