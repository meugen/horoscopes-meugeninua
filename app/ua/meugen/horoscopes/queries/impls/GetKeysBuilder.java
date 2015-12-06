package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.KeysRequest;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class GetKeysBuilder implements QueryBuilder<Period, KeysRequest> {

    @Inject
    private Model.Find<Integer, Period> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Period> build(final KeysRequest request) {
        final ExpressionList<Period> expressions = find.select("key")
                .where().eq("type", request.getType());
        if (request.getPeriod() != null) {
            expressions.eq("value", request.getPeriod());
        }
        return expressions.query();
    }
}
