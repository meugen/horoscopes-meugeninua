package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Japan;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchJapansBuilder implements QueryBuilder<Japan, String> {

    @Inject
    private Model.Find<Integer, Japan> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Japan> build(final String request) {
        return find.select("japan, icon.name, period").where().eq("locale", request).orderBy("sort");
    }
}
