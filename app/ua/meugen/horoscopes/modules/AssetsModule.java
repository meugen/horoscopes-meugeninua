package ua.meugen.horoscopes.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import play.Application;
import ua.meugen.horoscopes.actions.actions.application.ApplicationAssetsAction;

public final class AssetsModule extends AbstractModule {

    @Provides @Named("js") @Singleton
    public ApplicationAssetsAction provideJsAction(final Application app) {
        return ApplicationAssetsAction.forJs(app);
    }

    @Provides @Named("css") @Singleton
    public ApplicationAssetsAction provideCssAction(final Application app) {
        return ApplicationAssetsAction.forCss(app);
    }
}
