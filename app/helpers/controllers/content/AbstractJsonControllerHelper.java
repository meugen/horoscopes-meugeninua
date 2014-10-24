package helpers.controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by admin on 23.10.2014.
 */
public abstract class AbstractJsonControllerHelper extends AbstractControllerHelper {

    private final JsonNode json;

    /**
     * Constructor.
     *
     * @param json Json
     */
    protected AbstractJsonControllerHelper(final JsonNode json) {
        this.json = json;
    }

    /**
     * {@inheritDoc}
     */
    protected final Result action() {
        boolean valid = this.json != null;
        final String[] notNullFields = this.getNotNullFields();
        for (int i = 0; valid && i < notNullFields.length; i++) {
            valid = this.json.has(notNullFields[i]) && !this.json.get(notNullFields[i]).isNull();
        }
        if (!valid) {
            return Controller.badRequest();
        }
        return this.action(this.json);
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
