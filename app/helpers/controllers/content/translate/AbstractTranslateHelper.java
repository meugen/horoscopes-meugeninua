package helpers.controllers.content.translate;

import helpers.DatabaseHelper;
import helpers.TranslateHelper;
import helpers.controllers.AbstractControllerHelper;
import helpers.controllers.Response;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meugen on 14.01.15.
 */
abstract class AbstractTranslateHelper extends AbstractControllerHelper {

    private final TranslateHelper helper;

    /**
     * Constructor.
     * @param lang Language
     */
    protected AbstractTranslateHelper(final String lang) {
        this.helper = new TranslateHelper();
        this.helper.setTarget(lang);
    }

    /**
     * {@inheritDoc}
     */
    protected final Result action() {
        try {
            return DatabaseHelper.actionWithDatabase(new DatabaseHelper.ConnectionAction<Result>() {
                public Result onAction(final Connection connection) throws SQLException {
                    return AbstractTranslateHelper.this.internalAction(connection);
                }
            });
        } catch (SQLException e) {
            return Controller.internalServerError(Response.error(e).asJson());
        }
    }

    private Result internalAction(final Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);
            return this.action(connection);
        } finally {
            connection.commit();
        }
    }

    /**
     * Action with connection.
     * @param connection Connection
     * @return Result
     * @throws SQLException On SQL error
     */
    protected abstract Result action(final Connection connection) throws SQLException;

    /**
     * Translate all queries.
     * @param queries Queries
     * @return Translated queries
     * @throws IOException I/O error
     */
    protected final List<String> translateAll(final List<String> queries) throws IOException {
        return this.helper.translateAll(queries);
    }

    protected final String getLocale() {
        return this.helper.getTarget();
    }
}
