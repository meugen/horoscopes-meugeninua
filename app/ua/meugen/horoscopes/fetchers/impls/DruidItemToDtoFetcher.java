package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.SimpleDto;
import ua.meugen.horoscopes.entities.Druid;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class DruidItemToDtoFetcher implements EntityToDtoFetcher<Druid, SimpleDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SimpleDto fetchEntityToDto(final Druid druid) {
        final SimpleDto dto = new SimpleDto();
        dto.setId(druid.getId());
        dto.setIcon(druid.getIcon().getName());
        dto.setPeriod(druid.getPeriod());
        dto.setName(druid.getDruid());
        return dto;
    }
}
