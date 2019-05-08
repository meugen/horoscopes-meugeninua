package ua.meugen.horoscopes.queries.impls;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class GetHoroscopesForBuilder implements QueryBuilder<Horoscope, BaseHoroscopesRequest> {

    @Inject
    private Finder<Integer, Horoscope> horoscopeFinder;
    @Inject
    private Finder<Integer, Period> periodFinder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Horoscope> build(final BaseHoroscopesRequest request) {
        final ExpressionList<Horoscope> expressions = horoscopeFinder.query().select("type, period, content").where()
                .eq("kind", HoroscopeUtils.DEFAULT_KIND)
                .eq("sign", request.getSign())
                .eq("locale", request.getLocale())
                .in("period", periodFinder.query().select("key").where().in("name",
                        HoroscopeUtils.WIDGET_PERIODS).query());
        return HoroscopeUtils.bindPeriods(expressions, request.getPeriods()).query();
    }
}
