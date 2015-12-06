package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.AmuletItemDto;
import ua.meugen.horoscopes.entities.Amulet;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class AmuletItemToDtoFetcher implements EntityToDtoFetcher<Amulet, AmuletItemDto> {

    @Override
    public AmuletItemDto fetchEntityToDto(final Amulet amulet) {
        final AmuletItemDto dto = new AmuletItemDto();
        dto.setId(amulet.getId());
        dto.setType(amulet.getType());
        dto.setAmulet(amulet.getAmulet());
        return dto;
    }
}
