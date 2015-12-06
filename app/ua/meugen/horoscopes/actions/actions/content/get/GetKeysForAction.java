package ua.meugen.horoscopes.actions.actions.content.get;

import com.google.inject.Inject;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.AbstractSimpleControllerAction;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.responses.KeysForResponse;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;

import java.util.stream.Collectors;

public final class GetKeysForAction extends AbstractSimpleControllerAction {

    private static final Logger.ALogger LOG = Logger.of(GetKeysForAction.class);

    private final ControllerResponsesFactory<KeysForResponse> factory;

    @Inject
    private QueryBuilder<Period, Void> builder;

    /**
     * Default constructor.
     */
    public GetKeysForAction() {
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    private KeysForResponse newResponse() {
        return new KeysForResponse();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action() {
        final KeysForResponse response = internalAction();
        return Controller.ok(response.asJson());
    }

    private KeysForResponse internalAction() {
        final KeysForResponse response = factory.newOkResponse();
        response.setKeys(builder.build(null).findList().stream()
                .collect(Collectors.toMap(Period::getType, Period::getKey)));
        return response;
    }
}
