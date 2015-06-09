package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.controllers.AbstractSimpleControllerAction;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.responses.SimpleResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
public final class SimpleGetByIdAction extends AbstractJsonControllerAction<Integer> {

    private static final Logger.ALogger LOG = Logger.of(SimpleGetByIdAction.class);

    private final ControllerResponsesFactory<SimpleResponse> factory;

    private final String sql;

    /**
     * Default constructor.
     */
    public SimpleGetByIdAction(final String sql) {
        super(Integer.class);
        this.sql = sql;
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    private SimpleResponse newResponse() {
        return new SimpleResponse();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final Integer request) {
        Result result;
        try {
            final SimpleResponse response = DatabaseHelper.actionWithStatement(
                    (statement) -> internalAction(statement, request), this.sql);
            result = Controller.ok(response.asJson());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
        return result;
    }

    private SimpleResponse internalAction(final PreparedStatement statement, final Integer id) throws SQLException {
        statement.setInt(1, id);
        final ResultSet resultSet = statement.executeQuery();

        SimpleResponse response;
        if (resultSet.next()) {
            response = this.factory.newOkResponse();
            response.setText(resultSet.getString(1));
        } else {
            response = this.factory.newNotFoundResponse();
        }
        return response;
    }

}
