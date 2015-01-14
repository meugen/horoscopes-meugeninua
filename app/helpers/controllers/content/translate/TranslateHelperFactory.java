package helpers.controllers.content.translate;

import helpers.controllers.ControllerHelper;

/**
 * Created by meugen on 14.01.15.
 */
public final class TranslateHelperFactory {

    private TranslateHelperFactory() {
        // Nothing to do
    }

    public static ControllerHelper forFlowers(final String lang) {
        return new FlowersTranslateHelper(lang);
    }
}
