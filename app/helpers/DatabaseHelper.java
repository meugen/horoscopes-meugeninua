package helpers;

import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by meugen on 24.06.14.
 */
public final class DatabaseHelper {

    private static Connection connection;

    private static void ensureConnection() throws SQLException {
        synchronized (DatabaseHelper.class) {
            if (connection == null || !connection.isValid(0)) {
                connection = DB.getConnection();
            }
        }
    }

    public static <T> T actionWithDatabase(final ConnectionAction<T> action) throws SQLException {
        ensureConnection();
        return action.onAction(connection);
    }

    public static <T> T actionWithStatement(final StatementAction<T> action, final String sql) throws SQLException {
        return actionWithDatabase(new ConnectionAction<T>() {
            public T onAction(Connection connection) throws SQLException {
                return internalOnAction(connection, action, sql);
            }
        });
    }

    private static <T> T internalOnAction(final Connection connection, final StatementAction<T> action,
                                          final String sql) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            return action.onAction(statement);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public interface ConnectionAction<T> {

        T onAction(Connection connection) throws SQLException;
    }

    public interface StatementAction<T> {

        T onAction(PreparedStatement statement) throws SQLException;
    }
}
