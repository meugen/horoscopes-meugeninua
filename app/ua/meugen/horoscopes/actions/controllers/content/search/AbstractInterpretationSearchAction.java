package ua.meugen.horoscopes.actions.controllers.content.search;

import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.requests.SearchInterpretationRequest;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meugen on 23.10.14.
 */
abstract class AbstractInterpretationSearchAction<Dto>
        extends AbstractJsonControllerAction<SearchInterpretationRequest> {

    private static final Logger.ALogger LOG = Logger.of(AbstractInterpretationSearchAction.class);

    private final String sql;

    /**
     * Factory for create responses.
     */
    protected final ControllerResponsesFactory<ItemsResponse<Dto>> factory;

    /**
     * Constructor.
     *
     * @param sql Sql for search
     */
    protected AbstractInterpretationSearchAction(final String sql) {
        super(SearchInterpretationRequest.class);
        this.sql = sql;
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    /**
     * Create new response.
     * @return Response
     */
    protected abstract ItemsResponse<Dto> newResponse();

    /**
     * {@inheritDoc}
     */
    protected final Result action(final SearchInterpretationRequest request) {
        Result result;
        try {
            final ItemsResponse<Dto> response = DatabaseHelper.actionWithStatement((statement) ->
                    internalAction(statement, request), this.sql);
            result = Controller.ok(response.asJson());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
        return result;
    }

    private ItemsResponse<Dto> internalAction(final PreparedStatement statement,
                                    final SearchInterpretationRequest request) throws SQLException {
        statement.setString(1, request.getSearch().toUpperCase());
        statement.setString(2, request.getLocale());
        try (ResultSet resultSet = statement.executeQuery()) {
            final List<Dto> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(this.fetchDto(resultSet));
            }

            final ItemsResponse<Dto> response = this.factory.newOkResponse();
            response.setItems(items);
            return response;
        }
    }

    /**
     * Fetch dto from result set.
     * @param resultSet Result set
     * @return Dto
     */
    protected abstract Dto fetchDto(final ResultSet resultSet) throws SQLException;
}
