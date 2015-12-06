package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Flower;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchFlowersBuilder implements QueryBuilder<Flower, String> {

    @Inject
    private Model.Find<Integer, Flower> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Flower> build(final String request) {
        return find.select("flower, icon.name, period").where().eq("locale", request).orderBy("sort");
    }
}
