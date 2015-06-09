package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
@Component
public final class GetSimpleActionsFactory {

    private static final String GET_AMULET_BY_ID_SQL = "select t2.name, t1.content from horo_amulets_v2 t1," +
            " horo_uploads t2 where t1.image_id=t2.id and t1.id=?";
    private static final String GET_DREAM_BY_ID_SQL = "select content from horo_dreams_v2 where id=?";
    private static final String GET_NAME_BY_ID_SQL = "select content from horo_names_v2 where id=?";
    private static final String GET_CHINA_BY_ID_SQL = "select content from horo_chinas_v2 where id=?";
    private static final String GET_DRUID_BY_ID_SQL = "select content from horo_druids where id=?";
    private static final String GET_FLOWER_BY_ID_SQL = "select content from horo_flowers where id=?";
    private static final String GET_JAPAN_BY_ID_SQL = "select content from horo_japans where id=?";

    @Bean(name = "getAmuletAction", autowire = Autowire.BY_NAME)
    public SimpleGetByIdAction newGetAmuletAction() {
        return new SimpleGetByIdAction(GET_AMULET_BY_ID_SQL);
    }

    @Bean(name = "getChinaAction", autowire = Autowire.BY_NAME)
    public SimpleGetByIdAction newGetChinaAction() {
        return new SimpleGetByIdAction(GET_CHINA_BY_ID_SQL);
    }

    @Bean(name = "getDreamAction", autowire = Autowire.BY_NAME)
    public SimpleGetByIdAction newGetDreamAction() {
        return new SimpleGetByIdAction(GET_DREAM_BY_ID_SQL);
    }

    @Bean(name = "getDruidAction", autowire = Autowire.BY_NAME)
    public SimpleGetByIdAction newGetDruidAction() {
        return new SimpleGetByIdAction(GET_DRUID_BY_ID_SQL);
    }

    @Bean(name = "getFlowerAction", autowire = Autowire.BY_NAME)
    public SimpleGetByIdAction newGetFlowerAction() {
        return new SimpleGetByIdAction(GET_FLOWER_BY_ID_SQL);
    }

    @Bean(name = "getNameAction", autowire = Autowire.BY_NAME)
    public SimpleGetByIdAction newGetNameAction() {
        return new SimpleGetByIdAction(GET_NAME_BY_ID_SQL);
    }

    @Bean(name = "getJapanAction", autowire = Autowire.BY_NAME)
    public SimpleGetByIdAction newGetJapanAction() {
        return new SimpleGetByIdAction(GET_JAPAN_BY_ID_SQL);
    }
}
