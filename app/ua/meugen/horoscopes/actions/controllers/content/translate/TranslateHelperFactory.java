package ua.meugen.horoscopes.actions.controllers.content.translate;

import ua.meugen.horoscopes.actions.controllers.ControllerAction;

/**
 * Created by meugen on 14.01.15.
 */
public final class TranslateHelperFactory {

    private TranslateHelperFactory() {
        // Nothing to do
    }

    public static ControllerAction forFlowers(final String lang) {
        return new FlowersTranslateAction(lang);
    }

    public static ControllerAction forDruids(final String lang) {
        return new DruidsTranslateAction(lang);
    }

    public static ControllerAction forJapans(final String lang) {
        return new JapansTranslateAction(lang);
    }

    public static ControllerAction forChinas(final String lang) {
        return new ChinasTranslateAction(lang);
    }

    public static ControllerAction forDreams(final String lang, final int max) {
        return new DreamsTranslateAction(lang, max);
    }

    public static ControllerAction forAmulets(final String lang, final int max) {
        return new AmuletsTranslateAction(lang, max);
    }
}
