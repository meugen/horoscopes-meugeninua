package ua.meugen.horoscopes.actions.actions.content.search;

import com.google.inject.Inject;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.dto.AmuletItemDto;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;
import ua.meugen.horoscopes.entities.Amulet;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class AmuletsSearchAction extends AbstractInterpretationSearchAction<Amulet, AmuletItemDto> {

    @Inject
    private QueryBuilder<Amulet, SearchInterpretationRequest> builder;
    @Inject
    private EntityToDtoFetcher<Amulet, AmuletItemDto> fetcher;

    /**
     * {@inheritDoc}
     */
    @Override
    protected Result action(final SearchInterpretationRequest request) {
        return action(builder, fetcher, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ItemsResponse<AmuletItemDto> newResponse() {
        return new ItemsResponse<>();
    }
}
