package ua.meugen.horoscopes.queries.impls;

import io.ebean.*;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.requests.HoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class TranslateHoroscopesBuilder implements QueryBuilder<Horoscope, HoroscopesRequest> {

    @Inject
    private Finder<Integer, Horoscope> finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Horoscope> build(final HoroscopesRequest request) {
        final ExpressionFactory factory = this.finder.query().getExpressionFactory();
        return bindBaseParameters(this.finder.query().where(), request)
                .eq("locale", HoroscopeUtils.DEFAULT_LOCALE)
                .not(factory.in("period",
                        this.bindBaseParameters(this.finder.query().select("period").where(), request)
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
