package ua.meugen.horoscopes.modules;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.Finder;
import io.ebean.Model;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ua.meugen.horoscopes.entities.*;

public final class EntitiesModule extends AbstractModule {

    @Override
    protected void configure() {
        // Nothing to do here
    }

    @Provides
    public EbeanServer provideEbeanServer() {
        return Ebean.getServer(null);
    }

    @Provides
    public Finder<Integer, Amulet> provideAmuletFind() {
        return new Finder<>(Amulet.class);
    }

    @Provides
    public Finder<Integer, China> provideChinaFind() {
        return new Finder<>(China.class);
    }

    @Provides
    public Finder<Integer, Dream> provideDreamFind() {
        return new Finder<>(Dream.class);
    }

    @Provides
    public Finder<Integer, Druid> provideDruidFind() {
        return new Finder<>(Druid.class);
    }

    @Provides
    public Finder<Integer, Flower> provideFlowerFind() {
        return new Finder<>(Flower.class);
    }

    @Provides
    public Finder<Integer, Horoscope> provideHoroscopeFind() {
        return new Finder<>(Horoscope.class);
    }

    @Provides
    public Finder<Integer, Japan> provideJapanFind() {
        return new Finder<>(Japan.class);
    }

    @Provides
    public Finder<Integer, Name> provideNameFind() {
        return new Finder<>(Name.class);
    }

    @Provides
    public Finder<Integer, Update> provideUpdateFind() {
        return new Finder<>(Update.class);
    }

    @Provides
    public Finder<Integer, Upload> provideUploadFind() {
        return new Finder<>(Upload.class);
    }

    @Provides
    public Finder<Integer, Period> providePeriodFind() {
        return new Finder<>(Period.class);
    }
}
