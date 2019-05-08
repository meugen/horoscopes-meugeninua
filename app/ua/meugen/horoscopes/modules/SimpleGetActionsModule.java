package ua.meugen.horoscopes.modules;

import io.ebean.Finder;
import io.ebean.Model;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ua.meugen.horoscopes.actions.actions.content.get.SimpleGetByIdAction;
import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.entities.*;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class SimpleGetActionsModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // Nothing to do
    }

    @Provides
    public SimpleGetByIdAction<China> newGetChinaAction(
            final Finder<Integer, China> find,
            final EntityToDtoFetcher<China, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Dream> newGetDreamAction(
            final Finder<Integer, Dream> find,
            final EntityToDtoFetcher<Dream, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Druid> newGetDruidAction(
            final Finder<Integer, Druid> find,
            final EntityToDtoFetcher<Druid, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Flower> newGetFlowerAction(
            final Finder<Integer, Flower> find,
            final EntityToDtoFetcher<Flower, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Name> newGetNameAction(
            final Finder<Integer, Name> find,
            final EntityToDtoFetcher<Name, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Japan> newGetJapanAction(
            final Finder<Integer, Japan> find,
            final EntityToDtoFetcher<Japan, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }
}
