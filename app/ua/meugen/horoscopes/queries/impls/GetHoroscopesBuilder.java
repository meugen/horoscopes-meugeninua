package ua.meugen.horoscopes.queries.impls;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.HoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class GetHoroscopesBuilder implements QueryBuilder<Horoscope, HoroscopesRequest> {

    @Inject
    private Finder<Integer, Horoscope> finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Horoscope> build(final HoroscopesRequest request) {
        final ExpressionList<Horoscope> expressions = finder.query().select("period, content").where()
                .eq("type", request.getType())
                .eq("kind", request.getKind())
                .eq("sign", request.getSign())
                .eq("locale", request.getLocale());
        return HoroscopeUtils.bindPeriods(expressions, request.getPeriods()).query();
    }
}
