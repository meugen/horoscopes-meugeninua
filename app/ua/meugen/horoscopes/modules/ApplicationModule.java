package ua.meugen.horoscopes.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import play.Configuration;

/**
 * @author meugen
 */
public class ApplicationModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // Nothing to do
    }

    @Provides @Named("html.obfuscate")
    public Boolean provideHtmlObfuscate(final Configuration configuration) {
        return configuration.getBoolean("horoscopes.html.obfuscate", Boolean.TRUE);
    }
}
