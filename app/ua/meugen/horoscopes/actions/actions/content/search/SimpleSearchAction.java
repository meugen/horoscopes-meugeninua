package ua.meugen.horoscopes.actions.actions.content.search;

import play.Logger;
import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.actions.actions.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.dto.SimpleDto;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;
import ua.meugen.horoscopes.queries.QueryBuilder;

import java.util.stream.Collectors;

public final class SimpleSearchAction<Entity> extends AbstractJsonControllerAction<String> {

    private static final Logger.ALogger LOG = Logger.of(SimpleSearchAction.class);

    private final ControllerResponsesFactory<ItemsResponse<SimpleDto>> factory;
    private final QueryBuilder<Entity, String> builder;
    private final EntityToDtoFetcher<Entity, SimpleDto> fetcher;

    /**
     * Constructor.
     *
     * @param builder Query builder
     * @param fetcher Entity to dto fetcher
     */
    public SimpleSearchAction(final QueryBuilder<Entity, String> builder,
                              final EntityToDtoFetcher<Entity, SimpleDto> fetcher) {
        super(String.class);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
        this.builder = builder;
        this.fetcher = fetcher;
    }

    /**
     * {@inheritDoc}
     */
    private ItemsResponse<SimpleDto> newResponse() {
        return new ItemsResponse<>();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final String request) {
        final ItemsResponse<SimpleDto> response = internalAction(request);
        return Results.ok(response.asJson());
    }

    private ItemsResponse<SimpleDto> internalAction(final String locale) {
        final ItemsResponse<SimpleDto> response = this.factory.newOkResponse();
        response.setItems(builder.build(locale).findList().stream()
                .map(fetcher::fetchEntityToDto).collect(Collectors.toList()));
        return response;
    }
}
