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
        Connection connection = null;
        try {
            connection = DB.getConnection();
            return action.onAction(connection);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
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
