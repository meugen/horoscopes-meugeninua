package ua.meugen.horoscopes.queries.impls;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.KeysRequest;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class GetKeysBuilder implements QueryBuilder<Period, KeysRequest> {

    @Inject
    private Finder<Integer, Period> finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Period> build(final KeysRequest request) {
        final ExpressionList<Period> expressions = finder.query().select("key")
                .where().eq("type", request.getType());
        if (request.getPeriod() != null) {
            expressions.eq("name", request.getPeriod());
        }
        return expressions.query();
    }
}
