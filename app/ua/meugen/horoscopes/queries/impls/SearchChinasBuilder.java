package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.China;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchChinasBuilder implements QueryBuilder<China, String> {

    @Inject
    private Model.Find<Integer, China> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<China> build(final String request) {
        return find.select("china, icon.name, period").where().eq("locale", request).orderBy("sort");
    }
}
