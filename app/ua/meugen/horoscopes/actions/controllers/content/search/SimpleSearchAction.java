package ua.meugen.horoscopes.actions.controllers.content.search;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.dto.SimpleDto;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meugen on 23.10.14.
 */
public final class SimpleSearchAction extends AbstractJsonControllerAction<String> {

    private static final Logger.ALogger LOG = Logger.of(SimpleSearchAction.class);

    private final ControllerResponsesFactory<ItemsResponse<SimpleDto>> factory;
    private final String sql;

    /**
     * Constructor.
     *
     * @param sql SQL
     */
    public SimpleSearchAction(final String sql) {
        super(String.class);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
        this.sql = sql;
    }

    /**
     * {@inheritDoc}
     */
    private ItemsResponse<SimpleDto> newResponse() {
        return new ItemsResponse<>();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final String request) {
        Result result;
        try {
            final ItemsResponse<SimpleDto> response = DatabaseHelper
                    .actionWithStatement((statement) -> internalAction(statement, request), this.sql);
            result = Results.ok(response.asJson());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.ok(this.factory.newErrorResponse(e).asJson());
        }
        return result;
    }

    private ItemsResponse<SimpleDto> internalAction(final PreparedStatement statement, final String locale) throws SQLException {
        statement.setString(1, locale);
        try (ResultSet resultSet = statement.executeQuery()) {
            final List<SimpleDto> items = new ArrayList<>();
            while (resultSet.next()) {
                final SimpleDto simpleDto = new SimpleDto();
                simpleDto.setId(resultSet.getInt(1));
                simpleDto.setName(resultSet.getString(2));
                simpleDto.setIcon(resultSet.getString(3));
                simpleDto.setPeriod(resultSet.getString(4));
                items.add(simpleDto);
            }

            final ItemsResponse<SimpleDto> response = this.factory.newOkResponse();
            response.setItems(items);
            return response;
        }
    }
}
