package ua.meugen.horoscopes.actions.actions.content.search;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;
import ua.meugen.horoscopes.queries.QueryBuilder;

import java.util.stream.Collectors;

abstract class AbstractInterpretationSearchAction<Entity, Dto>
        extends AbstractJsonControllerAction<SearchInterpretationRequest> {

    private static final Logger.ALogger LOG = Logger.of(AbstractInterpretationSearchAction.class);

    private final ControllerResponsesFactory<ItemsResponse<Dto>> factory;

    /**
     * Constructor.
     */
    protected AbstractInterpretationSearchAction() {
        super(SearchInterpretationRequest.class);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    /**
     * Create new response.
     *
     * @return Response
     */
    protected abstract ItemsResponse<Dto> newResponse();

    protected final Result action(final QueryBuilder<Entity, SearchInterpretationRequest> builder,
                                  final EntityToDtoFetcher<Entity, Dto> fetcher,
                                  final SearchInterpretationRequest request) {
        final ItemsResponse<Dto> response = internalAction(builder, fetcher, request);
        return Controller.ok(response.asJson());
    }

    private ItemsResponse<Dto> internalAction(
            final QueryBuilder<Entity, SearchInterpretationRequest> builder,
            final EntityToDtoFetcher<Entity, Dto> fetcher,
            final SearchInterpretationRequest request) {
        final ItemsResponse<Dto> response = this.factory.newOkResponse();
        response.setItems(builder.build(request).findList().stream()
                .map(fetcher::fetchEntityToDto).collect(Collectors.toList()));
        return response;
    }
}
