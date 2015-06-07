package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import ua.meugen.horoscopes.actions.TranslateHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.actions.requests.HoroscopesRequest;
import ua.meugen.horoscopes.actions.responses.HoroscopesResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by meugen on 13.03.15.
 */
abstract class TranslateHoroscopesAction<Req extends BaseHoroscopesRequest> extends AbstractJsonControllerAction<Req> {

    private static final String INSERT_TRANSLATED_HOROSCOPE = "INSERT INTO horo_texts" +
            " (type, kind, sign, period, locale, content) VALUES (?, ?, ?, ?, ?, ?)";
    private static final Set<String> RU_LOCALES = new HashSet<>(Arrays.asList("ru", "uk", "be", "bg", "az",
            "lv", "lt", "et", "hy", "kk", "ky", "mo"));

    protected static final String DEFAULT_LOCALE = "ru";

    /**
     * Default constructor.
     * @param reqClass Class for request
     */
    protected TranslateHoroscopesAction(final Class<Req> reqClass) {
        super(reqClass);
    }

    protected final Void translateAll(final Connection connection, final String sql,
                                      final Req request) throws SQLException {
        final String locale = this.getLocale(request);
        if (DEFAULT_LOCALE.equals(locale)) {
            return null;
        }

        synchronized (TranslateHoroscopesAction.class) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                this.bindStatement(statement, request, locale);
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
                            insert.setString(2, this.getKind(request));
                            insert.setString(3, request.getSign());
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

    protected final String getLocale(final Req request) {
        String locale = request.getLocale();
        locale = RU_LOCALES.contains(locale) ? "ru" : "en";
        return locale;
    }

    protected abstract String getKind(final Req request);

    protected abstract void bindStatement(final PreparedStatement statement, final Req request,
                                          final String locale) throws SQLException;
}
