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
import java.util.Arrays;
import java.util.List;

/**
 * Created by meugen on 14.01.15.
 */
final class FlowersTranslateHelper extends AbstractTranslateHelper {

    private static final String SELECT = "select flower, icon_id, period, content, \"order\" from horo_flowers where locale=?";
    private static final String INSERT = "insert into horo_flowers (upflower, flower, icon_id, period, content," +
            " locale, \"order\") values (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Constructor.
     * @param lang Language
     */
    public FlowersTranslateHelper(final String lang) {
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
        try (PreparedStatement insert = connection.prepareStatement(INSERT)) {
            while (resultSet.next()) {
                final List<String> queries = new ArrayList<>();
                queries.add(resultSet.getString(1));
                queries.add(resultSet.getString(3));
                queries.add(resultSet.getString(4));
                final List<String> translated = this.translateAll(queries);

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
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }
}
