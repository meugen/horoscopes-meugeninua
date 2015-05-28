package helpers;

import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by meugen on 24.06.14.
 */
public final class DatabaseHelper {

    public static <T> T actionWithDatabase(final ConnectionAction<T> action) throws SQLException {
        try (Connection connection = DB.getConnection()) {
            return action.onAction(connection);
        }
    }

    public static <T> T actionWithStatement(final StatementAction<T> action, final String sql) throws SQLException {
        return actionWithDatabase((connection) -> internalOnAction(connection, action, sql));
    }

    private static <T> T internalOnAction(final Connection connection, final StatementAction<T> action,
                                          final String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return action.onAction(statement);
        }
    }

    public interface ConnectionAction<T> {

        T onAction(Connection connection) throws SQLException;
    }

    public interface StatementAction<T> {

        T onAction(PreparedStatement statement) throws SQLException;
    }
}
