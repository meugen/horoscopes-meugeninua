package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.entities.Druid;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class DruidContentToDtoFetcher implements EntityToDtoFetcher<Druid, BaseContentDto> {

    @Override
    public BaseContentDto fetchEntityToDto(final Druid druid) {
        final BaseContentDto dto = new BaseContentDto();
        dto.setText(druid.getContent());
        return dto;
    }
}
