package ua.meugen.horoscopes.actions.actions.content.get;

import com.google.inject.Inject;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.requests.KeysRequest;
import ua.meugen.horoscopes.actions.responses.KeysResponse;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;

import java.util.stream.Collectors;

public final class GetKeysAction extends AbstractJsonControllerAction<KeysRequest> {

    private static final Logger.ALogger LOG = Logger.of(GetKeysAction.class);

    private final ControllerResponsesFactory<KeysResponse> factory;

    @Inject
    private QueryBuilder<Period, KeysRequest> builder;

    /**
     * Default constructor.
     */
    public GetKeysAction() {
        super(KeysRequest.class);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    private KeysResponse newResponse() {
        return new KeysResponse();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final KeysRequest request) {
        final KeysResponse response = internalAction(request);
        return Controller.ok(response.asJson());
    }

    private KeysResponse internalAction(final KeysRequest request) {
        final KeysResponse response = factory.newOkResponse();
        response.setKeys(builder.build(request).findList().stream()
                .map(Period::getKey).collect(Collectors.toList()));
        return response;
    }
}
