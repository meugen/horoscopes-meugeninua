package ua.meugen.horoscopes.actions.actions.content.translate;

import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.TranslateHelper;
import ua.meugen.horoscopes.actions.actions.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.requests.BaseTranslateRequest;
import ua.meugen.horoscopes.actions.responses.BaseResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by meugen on 14.01.15.
 */
abstract class AbstractTranslateAction<Req extends BaseTranslateRequest> extends AbstractJsonControllerAction<Req> {

    /**
     * Factory for create responses.
     */
    protected final ControllerResponsesFactory<BaseResponse> factory;

    protected AbstractTranslateAction(final Class<Req> reqClazz) {
        super(reqClazz);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    /**
     * {@inheritDoc}
     */
    private BaseResponse newResponse() {
        return new BaseResponse();
    }

    /**
     * {@inheritDoc}
     */
    protected final Result action(final Req req) {
        try {
            return DatabaseHelper.actionWithDatabase((connection) -> internalAction(connection, req));
        } catch (SQLException e) {
            return Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
    }

    private Result internalAction(final Connection connection, final Req request) throws SQLException {
        try {
            connection.setAutoCommit(false);
            return this.action(connection, request);
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
    protected abstract Result action(final Connection connection, final Req request) throws SQLException;

    /**
     * Translate all queries.
     *
     * @param queries Queries
     * @return Translated queries
     * @throws IOException I/O error
     */
    protected final List<String> translateAll(final List<String> queries, final Req request) throws IOException {
        final TranslateHelper helper = new TranslateHelper();
        helper.setTarget(request.getLang());
        return helper.translateAll(queries);
    }
}
