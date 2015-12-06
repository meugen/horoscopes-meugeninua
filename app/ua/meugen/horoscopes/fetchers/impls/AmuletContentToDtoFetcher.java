package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.AmuletContentDto;
import ua.meugen.horoscopes.entities.Amulet;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class AmuletContentToDtoFetcher implements EntityToDtoFetcher<Amulet, AmuletContentDto> {

    @Override
    public AmuletContentDto fetchEntityToDto(final Amulet amulet) {
        final AmuletContentDto dto = new AmuletContentDto();
        dto.setImage(amulet.getImage().getName());
        dto.setText(amulet.getContent());
        return dto;
    }
}
