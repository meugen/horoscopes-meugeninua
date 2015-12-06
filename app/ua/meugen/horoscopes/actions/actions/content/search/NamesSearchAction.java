package ua.meugen.horoscopes.actions.actions.content.search;

import com.google.inject.Inject;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.dto.NameItemDto;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;
import ua.meugen.horoscopes.entities.Name;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class NamesSearchAction extends AbstractInterpretationSearchAction<Name, NameItemDto> {

    @Inject
    private QueryBuilder<Name, SearchInterpretationRequest> builder;
    @Inject
    private EntityToDtoFetcher<Name, NameItemDto> fetcher;

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
    protected ItemsResponse<NameItemDto> newResponse() {
        return new ItemsResponse<>();
    }
}
