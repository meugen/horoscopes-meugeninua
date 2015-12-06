package ua.meugen.horoscopes.actions.actions.content.search;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

/**
 * Created by admin on 10.06.2015.
 */
public final class SimpleSearchActionsModule extends AbstractModule {

    private static final String SEARCH_CHINAS_SQL = "select t1.id, t1.china, t2.name, t1.period from horo_chinas_v2 t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_DRUIDS_SQL = "select t1.id, t1.druid, t2.name, t1.period from horo_druids t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_FLOWERS_SQL = "select t1.id, t1.flower, t2.name, t1.period from horo_flowers t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_JAPANS_SQL = "select t1.id, t1.japan, t2.name, t1.period from horo_japans t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // Nothing to do
    }

    @Provides
    @Named("searchChinasAction")
    public SimpleSearchAction newSearchChinasAction() {
        return new SimpleSearchAction(SEARCH_CHINAS_SQL);
    }

    @Provides
    @Named("searchDruidsAction")
    public SimpleSearchAction newSearchDruidsAction() {
        return new SimpleSearchAction(SEARCH_DRUIDS_SQL);
    }

    @Provides
    @Named("searchFlowersAction")
    public SimpleSearchAction newSearchFlowersAction() {
        return new SimpleSearchAction(SEARCH_FLOWERS_SQL);
    }

    @Provides
    @Named("searchJapansAction")
    public SimpleSearchAction newSearchJapansAction() {
        return new SimpleSearchAction(SEARCH_JAPANS_SQL);
    }
}
