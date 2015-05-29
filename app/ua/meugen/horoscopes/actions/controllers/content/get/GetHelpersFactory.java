package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ua.meugen.horoscopes.actions.controllers.ControllerAction;
import ua.meugen.horoscopes.actions.controllers.content.OnFillObjectListener;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
public final class GetHelpersFactory {

    private static final String GET_AMULET_SQL = "select t2.name, t1.content from horo_amulets_v2 t1," +
            " horo_uploads t2 where t1.image_id=t2.id and t1.upamulet=? and t1.locale=?";
    private static final String GET_AMULET_BY_ID_SQL = "select t2.name, t1.content from horo_amulets_v2 t1," +
            " horo_uploads t2 where t1.image_id=t2.id and t1.id=?";
    private static final String GET_CHINA_SQL = "select content from horo_chinas_v2 where upchina=? and locale=?";
    private static final String GET_DREAM_SQL = "select content from horo_dreams_v2 where updream=? and locale=?";
    private static final String GET_DREAM_BY_ID_SQL = "select content from horo_dreams_v2 where id=?";
    private static final String GET_DRUID_SQL = "select content from horo_druids where updruid=? and locale=?";
    private static final String GET_FLOWER_SQL = "select content from horo_flowers where upflower=? and locale=?";
    private static final String GET_NAME_SQL = "select content from horo_names_v2 where upname=? and locale=?";
    private static final String GET_NAME_BY_ID_SQL = "select content from horo_names_v2 where id=?";
    private static final String GET_JAPAN_SQL = "select content from horo_japans where upjapan=? and locale=?";
    private static final String PARAM_AMULET = "amulet";
    private static final String PARAM_CHINA = "china";
    private static final String PARAM_DREAM = "dream";
    private static final String PARAM_DRUID = "druid";
    private static final String PARAM_FLOWER = "flower";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_JAPAN = "japan";
    private static final String IMAGE_KEY = "image";
    private static final String TEXT_KEY = "text";

    private static final OnFillObjectListener AMULET_FILL_OBJECT_LISTENER
            = new OnFillObjectListener() {
        public void onFillObject(final ObjectNode object, final ResultSet resultSet) throws SQLException {
            object.put(IMAGE_KEY, resultSet.getString(1));
            object.put(TEXT_KEY, resultSet.getString(2));
        }
    };
    private static final OnFillObjectListener SIMPLE_FILL_OBJECT_LISTENER
            = new OnFillObjectListener() {
        public void onFillObject(final ObjectNode object, final ResultSet resultSet) throws SQLException {
            object.put(TEXT_KEY, resultSet.getString(1));
        }
    };

    private GetHelpersFactory() {
    }

    public static ControllerAction newGetAmuletHelper(final JsonNode json) {
        final SimpleGetAction helper = new SimpleGetAction(json);
        helper.setParam(PARAM_AMULET);
        helper.setSql(GET_AMULET_SQL);
        helper.setOnFillObjectListener(AMULET_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetAmuletHelper(final Integer id) {
        final SimpleGetByIdAction helper = new SimpleGetByIdAction(id);
        helper.setSql(GET_AMULET_BY_ID_SQL);
        helper.setOnFillObjectListener(AMULET_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetChinaHelper(final JsonNode json) {
        final SimpleGetAction helper = new SimpleGetAction(json);
        helper.setParam(PARAM_CHINA);
        helper.setSql(GET_CHINA_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetDreamHelper(final JsonNode json) {
        final SimpleGetAction helper = new SimpleGetAction(json);
        helper.setParam(PARAM_DREAM);
        helper.setSql(GET_DREAM_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetDreamHelper(final Integer id) {
        final SimpleGetByIdAction helper = new SimpleGetByIdAction(id);
        helper.setSql(GET_DREAM_BY_ID_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetDruidHelper(final JsonNode json) {
        final SimpleGetAction helper = new SimpleGetAction(json);
        helper.setParam(PARAM_DRUID);
        helper.setSql(GET_DRUID_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetFlowerHelper(final JsonNode json) {
        final SimpleGetAction helper = new SimpleGetAction(json);
        helper.setParam(PARAM_FLOWER);
        helper.setSql(GET_FLOWER_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetNameHelper(final JsonNode json) {
        final SimpleGetAction helper = new SimpleGetAction(json);
        helper.setParam(PARAM_NAME);
        helper.setSql(GET_NAME_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetNameHelper(final Integer id) {
        final SimpleGetByIdAction helper = new SimpleGetByIdAction(id);
        helper.setSql(GET_NAME_BY_ID_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetJapanHelper(final JsonNode json) {
        final SimpleGetAction helper = new SimpleGetAction(json);
        helper.setParam(PARAM_JAPAN);
        helper.setSql(GET_JAPAN_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT_LISTENER);
        return helper;
    }

    public static ControllerAction newGetHoroscopeHelper(final JsonNode json) {
        return new GetHoroscopeAction(json);
    }

    public static ControllerAction newGetHoroscopeHelper(final JsonNode json, final String period) {
        final GetHoroscopeAction helper = new GetHoroscopeAction(json);
        helper.setPeriod(period);
        return helper;
    }

    public static ControllerAction newGetHoroscopesForHelper(final JsonNode json) {
        return new GetHoroscopesForAction(json);
    }

    public static ControllerAction newGetKeysHelper(final JsonNode json) {
        return new GetKeysAction(json);
    }

    public static ControllerAction newGetKeysHelper(final JsonNode json, final String period) {
        final GetKeysAction helper = new GetKeysAction(json);
        helper.setPeriod(period);
        return helper;
    }

    public static ControllerAction newGetKeysForHelper() {
        return new GetKeysForAction();
    }
}
