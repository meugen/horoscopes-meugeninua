package ua.meugen.horoscopes.actions.controllers.content.translate;

import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.TranslateHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractControllerAction;
import ua.meugen.horoscopes.actions.controllers.Response;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by meugen on 14.01.15.
 */
abstract class AbstractTranslateAction extends AbstractControllerAction {

    private final TranslateHelper helper;

    /**
     * Constructor.
     *
     * @param lang Language
     */
    protected AbstractTranslateAction(final String lang) {
        this.helper = new TranslateHelper();
        this.helper.setTarget(lang);
    }

    /**
     * {@inheritDoc}
     */
    protected final Result action() {
        try {
            return DatabaseHelper.actionWithDatabase(this::internalAction);
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
     *
     * @param connection Connection
     * @return Result
     * @throws SQLException On SQL error
     */
    protected abstract Result action(final Connection connection) throws SQLException;

    /**
     * Translate all queries.
     *
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
