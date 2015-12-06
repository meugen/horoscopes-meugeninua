package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.entities.Name;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchNamesBuilder implements QueryBuilder<Name, SearchInterpretationRequest> {

    @Inject
    private Model.Find<Integer, Name> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Name> build(final SearchInterpretationRequest request) {
        return find.select("sex, name").where().eq("locale", request.getLocale())
                .startsWith("upname", request.getSearch().toUpperCase())
                .query();
    }
}
