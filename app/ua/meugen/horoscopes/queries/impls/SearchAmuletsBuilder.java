package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import ua.meugen.horoscopes.entities.Amulet;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class SearchAmuletsBuilder implements QueryBuilder<Amulet, SearchInterpretationRequest> {

    @Inject
    private Model.Find<Integer, Amulet> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Amulet> build(final SearchInterpretationRequest request) {
        return find.select("type, amulet").where().eq("locale", request.getLocale())
                .startsWith("upamulet", request.getSearch().toUpperCase())
                .query();
    }
}
