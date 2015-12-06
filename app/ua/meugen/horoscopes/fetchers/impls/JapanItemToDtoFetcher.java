package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.SimpleDto;
import ua.meugen.horoscopes.entities.Japan;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class JapanItemToDtoFetcher implements EntityToDtoFetcher<Japan, SimpleDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SimpleDto fetchEntityToDto(final Japan japan) {
        final SimpleDto dto = new SimpleDto();
        dto.setId(japan.getId());
        dto.setIcon(japan.getIcon().getName());
        dto.setName(japan.getJapan());
        dto.setPeriod(japan.getPeriod());
        return dto;
    }
}
