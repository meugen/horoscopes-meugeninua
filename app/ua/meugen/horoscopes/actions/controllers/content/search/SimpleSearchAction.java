package ua.meugen.horoscopes.actions.controllers.content.search;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import play.mvc.Results;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractSimpleControllerAction;
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
public final class SimpleSearchAction extends AbstractSimpleControllerAction<ItemsResponse<SimpleDto>> {

    private static final Logger.ALogger LOG = Logger.of(SimpleSearchAction.class);

    private static final String ITEMS_KEY = "items";

    private String locale;
    private String sql;

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
    @Override
    protected ItemsResponse<SimpleDto> newResponse() {
        return null;
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
            result = Controller.ok(this.newErrorResponse(e).asJson());
        }
        return result;
    }

    private ItemsResponse<SimpleDto> internalAction(final PreparedStatement statement) throws SQLException {
        statement.setString(1, this.locale);
        try (ResultSet resultSet = statement.executeQuery()) {
            final List<SimpleDto> items = new ArrayList<>();
            while (resultSet.next()) {
                final SimpleDto simpleDto = new SimpleDto();
                simpleDto.setName(resultSet.getString(1));
                simpleDto.setIcon(resultSet.getString(2));
                simpleDto.setPeriod(resultSet.getString(3));
                items.add(simpleDto);
            }

            final ItemsResponse<SimpleDto> response = this.newOkResponse();
            response.setItems(items);
            return response;
        }
    }
}
