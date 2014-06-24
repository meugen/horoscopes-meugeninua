package controllers;

import helpers.DatabaseHelper;
import play.db.DB;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result main() {
        try {
            return DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                public Result onAction(PreparedStatement statement) throws SQLException {
                    return doMain(statement);
                }
            }, "SELECT name FROM horo_names_v2 where sex=? order by upname");
        } catch (SQLException e) {
            return internalServerError(e.getMessage());
        }
    }

    private static Result doMain(final PreparedStatement statement) throws SQLException {
        statement.setInt(1, 1);
        final ResultSet result = statement.executeQuery();

        final StringBuilder builder = new StringBuilder();
        while (result.next()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(result.getString(1));
        }
        return ok(builder.toString());
    }

}
