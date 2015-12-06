package ua.meugen.horoscopes.fetchers;

public interface EntityToDtoFetcher<Entity, Dto> {

    Dto fetchEntityToDto(Entity entity);
}
