package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.HoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class TranslateHoroscopesBuilder implements QueryBuilder<Horoscope, HoroscopesRequest> {

    @Inject
    private Model.Find<Integer, Horoscope> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Horoscope> build(final HoroscopesRequest request) {
        final ExpressionFactory factory = this.find.getExpressionFactory();
        return bindBaseParameters(this.find.where(), request)
                .eq("locale", HoroscopeUtils.DEFAULT_LOCALE)
                .not(factory.in("period",
                        this.bindBaseParameters(this.find.select("period").where(), request)
                        .eq("locale", HoroscopeUtils.getLocale(request)).query()))
                .query();
    }

    private ExpressionList<Horoscope> bindBaseParameters(
            final ExpressionList<Horoscope> expressions, HoroscopesRequest request) {
        expressions.eq("type", request.getType())
                .eq("kind", request.getKind())
                .eq("sign", request.getSign());
        return HoroscopeUtils.bindPeriods(expressions, request.getPeriods());
    }
}
