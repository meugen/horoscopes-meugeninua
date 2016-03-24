package ua.meugen.horoscopes.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import play.i18n.Lang;
import play.i18n.Messages;
import play.i18n.MessagesApi;

public final class MessagesModule extends AbstractModule {

    @Override
    protected void configure() {
        // Nothing to do
    }

    @Provides @Named("ru")
    public Messages provideRuMessages(final MessagesApi api) {
        return new Messages(Lang.forCode("ru"), api);
    }
}
