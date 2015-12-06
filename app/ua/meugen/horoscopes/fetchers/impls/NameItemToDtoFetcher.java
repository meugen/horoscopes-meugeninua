package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.NameItemDto;
import ua.meugen.horoscopes.entities.Name;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class NameItemToDtoFetcher implements EntityToDtoFetcher<Name, NameItemDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public NameItemDto fetchEntityToDto(final Name name) {
        final NameItemDto dto = new NameItemDto();
        dto.setId(name.getId());
        dto.setSex(name.getSex());
        dto.setName(name.getName());
        return dto;
    }
}
