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

    public static ControllerHelper forDruids(final String lang) {
        return new DruidsTranslateHelper(lang);
    }

    public static ControllerHelper forJapans(final String lang) {
        return new JapansTranslateHelper(lang);
    }

    public static ControllerHelper forChinas(final String lang) {
        return new ChinasTranslateHelper(lang);
    }

    public static ControllerHelper forDreams(final String lang, final int max) {
        return new DreamsTranslateHelper(lang, max);
    }

    public static ControllerHelper forAmulets(final String lang, final int max) {
        return new AmuletsTranslateHelper(lang, max);
    }
}
