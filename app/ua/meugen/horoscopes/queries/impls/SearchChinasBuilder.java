package ua.meugen.horoscopes.queries.impls;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.China;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchChinasBuilder implements QueryBuilder<China, String> {

    @Inject
    private Finder<Integer, China> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<China> build(final String request) {
        return find.query().select("china, icon.name, period").where().eq("locale", request).orderBy("sort");
    }
}
