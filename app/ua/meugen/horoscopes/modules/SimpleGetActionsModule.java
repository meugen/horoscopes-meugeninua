package ua.meugen.horoscopes.modules;

import com.avaje.ebean.Model;
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
            final Model.Find<Integer, China> find,
            final EntityToDtoFetcher<China, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Dream> newGetDreamAction(
            final Model.Find<Integer, Dream> find,
            final EntityToDtoFetcher<Dream, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Druid> newGetDruidAction(
            final Model.Find<Integer, Druid> find,
            final EntityToDtoFetcher<Druid, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Flower> newGetFlowerAction(
            final Model.Find<Integer, Flower> find,
            final EntityToDtoFetcher<Flower, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Name> newGetNameAction(
            final Model.Find<Integer, Name> find,
            final EntityToDtoFetcher<Name, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }

    @Provides
    public SimpleGetByIdAction<Japan> newGetJapanAction(
            final Model.Find<Integer, Japan> find,
            final EntityToDtoFetcher<Japan, BaseContentDto> fetcher) {
        return new SimpleGetByIdAction<>(find, fetcher);
    }
}
