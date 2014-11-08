package helpers.controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.controllers.AbstractControllerHelper;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by admin on 27.10.2014.
 */
public abstract class CheckJsonHelper {

    private final JsonNode json;
    private final String[] fields;

    /**
     * Constructor.
     *
     * @param json   Json to check
     * @param fields Not null fields
     */
    protected CheckJsonHelper(final JsonNode json, final String[] fields) {
        this.json = json;
        this.fields = fields;
    }

    /**
     * Call when json valid.
     *
     * @param json Valid json
     * @return Result
     */
    protected abstract Result onJsonValid(final JsonNode json);

    /**
     * Check.
     *
     * @return Result
     */
    public final Result check() {
        boolean valid = this.json != null;
        for (int i = 0; valid && i < this.fields.length; i++) {
            valid = this.json.has(this.fields[i]) && !this.json.get(this.fields[i]).isNull();
        }
        if (!valid) {
            return Controller.badRequest();
        }
        return onJsonValid(this.json);
    }

    /**
     * Promise check.
     *
     * @return Promise result
     */
    public final F.Promise<Result> promiseCheck() {
        return new AbstractControllerHelper() {
            protected Result action() {
                return CheckJsonHelper.this.check();
            }
        }.execute();
    }
}
