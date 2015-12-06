package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Druid;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchDruidsBuilder implements QueryBuilder<Druid, String> {

    @Inject
    private Model.Find<Integer, Druid> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Druid> build(final String request) {
        return find.select("druid, icon.name, period").where().eq("locale", request).orderBy("sort");
    }
}
