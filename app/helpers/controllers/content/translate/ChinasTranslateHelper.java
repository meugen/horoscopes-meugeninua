package helpers.controllers.content.translate;

import helpers.controllers.Response;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meugen on 14.01.15.
 */
final class ChinasTranslateHelper extends AbstractTranslateHelper {

    private static final String SELECT = "select china, icon_id, period, content, \"order\" from horo_chinas_v2 where locale=?";
    private static final String INSERT = "insert into horo_chinas_v2 (upchina, china, icon_id, period, content," +
            " locale, \"order\") values (?, ?, ?, ?, ?, ?, ?)";
    private static final String CHECK = "select count(id) from horo_chinas_v2 where upchina=? and locale=?";
    private static final String UPDATE = "update horo_chinas_v2 set china=?, icon_id=?, period=?, content=?," +
            " \"order\"=? where upchina=? and locale=?";

    /**
     * Constructor.
     *
     * @param lang Language
     */
    public ChinasTranslateHelper(final String lang) {
        super(lang);
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final Connection connection) throws SQLException {
        try (PreparedStatement select = connection.prepareStatement(SELECT)) {
            select.setString(1, "ru");
            try (ResultSet resultSet = select.executeQuery()) {
                this.processResults(connection, resultSet);
                return Controller.ok(Response.empty().asJson());
            }
        }
    }

    private void processResults(final Connection connection, final ResultSet resultSet) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERT);
             PreparedStatement check = connection.prepareStatement(CHECK);
             PreparedStatement update = connection.prepareStatement(UPDATE)) {
            while (resultSet.next()) {
                final List<String> queries = new ArrayList<>();
                queries.add(resultSet.getString(1));
                queries.add(resultSet.getString(3));
                queries.add(resultSet.getString(4));
                final List<String> translated = this.translateAll(queries);

                if (this.checkTranslated(check, translated.get(0).toUpperCase())) {
                    update.clearParameters();
                    update.setString(1, translated.get(0));
                    update.setInt(2, resultSet.getInt(2));
                    update.setString(3, translated.get(1));
                    update.setString(4, translated.get(2));
                    update.setInt(5, resultSet.getInt(5));
                    update.setString(6, translated.get(0).toUpperCase());
                    update.setString(7, this.getLocale());
                    update.execute();
                } else {
                    insert.clearParameters();
                    insert.setString(1, translated.get(0).toUpperCase());
                    insert.setString(2, translated.get(0));
                    insert.setInt(3, resultSet.getInt(2));
                    insert.setString(4, translated.get(1));
                    insert.setString(5, translated.get(2));
                    insert.setString(6, this.getLocale());
                    insert.setInt(7, resultSet.getInt(5));
                    insert.execute();
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    private boolean checkTranslated(final PreparedStatement statement, final String text) throws SQLException {
        statement.clearParameters();
        statement.setString(1, text);
        statement.setString(2, this.getLocale());
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }
}
