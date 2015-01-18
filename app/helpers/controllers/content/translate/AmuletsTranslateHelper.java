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

    private static final String COUNT = "select count(id) from horo_amulets_v2 where locale=?" +
            " and amulet not in (select rus_amulet from horo_amulets_v2 where locale=?)";
    private static final String SELECT = "select amulet, type, image_id, content from horo_amulets_v2 where locale=?" +
            " and amulet not in (select rus_amulet from horo_amulets_v2 where locale=?) limit ?";
    private static final String INSERT = "insert into horo_amulets_v2 (upamulet, amulet, type, image_id, content," +
            " locale, rus_amulet) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "update horo_amulets_v2 set amulet=?, type=?, image_id=?, content=?" +
            " where upamulet=? and locale=? and rus_amulet=?";

    private final int limit;

    /**
     * Constructor.
     * @param lang Language
     */
    public AmuletsTranslateHelper(final String lang, final int limit) {
        super(lang);
        this.limit = limit;
    }

    private int getTotalCount(final Connection connection) throws SQLException {
        try (PreparedStatement count = connection.prepareStatement(COUNT)) {
            count.setString(1, "ru");
            count.setString(2, this.getLocale());
            try (ResultSet resultSet = count.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final Connection connection) throws SQLException {
        final int total = this.getTotalCount(connection);
        try (PreparedStatement select = connection.prepareStatement(SELECT)) {
            select.setString(1, "ru");
            select.setString(2, this.getLocale());
            select.setInt(3, this.limit);
            try (ResultSet resultSet = select.executeQuery()) {
                this.processResults(connection, resultSet);
                final Response response = Response.empty();
                response.setMessage(String.format("%d of %d amulets translated. %d left",
                        Math.min(this.limit, total), total, this.getTotalCount(connection)));
                return Controller.ok(response.asJson());
            }
        }
    }

    private void processResults(final Connection connection, final ResultSet resultSet) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERT);
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
                update.clearParameters();
                update.setString(1, translated.get(0));
                update.setInt(2, resultSet.getInt(2));
                update.setInt(3, resultSet.getInt(3));
                update.setString(4, translatedContent.toString());
                update.setString(5, translated.get(0).toUpperCase());
                update.setString(6, this.getLocale());
                update.setString(7, queries.get(0));
                if (update.executeUpdate() == 0) {
                    insert.clearParameters();
                    insert.setString(1, translated.get(0).toUpperCase());
                    insert.setString(2, translated.get(0));
                    insert.setInt(3, resultSet.getInt(2));
                    insert.setInt(4, resultSet.getInt(3));
                    insert.setString(5, translatedContent.toString());
                    insert.setString(6, this.getLocale());
                    insert.setString(7, queries.get(0));
                    insert.execute();
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }
}
