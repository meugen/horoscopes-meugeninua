package helpers.controllers.content.translate;

import helpers.controllers.Response;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meugen on 14.01.15.
 */
final class AmuletsTranslateHelper extends AbstractTranslateHelper {

    private static final String SELECT = "select amulet, type, image_id, content from horo_amulets_v2 where locale=?";
    private static final String INSERT = "insert into horo_amulets_v2 (upamulet, amulet, type, image_id, content," +
            " locale) values (?, ?, ?, ?, ?, ?)";
    private static final String CHECK = "select count(id) from horo_amulets_v2 where upamulet=? and locale=?";
    private static final String UPDATE = "update horo_amulets_v2 set amulet=?, type=?, image_id=?, content=?" +
            " where upamulet=? and locale=?";

    /**
     * Constructor.
     * @param lang Language
     */
    public AmuletsTranslateHelper(final String lang) {
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
                queries.addAll(Arrays.asList(resultSet.getString(4).split("\\[part\\]")));
                final List<String> translated = this.translateAll(queries);

                final StringBuilder translatedContent = new StringBuilder(translated.get(1));
                for (int i = 2; i < translated.size(); i++) {
                    translatedContent.append("[part]").append(translated.get(i));
                }
                if (this.checkTranslated(check, translated.get(0).toUpperCase())) {
                    update.clearParameters();
                    update.setString(1, translated.get(0));
                    update.setInt(2, resultSet.getInt(2));
                    update.setInt(3, resultSet.getInt(3));
                    update.setString(4, translatedContent.toString());
                    update.setString(5, translated.get(0).toUpperCase());
                    update.setString(6, this.getLocale());
                    update.execute();
                } else {
                    insert.clearParameters();
                    insert.setString(1, translated.get(0).toUpperCase());
                    insert.setString(2, translated.get(0));
                    insert.setInt(3, resultSet.getInt(2));
                    insert.setInt(4, resultSet.getInt(3));
                    insert.setString(5, translatedContent.toString());
                    insert.setString(6, this.getLocale());
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
