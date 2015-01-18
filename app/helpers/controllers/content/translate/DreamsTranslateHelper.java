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
final class DreamsTranslateHelper extends AbstractTranslateHelper {

    private static final String COUNT = "select count(id) from horo_dreams_v2 where locale=?" +
            " and dream not in (select rus_dream from horo_dreams_v2 where locale=?)";
    private static final String SELECT = "select dream, type, content from horo_dreams_v2 where locale=?" +
            " and dream not in (select rus_dream from horo_dreams_v2 where locale=?) limit ?";
    private static final String INSERT = "insert into horo_dreams_v2 (updream, dream, type, content," +
            " locale, rus_dream) values (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "update horo_dreams_v2 set dream=?, type=?, content=?" +
            " where updream=? and locale=? and rus_dream=?";

    private final int limit;

    /**
     * Constructor.
     * @param lang Language
     */
    public DreamsTranslateHelper(final String lang, final int limit) {
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
                response.setMessage(String.format("%d of %d dreams translated. %d left",
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
                queries.add(resultSet.getString(3));
                final List<String> translated = this.translateAll(queries);

                update.clearParameters();
                update.setString(1, translated.get(0));
                update.setInt(2, resultSet.getInt(2));
                update.setString(3, translated.get(1));
                update.setString(4, translated.get(0).toUpperCase());
                update.setString(5, this.getLocale());
                update.setString(6, queries.get(0));
                if (update.executeUpdate() == 0) {
                    insert.clearParameters();
                    insert.setString(1, translated.get(0).toUpperCase());
                    insert.setString(2, translated.get(0));
                    insert.setInt(3, resultSet.getInt(2));
                    insert.setString(4, translated.get(1));
                    insert.setString(5, this.getLocale());
                    insert.setString(6, queries.get(0));
                    insert.execute();
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }
}
