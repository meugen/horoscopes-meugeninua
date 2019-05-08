package ua.meugen.horoscopes.queries.impls;

import io.ebean.Finder;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Druid;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchDruidsBuilder implements QueryBuilder<Druid, String> {

    @Inject
    private Finder<Integer, Druid> finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Druid> build(final String request) {
        return finder.query().select("druid, icon.name, period").where().eq("locale", request).orderBy("sort");
    }
}
