package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class GetHoroscopesForBuilder implements QueryBuilder<Horoscope, BaseHoroscopesRequest> {

    @Inject
    private Model.Find<Integer, Horoscope> horoscopeFind;
    @Inject
    private Model.Find<Integer, Period> periodFind;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Horoscope> build(final BaseHoroscopesRequest request) {
        final ExpressionList<Horoscope> expressions = horoscopeFind.select("type, period, content").where()
                .eq("kind", HoroscopeUtils.DEFAULT_KIND)
                .eq("sign", request.getSign())
                .eq("locale", request.getLocale())
                .in("period", periodFind.select("key").where().in("name",
                        HoroscopeUtils.WIDGET_PERIODS).query());
        return HoroscopeUtils.bindPeriods(expressions, request.getPeriods()).query();
    }
}
