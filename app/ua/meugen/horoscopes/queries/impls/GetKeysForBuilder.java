package ua.meugen.horoscopes.queries.impls;

import io.ebean.Finder;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class GetKeysForBuilder implements QueryBuilder<Period, Void> {

    @Inject
    private Finder<Integer, Period> finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Period> build(final Void request) {
        return finder.query().select("type, key").where().in("name",
                HoroscopeUtils.WIDGET_PERIODS).query();
    }
}
