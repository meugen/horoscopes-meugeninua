package ua.meugen.horoscopes.actions.controllers.content.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ua.meugen.horoscopes.actions.controllers.ControllerAction;
import ua.meugen.horoscopes.actions.controllers.content.OnFillObjectListener;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 23.10.14.
 */
public final class SearchHelpersFactory {

    private static final String SEARCH_AMULET_SQL = "select id, type, amulet from horo_amulets_v2 where" +
            " upamulet like concat(?, '%') and locale=? order by type, amulet";
    private static final String SEARCH_DREAMS_SQL = "select id, type, dream from horo_dreams_v2 where" +
            " updream like concat(?, '%') and locale=? order by type, dream";
    private static final String SEARCH_NAMES_SQL = "select id, sex, name from horo_names_v2 where" +
            " upname like concat(?, '%') and locale=? order by sex, name";
    private static final String SEARCH_CHINAS_SQL = "select t1.china, t2.name, t1.period from horo_chinas_v2 t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_DRUIDS_SQL = "select t1.druid, t2.name, t1.period from horo_druids t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_FLOWERS_SQL = "select t1.flower, t2.name, t1.period from horo_flowers t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String SEARCH_JAPANS_SQL = "select t1.japan, t2.name, t1.period from horo_japans t1," +
            " horo_uploads t2 where t1.icon_id=t2.id and t1.locale=? order by t1.order";
    private static final String TYPE_KEY = "type";
    private static final String AMULET_KEY = "amulet";
    private static final String DREAM_KEY = "dream";
    private static final String NAME_KEY = "name";
    private static final String ICON_KEY = "icon";
    private static final String PERIOD_KEY = "period";
    private static final String SEX_KEY = "sex";

    private static final OnFillObjectListener SIMPLE_FILL_OBJECT = new OnFillObjectListener() {
        public void onFillObject(final ObjectNode object, final ResultSet resultSet) throws SQLException {
            object.put(NAME_KEY, resultSet.getString(1));
            object.put(ICON_KEY, resultSet.getString(2));
            object.put(PERIOD_KEY, resultSet.getString(3));
        }
    };

    private SearchHelpersFactory() {
    }

    public static ControllerAction newSearchAmuletsHelper(final JsonNode json) {
        final InterpretationSearchAction helper = new InterpretationSearchAction(json);
        helper.setSql(SEARCH_AMULET_SQL);
        helper.setOnFillObjectListener(new FillInterpretationObject(TYPE_KEY, AMULET_KEY));
        return helper;
    }

    public static ControllerAction newSearchDreamsHelper(final JsonNode json) {
        final InterpretationSearchAction helper = new InterpretationSearchAction(json);
        helper.setSql(SEARCH_DREAMS_SQL);
        helper.setOnFillObjectListener(new FillInterpretationObject(TYPE_KEY, DREAM_KEY));
        return helper;
    }

    public static ControllerAction newSearchNamesHelper(final JsonNode json) {
        final InterpretationSearchAction helper = new InterpretationSearchAction(json);
        helper.setSql(SEARCH_NAMES_SQL);
        helper.setOnFillObjectListener(new FillInterpretationObject(SEX_KEY, NAME_KEY));
        return helper;
    }

    public static ControllerAction newSearchChinasHelper(final String locale) {
        final SimpleSearchAction helper = new SimpleSearchAction();
        helper.setLocale(locale);
        helper.setSql(SEARCH_CHINAS_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT);
        return helper;
    }

    public static ControllerAction newSearchDruidsHelper(final String locale) {
        final SimpleSearchAction helper = new SimpleSearchAction();
        helper.setLocale(locale);
        helper.setSql(SEARCH_DRUIDS_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT);
        return helper;
    }

    public static ControllerAction newSearchFlowersHelper(final String locale) {
        final SimpleSearchAction helper = new SimpleSearchAction();
        helper.setLocale(locale);
        helper.setSql(SEARCH_FLOWERS_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT);
        return helper;
    }

    public static ControllerAction newSearchJapansHelper(final String locale) {
        final SimpleSearchAction helper = new SimpleSearchAction();
        helper.setLocale(locale);
        helper.setSql(SEARCH_JAPANS_SQL);
        helper.setOnFillObjectListener(SIMPLE_FILL_OBJECT);
        return helper;
    }

    private static class FillInterpretationObject implements OnFillObjectListener {

        private final String typeKey;
        private final String dataKey;

        /**
         * Constructor.
         *
         * @param typeKey Type key
         * @param dataKey Data key
         */
        public FillInterpretationObject(final String typeKey, final String dataKey) {
            this.typeKey = typeKey;
            this.dataKey = dataKey;
        }

        /**
         * {@inheritDoc}
         */
        public void onFillObject(final ObjectNode object, final ResultSet resultSet) throws SQLException {
            object.put("id", resultSet.getInt(1));
            object.put(this.typeKey, resultSet.getInt(2));
            object.put(this.dataKey, resultSet.getString(3));
        }
    }
}
