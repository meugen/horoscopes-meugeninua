package helpers.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.TranslateHelper;
import helpers.controllers.content.AbstractJsonControllerHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by meugen on 13.03.15.
 */
abstract class TranslateHoroscopesHelper extends AbstractJsonControllerHelper {

    private static final String INSERT_TRANSLATED_HOROSCOPE = "INSERT INTO horo_texts" +
            " (type, kind, sign, period, locale, content) VALUES (?, ?, ?, ?, ?, ?)";
    private static final Set<String> RU_LOCALES = new HashSet<>(Arrays.asList("ru", "uk", "be", "bg", "az",
            "lv", "lt", "et", "hy", "kk", "ky", "mo"));

    protected static final String PARAM_LOCALE = "locale";
    protected static final String DEFAULT_LOCALE = "ru";
    protected static final String PARAM_TYPE = "type";
    protected static final String PARAM_KIND = "kind";
    protected static final String PARAM_SIGN = "sign";

    /**
     * Constructor.
     *
     * @param json Json
     */
    protected TranslateHoroscopesHelper(final JsonNode json) {
        super(json);
    }

    protected final Void translateAll(final Connection connection, final String sql,
                                      final JsonNode json) throws SQLException {
        final String locale = this.getLocale(json);
        if (DEFAULT_LOCALE.equals(locale)) {
            return null;
        }

        synchronized (TranslateHoroscopesHelper.class) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                this.bindStatement(statement, json, locale);
                final ResultSet resultSet = statement.executeQuery();

                final List<String> types = new ArrayList<>();
                final List<String> periods = new ArrayList<>();
                final List<String> contents = new ArrayList<>();
                while (resultSet.next()) {
                    types.add(resultSet.getString(1));
                    periods.add(resultSet.getString(2));
                    contents.add(resultSet.getString(3));
                }

                if (!contents.isEmpty()) {
                    final TranslateHelper helper = new TranslateHelper();
                    helper.setTarget(locale);
                    final List<String> translatedContents = helper.translateAll(contents);

                    try (PreparedStatement insert = connection.prepareStatement(INSERT_TRANSLATED_HOROSCOPE)) {
                        final int count = translatedContents.size();
                        for (int i = 0; i < count; i++) {
                            insert.clearParameters();
                            insert.setString(1, types.get(i));
                            insert.setString(2, json.get(PARAM_KIND).textValue());
                            insert.setString(3, json.get(PARAM_SIGN).textValue());
                            insert.setString(4, periods.get(i));
                            insert.setString(5, locale);
                            insert.setString(6, translatedContents.get(i));
                            insert.execute();
                        }
                    }
                }
                connection.commit();

                return null;
            } catch (IOException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    protected final String getLocale(final JsonNode json) {
        String locale = json.has(PARAM_LOCALE) ? json.get(PARAM_LOCALE).textValue() : DEFAULT_LOCALE;
        locale = RU_LOCALES.contains(locale) ? "ru" : "en";
        return locale;
    }

    protected abstract void bindStatement(final PreparedStatement statement, final JsonNode json,
                                          final String locale) throws SQLException;
}
