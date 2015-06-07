package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.requests.HoroscopesRequest;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Helper for get horoscopes for sign.
 *
 * @author meugen
 */
@Component
public final class GetHoroscopesForAction extends TranslateHoroscopesAction {

    private static final Logger.ALogger LOG = Logger.of(GetHoroscopesForAction.class);

    private static final String SELECT = "SELECT type, period, content FROM horo_texts t1 WHERE kind=?" +
            " AND sign=? AND locale=? AND period IN (SELECT key FROM horo_periods p1 WHERE p1.type=t1.type AND" +
            " p1.period IN ('today', 'cur', 'second') AND (p1.type='weekly' AND p1.version=2 OR p1.type<>'weekly'))";
    private static final String TRANSLATE = "SELECT t1.type, t1.period, t1.content FROM horo_texts t1 WHERE t1.kind=?" +
            " AND t1.sign=? AND t1.locale=? AND t1.period IN (SELECT p1.key FROM horo_periods p1 WHERE p1.type=t1.type AND" +
            " p1.period IN ('today', 'cur', 'second') AND (p1.type='weekly' AND p1.version=2 OR p1.type<>'weekly'))" +
            " AND t1.period NOT IN (SELECT t2.period FROM horo_texts t2 WHERE t2.kind=?" +
            " AND t2.sign=? AND t2.locale=? AND t2.period IN (SELECT p2.key FROM horo_periods p2 WHERE p2.type=t2.type AND" +
            " p2.period IN ('today', 'cur', 'second') AND (p2.type='weekly' AND p2.version=2 OR p2.type<>'weekly')))";

    private static final String DEFAULT_KIND = "common";

    /**
     * {@inheritDoc}
     */
    protected Result action(final HoroscopesRequest request) {
        try {
            final List<String> periods = request.getPeriods();

            final StringBuilder where = new StringBuilder();
            if (!periods.isEmpty()) {
                where.append(" AND (1=0");
                for (String period : periods) {
                    where.append(String.format(" OR t1.period='%s'", period));
                }
                where.append(")");
            }

            this.translateAll(request, where);
            final JsonNode response = DatabaseHelper.actionWithStatement((statement) ->
                    internalAction(statement, request), SELECT);
            return Controller.ok(response);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
    }

    private JsonNode internalAction(final PreparedStatement statement, final HoroscopesRequest request) throws SQLException {
        statement.setString(1, request.getKind());
        statement.setString(2, request.getSign());
        statement.setString(3, this.getLocale(request));

        try (ResultSet resultSet = statement.executeQuery()) {
            final ObjectNode content = Json.newObject();
            while (resultSet.next()) {
                final ObjectNode sign = Json.newObject();
                sign.put(resultSet.getString(2), resultSet.getString(3));
                final ObjectNode kind = Json.newObject();
                kind.set(json.get(PARAM_SIGN).textValue(), sign);
                final ObjectNode type = Json.newObject();
                type.set(DEFAULT_KIND, kind);
                content.set(resultSet.getString(1), type);
            }
            return BaseResponse.content(content).asJson();
        }
    }

    private void translateAll(final HoroscopesRequest request, final CharSequence where) throws SQLException {
        DatabaseHelper.actionWithDatabase((connection) -> translateAll(connection, TRANSLATE + where, request));
    }

    /**
     * {@inheritDoc}
     */
    protected void bindStatement(final PreparedStatement statement, final HoroscopesRequest request,
                                 final String locale) throws SQLException {
        statement.setString(1, DEFAULT_KIND);
        statement.setString(2, request.getSign());
        statement.setString(3, DEFAULT_LOCALE);
        statement.setString(4, DEFAULT_KIND);
        statement.setString(5, request.getSign());
        statement.setString(6, locale);
    }
}
