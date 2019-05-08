package ua.meugen.horoscopes.queries.impls;

import io.ebean.Finder;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Japan;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchJapansBuilder implements QueryBuilder<Japan, String> {

    @Inject
    private Finder<Integer, Japan> finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Japan> build(final String request) {
        return finder.query().select("japan, icon.name, period").where().eq("locale", request).orderBy("sort");
    }
}
