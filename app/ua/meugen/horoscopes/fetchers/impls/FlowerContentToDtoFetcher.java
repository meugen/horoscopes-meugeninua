package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.entities.Flower;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class FlowerContentToDtoFetcher implements EntityToDtoFetcher<Flower, BaseContentDto> {

    @Override
    public BaseContentDto fetchEntityToDto(final Flower flower) {
        final BaseContentDto dto = new BaseContentDto();
        dto.setText(flower.getContent());
        return dto;
    }
}
