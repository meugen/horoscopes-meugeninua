package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.DreamItemDto;
import ua.meugen.horoscopes.entities.Dream;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class DreamItemToDtoFetcher implements EntityToDtoFetcher<Dream, DreamItemDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DreamItemDto fetchEntityToDto(final Dream dream) {
        final DreamItemDto dto = new DreamItemDto();
        dto.setId(dream.getId());
        dto.setType(dream.getType());
        dto.setDream(dream.getDream());
        return dto;
    }
}
