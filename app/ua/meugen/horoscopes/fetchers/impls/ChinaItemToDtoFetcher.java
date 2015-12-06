package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.SimpleDto;
import ua.meugen.horoscopes.entities.China;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class ChinaItemToDtoFetcher implements EntityToDtoFetcher<China, SimpleDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SimpleDto fetchEntityToDto(final China china) {
        final SimpleDto dto = new SimpleDto();
        dto.setId(china.getId());
        dto.setName(china.getChina());
        dto.setPeriod(china.getPeriod());
        dto.setIcon(china.getIcon().getName());
        return dto;
    }
}
