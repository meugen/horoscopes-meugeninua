package ua.meugen.horoscopes.actions.actions.content.search;

import com.google.inject.Inject;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.dto.DreamItemDto;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;
import ua.meugen.horoscopes.entities.Dream;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class DreamsSearchAction extends AbstractInterpretationSearchAction<Dream, DreamItemDto> {

    @Inject
    private QueryBuilder<Dream, SearchInterpretationRequest> builder;
    @Inject
    private EntityToDtoFetcher<Dream, DreamItemDto> fetcher;

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
    protected ItemsResponse<DreamItemDto> newResponse() {
        return new ItemsResponse<>();
    }
}
