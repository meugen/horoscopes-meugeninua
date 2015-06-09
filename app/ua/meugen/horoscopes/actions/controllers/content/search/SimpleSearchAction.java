package ua.meugen.horoscopes.actions.controllers.content.search;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import play.mvc.Results;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractSimpleControllerAction;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.dto.SimpleDto;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import play.Logger;
import play.libs.Json;
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
@Component
public final class SimpleSearchAction extends AbstractSimpleControllerAction {

    private static final Logger.ALogger LOG = Logger.of(SimpleSearchAction.class);

    private final ControllerResponsesFactory<ItemsResponse<SimpleDto>> factory;

    private String locale;
    private String sql;

    public SimpleSearchAction() {
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    /**
     * Getter for locale.
     *
     * @return Locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Setter for locale.
     *
     * @param locale Locale
     */
    public void setLocale(final String locale) {
        this.locale = locale;
    }

    /**
     * Getter for sql.
     *
     * @return Sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * Setter for sql.
     *
     * @param sql Sql
     */
    public void setSql(final String sql) {
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
    protected Result action() {
        Result result;
        try {
            final ItemsResponse<SimpleDto> response = DatabaseHelper
                    .actionWithStatement(this::internalAction, this.sql);
            result = Results.ok(response.asJson());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.ok(this.factory.newErrorResponse(e).asJson());
        }
        return result;
    }

    private ItemsResponse<SimpleDto> internalAction(final PreparedStatement statement) throws SQLException {
        statement.setString(1, this.locale);
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
