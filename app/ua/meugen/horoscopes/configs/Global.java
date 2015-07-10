package ua.meugen.horoscopes.configs;

import play.Application;
import play.GlobalSettings;
import play.inject.Injector;

import java.util.Collections;
import java.util.List;

/**
 * Global settings.
 *
 * @author meugen
 */
public final class Global extends GlobalSettings {

    private final List<Class<? extends Config>> configClasses
            = Collections.singletonList(UpdatesConfig.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart(final Application app) {
        super.onStart(app);

        final Injector injector = app.injector();
        for (Class<? extends Config> clazz : this.configClasses) {
            injector.instanceOf(clazz).configure();
        }
    }
}
