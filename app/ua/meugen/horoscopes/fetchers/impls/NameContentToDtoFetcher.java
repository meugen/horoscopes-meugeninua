package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.entities.Name;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class NameContentToDtoFetcher implements EntityToDtoFetcher<Name, BaseContentDto> {

    @Override
    public BaseContentDto fetchEntityToDto(final Name name) {
        final BaseContentDto dto = new BaseContentDto();
        dto.setText(name.getContent());
        return dto;
    }
}
