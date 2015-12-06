package ua.meugen.horoscopes.modules;

import com.avaje.ebean.Model;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ua.meugen.horoscopes.entities.*;

public final class EntityModule extends AbstractModule {

    @Override
    protected void configure() {
        // Nothing to do here
    }

    @Provides
    public Model.Find<Integer, Amulet> provideAmuletFind() {
        return new Model.Finder<>(Amulet.class);
    }

    @Provides
    public Model.Find<Integer, China> provideChinaFind() {
        return new Model.Finder<>(China.class);
    }

    @Provides
    public Model.Find<Integer, Dream> provideDreamFind() {
        return new Model.Finder<>(Dream.class);
    }

    @Provides
    public Model.Find<Integer, Druid> provideDruidFind() {
        return new Model.Finder<>(Druid.class);
    }

    @Provides
    public Model.Find<Integer, Flower> provideFlowerFind() {
        return new Model.Finder<>(Flower.class);
    }

    @Provides
    public Model.Find<Integer, Horoscope> provideHoroscopeFind() {
        return new Model.Finder<>(Horoscope.class);
    }

    @Provides
    public Model.Find<Integer, Japan> provideJapanFind() {
        return new Model.Finder<>(Japan.class);
    }

    @Provides
    public Model.Find<Integer, Name> provideNameFind() {
        return new Model.Finder<>(Name.class);
    }

    @Provides
    public Model.Find<Integer, Update> provideUpdateFind() {
        return new Model.Finder<>(Update.class);
    }

    @Provides
    public Model.Find<Integer, Upload> provideUploadFind() {
        return new Model.Finder<>(Upload.class);
    }

    @Provides
    public Model.Find<Integer, Period> providePeriodFind() {
        return new Model.Finder<>(Period.class);
    }
}
