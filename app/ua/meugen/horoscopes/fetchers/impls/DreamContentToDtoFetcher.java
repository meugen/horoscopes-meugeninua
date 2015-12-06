package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.entities.Dream;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class DreamContentToDtoFetcher implements EntityToDtoFetcher<Dream, BaseContentDto> {

    @Override
    public BaseContentDto fetchEntityToDto(final Dream dream) {
        final BaseContentDto dto = new BaseContentDto();
        dto.setText(dream.getContent());
        return dto;
    }
}
