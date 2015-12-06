package ua.meugen.horoscopes.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.actions.requests.HoroscopesRequest;
import ua.meugen.horoscopes.actions.requests.KeysRequest;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.entities.*;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.queries.impls.*;

public final class QueryBuilderModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        bind(new TypeLiteral<QueryBuilder<Horoscope, HoroscopesRequest>>(){})
                .annotatedWith(Names.named("translate"))
                .to(TranslateHoroscopesBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Horoscope, HoroscopesRequest>>(){})
                .annotatedWith(Names.named("get"))
                .to(GetHoroscopesBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Horoscope, BaseHoroscopesRequest>>(){})
                .annotatedWith(Names.named("translate"))
                .to(TranslateHoroscopesForBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Horoscope, BaseHoroscopesRequest>>(){})
                .annotatedWith(Names.named("get"))
                .to(GetHoroscopesForBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Period, KeysRequest>>(){})
                .to(GetKeysBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Period, Void>>(){})
                .to(GetKeysForBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Upload, String>>(){})
                .to(GetMimeBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Amulet, SearchInterpretationRequest>>(){})
                .to(SearchAmuletsBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Dream, SearchInterpretationRequest>>(){})
                .to(SearchDreamsBuilder.class);
        bind(new TypeLiteral<QueryBuilder<Name, SearchInterpretationRequest>>(){})
                .to(SearchNamesBuilder.class);
    }
}
