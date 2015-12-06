package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

public final class GetKeysForBuilder implements QueryBuilder<Period, Void> {

    @Inject
    private Model.Find<Integer, Period> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Period> build(final Void request) {
        return find.select("type, key").where().in("value",
                HoroscopeUtils.WIDGET_PERIODS).query();
    }
}
