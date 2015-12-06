package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.entities.Japan;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class JapanContentToDtoFetcher implements EntityToDtoFetcher<Japan, BaseContentDto> {

    @Override
    public BaseContentDto fetchEntityToDto(final Japan japan) {
        final BaseContentDto dto = new BaseContentDto();
        dto.setText(japan.getContent());
        return dto;
    }
}
