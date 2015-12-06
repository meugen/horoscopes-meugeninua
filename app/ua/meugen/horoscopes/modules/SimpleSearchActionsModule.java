package ua.meugen.horoscopes.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ua.meugen.horoscopes.actions.actions.content.search.SimpleSearchAction;
import ua.meugen.horoscopes.actions.dto.SimpleDto;
import ua.meugen.horoscopes.entities.China;
import ua.meugen.horoscopes.entities.Druid;
import ua.meugen.horoscopes.entities.Flower;
import ua.meugen.horoscopes.entities.Japan;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SimpleSearchActionsModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // Nothing to do
    }

    @Provides
    public SimpleSearchAction<China> newSearchChinasAction(
            final QueryBuilder<China, String> builder,
            final EntityToDtoFetcher<China, SimpleDto> fetcher) {
        return new SimpleSearchAction<>(builder, fetcher);
    }

    @Provides
    public SimpleSearchAction<Druid> newSearchDruidsAction(
            final QueryBuilder<Druid, String> builder,
            final EntityToDtoFetcher<Druid, SimpleDto> fetcher) {
        return new SimpleSearchAction<>(builder, fetcher);
    }

    @Provides
    public SimpleSearchAction<Flower> newSearchFlowersAction(
            final QueryBuilder<Flower, String> builder,
            final EntityToDtoFetcher<Flower, SimpleDto> fetcher) {
        return new SimpleSearchAction<>(builder, fetcher);
    }

    @Provides
    public SimpleSearchAction<Japan> newSearchJapansAction(
            final QueryBuilder<Japan, String> builder,
            final EntityToDtoFetcher<Japan, SimpleDto> fetcher) {
        return new SimpleSearchAction<>(builder, fetcher);
    }
}
