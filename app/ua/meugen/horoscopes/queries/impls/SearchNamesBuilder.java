package ua.meugen.horoscopes.queries.impls;

import io.ebean.Finder;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.entities.Name;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchNamesBuilder implements QueryBuilder<Name, SearchInterpretationRequest> {

    @Inject
    private Finder<Integer, Name> finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Name> build(final SearchInterpretationRequest request) {
        return finder.query().select("sex, name").where().eq("locale", request.getLocale())
                .startsWith("upname", request.getSearch().toUpperCase())
                .query();
    }
}
