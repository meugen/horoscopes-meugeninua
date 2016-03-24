package ua.meugen.horoscopes.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.inject.Injector;

import java.util.Collections;
import java.util.List;

@Singleton
public final class Configurer {

    @Inject
    public Configurer(final Injector injector) {
        final List<Class<? extends Config>> configClasses
                = Collections.singletonList(UpdatesConfig.class);
        for (Class<? extends Config> clazz : configClasses) {
            injector.instanceOf(clazz).configure();
        }
    }
}
