package ua.meugen.horoscopes.actions.actions.content.translate;

import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.requests.BaseTranslateRequest;

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
public final class FlowersTranslateAction extends AbstractTranslateAction<BaseTranslateRequest> {

    private static final String SELECT = "select flower, icon_id, period, content, \"order\" from horo_flowers where locale=?";
    private static final String INSERT = "insert into horo_flowers (upflower, flower, icon_id, period, content," +
            " locale, \"order\") values (?, ?, ?, ?, ?, ?, ?)";
    private static final String CHECK = "select count(id) from horo_flowers where upflower=? and locale=?";
    private static final String UPDATE = "update horo_flowers set flower=?, icon_id=?, period=?, content=?," +
            " \"order\"=? where upflower=? and locale=?";

    /**
     * Default constructor.
     */
    public FlowersTranslateAction() {
        super(BaseTranslateRequest.class);
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final Connection connection, final BaseTranslateRequest request) throws SQLException {
        try (PreparedStatement select = connection.prepareStatement(SELECT)) {
            select.setString(1, "ru");
            try (ResultSet resultSet = select.executeQuery()) {
                this.processResults(connection, resultSet, request);
                return Controller.ok(this.factory.newOkResponse().asJson());
            }
        }
    }

    private void processResults(final Connection connection, final ResultSet resultSet,
                                final BaseTranslateRequest request) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERT);
             PreparedStatement check = connection.prepareStatement(CHECK);
             PreparedStatement update = connection.prepareStatement(UPDATE)) {
            while (resultSet.next()) {
                final List<String> queries = new ArrayList<>();
                queries.add(resultSet.getString(1));
                queries.add(resultSet.getString(3));
                queries.add(resultSet.getString(4));
                final List<String> translated = this.translateAll(queries, request);

                if (this.checkTranslated(check, translated.get(0).toUpperCase(), request)) {
                    update.clearParameters();
                    update.setString(1, translated.get(0));
                    update.setInt(2, resultSet.getInt(2));
                    update.setString(3, translated.get(1));
                    update.setString(4, translated.get(2));
                    update.setInt(5, resultSet.getInt(5));
                    update.setString(6, translated.get(0).toUpperCase());
                    update.setString(7, request.getLang());
                    update.execute();
                } else {
                    insert.clearParameters();
                    insert.setString(1, translated.get(0).toUpperCase());
                    insert.setString(2, translated.get(0));
                    insert.setInt(3, resultSet.getInt(2));
                    insert.setString(4, translated.get(1));
                    insert.setString(5, translated.get(2));
                    insert.setString(6, request.getLang());
                    insert.setInt(7, resultSet.getInt(5));
                    insert.execute();
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    private boolean checkTranslated(final PreparedStatement statement, final String text,
                                    final BaseTranslateRequest request) throws SQLException {
        statement.clearParameters();
        statement.setString(1, text);
        statement.setString(2, request.getLang());
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }
}
