package ua.meugen.horoscopes.queries.impls;

import io.ebean.Finder;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Flower;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchFlowersBuilder implements QueryBuilder<Flower, String> {

    @Inject
    private Finder<Integer, Flower> finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Flower> build(final String request) {
        return finder.query().select("flower, icon.name, period").where().eq("locale", request).orderBy("sort");
    }
}
