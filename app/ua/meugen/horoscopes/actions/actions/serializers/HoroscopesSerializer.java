package ua.meugen.horoscopes.actions.actions.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ua.meugen.horoscopes.actions.dto.HoroscopesDto;

import java.io.IOException;
import java.util.Map;

/**
 * Created by admin on 05.06.2015.
 */
public final class HoroscopesSerializer extends JsonSerializer<HoroscopesDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(final HoroscopesDto value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeObjectFieldStart(value.getType());
        jgen.writeObjectFieldStart(value.getKind());
        jgen.writeObjectFieldStart(value.getSign());
        for (Map.Entry<String, String> entry : value.getHoroscopes().entrySet()) {
            jgen.writeStringField(entry.getKey(), entry.getValue());
        }
        jgen.writeEndObject();
        jgen.writeEndObject();
        jgen.writeEndObject();
    }
}
