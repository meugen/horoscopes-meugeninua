package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.entities.China;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class ChinaContentToDtoFetcher implements EntityToDtoFetcher<China, BaseContentDto> {

    @Override
    public BaseContentDto fetchEntityToDto(final China china) {
        final BaseContentDto dto = new BaseContentDto();
        dto.setText(china.getContent());
        return dto;
    }
}
