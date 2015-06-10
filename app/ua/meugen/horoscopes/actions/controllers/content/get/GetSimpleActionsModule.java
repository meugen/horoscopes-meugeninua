package ua.meugen.horoscopes.actions.controllers.content.get;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

/**
 * Created by admin on 23.10.2014.
 */
public final class GetSimpleActionsModule extends AbstractModule {

    private static final String GET_AMULET_BY_ID_SQL = "select t2.name, t1.content from horo_amulets_v2 t1," +
            " horo_uploads t2 where t1.image_id=t2.id and t1.id=?";
    private static final String GET_DREAM_BY_ID_SQL = "select content from horo_dreams_v2 where id=?";
    private static final String GET_NAME_BY_ID_SQL = "select content from horo_names_v2 where id=?";
    private static final String GET_CHINA_BY_ID_SQL = "select content from horo_chinas_v2 where id=?";
    private static final String GET_DRUID_BY_ID_SQL = "select content from horo_druids where id=?";
    private static final String GET_FLOWER_BY_ID_SQL = "select content from horo_flowers where id=?";
    private static final String GET_JAPAN_BY_ID_SQL = "select content from horo_japans where id=?";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // Nothing to do
    }

    @Provides
    @Named("getAmuletAction")
    public SimpleGetByIdAction newGetAmuletAction() {
        return new SimpleGetByIdAction(GET_AMULET_BY_ID_SQL);
    }

    @Provides
    @Named("getChinaAction")
    public SimpleGetByIdAction newGetChinaAction() {
        return new SimpleGetByIdAction(GET_CHINA_BY_ID_SQL);
    }

    @Provides
    @Named("getDreamAction")
    public SimpleGetByIdAction newGetDreamAction() {
        return new SimpleGetByIdAction(GET_DREAM_BY_ID_SQL);
    }

    @Provides
    @Named("getDruidAction")
    public SimpleGetByIdAction newGetDruidAction() {
        return new SimpleGetByIdAction(GET_DRUID_BY_ID_SQL);
    }

    @Provides
    @Named("getFlowerAction")
    public SimpleGetByIdAction newGetFlowerAction() {
        return new SimpleGetByIdAction(GET_FLOWER_BY_ID_SQL);
    }

    @Provides
    @Named("getNameAction")
    public SimpleGetByIdAction newGetNameAction() {
        return new SimpleGetByIdAction(GET_NAME_BY_ID_SQL);
    }

    @Provides
    @Named("getJapanAction")
    public SimpleGetByIdAction newGetJapanAction() {
        return new SimpleGetByIdAction(GET_JAPAN_BY_ID_SQL);
    }
}
