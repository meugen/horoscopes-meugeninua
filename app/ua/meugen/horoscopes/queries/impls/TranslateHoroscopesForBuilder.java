package ua.meugen.horoscopes.queries.impls;

import io.ebean.*;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class TranslateHoroscopesForBuilder implements QueryBuilder<Horoscope, BaseHoroscopesRequest> {

    @Inject
    private Finder<Integer, Horoscope> horoscopeFinder;
    @Inject
    private Finder<Integer, Period> periodFinder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Horoscope> build(final BaseHoroscopesRequest request) {
        final ExpressionFactory factory = this.horoscopeFinder.query().getExpressionFactory();
        return bindBaseParameters(this.horoscopeFinder.query().where(), request)
                .eq("locale", HoroscopeUtils.DEFAULT_LOCALE)
                .not(factory.in("period",
                        this.bindBaseParameters(this.horoscopeFinder.query().select("period").where(), request)
                                .eq("locale", HoroscopeUtils.getLocale(request)).query()))
                .query();
    }

    private ExpressionList<Horoscope> bindBaseParameters(
            final ExpressionList<Horoscope> expressions, BaseHoroscopesRequest request) {
        expressions.eq("sign", request.getSign())
                .eq("kind", HoroscopeUtils.DEFAULT_KIND)
                .in("period", this.periodFinder.query().select("key").where().in("name",
                        HoroscopeUtils.WIDGET_PERIODS).query());
        return HoroscopeUtils.bindPeriods(expressions, request.getPeriods());
    }
}
