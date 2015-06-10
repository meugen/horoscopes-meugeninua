package ua.meugen.horoscopes.actions.controllers.content.get;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.responses.ContentResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
abstract class AbstractGetByIdAction<Dto> extends AbstractJsonControllerAction<Integer> {

    private static final Logger.ALogger LOG = Logger.of(AbstractGetByIdAction.class);

    private final ControllerResponsesFactory<ContentResponse<Dto>> factory;

    private final String sql;

    /**
     * Constructor.
     * @param sql SQL
     */
    protected AbstractGetByIdAction(final String sql) {
        super(Integer.class);
        this.sql = sql;
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    /**
     * Create new response.
     * @return New response
     */
    protected abstract ContentResponse<Dto> newResponse();

    protected abstract Dto fetchDto(final ResultSet resultSet) throws SQLException;

    /**
     * {@inheritDoc}
     */
    protected Result action(final Integer request) {
        Result result;
        try {
            final ContentResponse<Dto> response = DatabaseHelper.actionWithStatement(
                    (statement) -> internalAction(statement, request), this.sql);
            result = Controller.ok(response.asJson());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
        return result;
    }

    private ContentResponse<Dto> internalAction(final PreparedStatement statement, final Integer id) throws SQLException {
        statement.setInt(1, id);
        final ResultSet resultSet = statement.executeQuery();

        ContentResponse<Dto> response;
        if (resultSet.next()) {
            response = this.factory.newOkResponse();
            response.setContent(this.fetchDto(resultSet));
        } else {
            response = this.factory.newNotFoundResponse();
        }
        return response;
    }

}
