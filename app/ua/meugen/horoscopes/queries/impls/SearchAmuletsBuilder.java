package ua.meugen.horoscopes.queries.impls;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.entities.Amulet;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchAmuletsBuilder implements QueryBuilder<Amulet, SearchInterpretationRequest> {

    @Inject
    private Finder<Integer, Amulet> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Amulet> build(final SearchInterpretationRequest request) {
        return find.query().select("type, amulet").where().eq("locale", request.getLocale())
                .startsWith("upamulet", request.getSearch().toUpperCase())
                .query();
    }
}
