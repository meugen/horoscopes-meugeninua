package ua.meugen.horoscopes.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import ua.meugen.horoscopes.actions.dto.*;
import ua.meugen.horoscopes.entities.*;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;
import ua.meugen.horoscopes.fetchers.impls.*;

public final class FetcherModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        bind(new TypeLiteral<EntityToDtoFetcher<Amulet, AmuletContentDto>>(){})
                .to(AmuletContentToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<China, BaseContentDto>>(){})
                .to(ChinaContentToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<Dream, BaseContentDto>>(){})
                .to(DreamContentToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<Druid, BaseContentDto>>(){})
                .to(DruidContentToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<Flower, BaseContentDto>>(){})
                .to(FlowerContentToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<Japan, BaseContentDto>>(){})
                .to(JapanContentToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<Name, BaseContentDto>>(){})
                .to(NameContentToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<Amulet, AmuletItemDto>>(){})
                .to(AmuletItemToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<Dream, DreamItemDto>>(){})
                .to(DreamItemToDtoFetcher.class);
        bind(new TypeLiteral<EntityToDtoFetcher<Name, NameItemDto>>(){})
                .to(NameItemToDtoFetcher.class);
    }
}
