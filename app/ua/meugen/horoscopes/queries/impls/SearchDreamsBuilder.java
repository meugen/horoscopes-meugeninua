package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.entities.Dream;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchDreamsBuilder implements QueryBuilder<Dream, SearchInterpretationRequest> {

    @Inject
    private Model.Find<Integer, Dream> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Dream> build(final SearchInterpretationRequest request) {
        return find.select("type, dream").where().eq("locale", request.getLocale())
                .startsWith("updream", request.getSearch().toUpperCase())
                .query();
    }
}
