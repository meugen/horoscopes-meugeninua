package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.controllers.ControllerHelper;

/**
 * Created by admin on 23.10.2014.
 */
public final class GetHelpersFactory {

    private GetHelpersFactory() {}

    public static ControllerHelper newGetAmuletHelper(final JsonNode json) {
        return new GetAmuletHelper(json);
    }

    public static ControllerHelper newGetChinaHelper(final JsonNode json) {
        return new GetChinaHelper(json);
    }

    public static ControllerHelper newGetDreamHelper(final JsonNode json) {
        return new GetDreamHelper(json);
    }

    public static ControllerHelper newGetDruidHelper(final JsonNode json) {
        return new GetDruidHelper(json);
    }
}
