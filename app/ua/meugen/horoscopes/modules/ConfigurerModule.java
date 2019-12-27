package ua.meugen.horoscopes.modules;

import com.google.inject.AbstractModule;
import ua.meugen.horoscopes.configs.Configurer;

public class ConfigurerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Configurer.class).asEagerSingleton();
    }
}
