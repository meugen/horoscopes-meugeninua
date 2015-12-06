package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class TranslateHoroscopesForBuilder implements QueryBuilder<Horoscope, BaseHoroscopesRequest> {

    @Inject
    private Model.Find<Integer, Horoscope> horoscopeFind;
    @Inject
    private Model.Find<Integer, Period> periodFind;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Horoscope> build(final BaseHoroscopesRequest request) {
        final ExpressionFactory factory = this.horoscopeFind.getExpressionFactory();
        return bindBaseParameters(this.horoscopeFind.where(), request)
                .eq("locale", HoroscopeUtils.DEFAULT_LOCALE)
                .not(factory.in("period",
                        this.bindBaseParameters(this.horoscopeFind.select("period").where(), request)
                                .eq("locale", HoroscopeUtils.getLocale(request)).query()))
                .query();
    }

    private ExpressionList<Horoscope> bindBaseParameters(
            final ExpressionList<Horoscope> expressions, BaseHoroscopesRequest request) {
        expressions.eq("sign", request.getSign())
                .eq("kind", HoroscopeUtils.DEFAULT_KIND)
                .in("period", this.periodFind.select("key").where().in("value",
                        HoroscopeUtils.WIDGET_PERIODS).query());
        return HoroscopeUtils.bindPeriods(expressions, request.getPeriods());
    }
}
