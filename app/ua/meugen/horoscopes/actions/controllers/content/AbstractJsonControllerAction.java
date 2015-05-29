package ua.meugen.horoscopes.actions.controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import ua.meugen.horoscopes.actions.controllers.AbstractControllerAction;
import play.mvc.Result;

/**
 * Created by admin on 23.10.2014.
 */
public abstract class AbstractJsonControllerAction extends AbstractControllerAction {

    private final JsonNode json;

    /**
     * Constructor.
     *
     * @param json Json
     */
    protected AbstractJsonControllerAction(final JsonNode json) {
        this.json = json;
    }

    /**
     * {@inheritDoc}
     */
    protected final Result action() {
        final CheckJsonHelper helper = new CheckJsonHelper(this.json, this.getNotNullFields()) {
            protected Result onJsonValid(final JsonNode json) {
                return AbstractJsonControllerAction.this.action(json);
            }
        };
        return helper.check();
    }

    /**
     * Action with valid json.
     *
     * @param json Valid json
     * @return Response
     */
    protected abstract Result action(final JsonNode json);

    /**
     * Getter for not null fields in json.
     *
     * @return Not null fields in json
     */
    protected abstract String[] getNotNullFields();
}
