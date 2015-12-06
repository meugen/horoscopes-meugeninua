package ua.meugen.horoscopes.fetchers.impls;

import ua.meugen.horoscopes.actions.dto.SimpleDto;
import ua.meugen.horoscopes.entities.Flower;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class FlowerItemToDtoFetcher implements EntityToDtoFetcher<Flower, SimpleDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SimpleDto fetchEntityToDto(final Flower flower) {
        final SimpleDto dto = new SimpleDto();
        dto.setId(flower.getId());
        dto.setName(flower.getFlower());
        dto.setIcon(flower.getIcon().getName());
        dto.setPeriod(flower.getPeriod());
        return dto;
    }
}
