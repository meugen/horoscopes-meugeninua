package ua.meugen.horoscopes.actions.actions.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ua.meugen.horoscopes.actions.dto.HoroscopesForDto;

import java.io.IOException;

public final class HoroscopesForSerializer extends JsonSerializer<HoroscopesForDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(final HoroscopesForDto horoscopesForDto, final JsonGenerator generator,
                          final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        for (HoroscopesForDto.Container container : horoscopesForDto.getItems()) {
            generator.writeObjectFieldStart(container.getType());
            generator.writeObjectFieldStart(container.getKind());
            generator.writeObjectFieldStart(horoscopesForDto.getSign());
            generator.writeStringField(container.getPeriod(), container.getHoroscope());
            generator.writeEndObject();
            generator.writeEndObject();
            generator.writeEndObject();
        }
    }
}
