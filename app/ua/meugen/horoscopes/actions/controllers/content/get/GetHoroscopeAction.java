package ua.meugen.horoscopes.actions.controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.dto.HoroscopesDto;
import ua.meugen.horoscopes.actions.requests.HoroscopesRequest;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.responses.HoroscopesResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 23.10.2014.
 */
@Component
public final class GetHoroscopeAction extends TranslateHoroscopesAction {

    private static final Logger.ALogger LOG = Logger.of(GetHoroscopeAction.class);

    private static final String GET_HOROSCOPE_SQL = "SELECT DISTINCT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=?";
    private static final String GET_HOROSCOPE_PERIOD_SQL = "SELECT DISTINCT t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=? and t2.period=?";
    private static final String TRANSLATE_HOROSCOPE_SQL = "SELECT DISTINCT t1.type, t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=? and t1.period not in (SELECT t1.period FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=?)";
    private static final String TRANSLATE_HOROSCOPE_PERIOD_SQL = "SELECT  DISTINCT t1.type, t1.period, t1.content FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=? and t1.period not in (SELECT t1.period FROM horo_texts t1," +
            " horo_periods t2 WHERE t2.key=t1.period and t2.type=t1.type and t1.type=? and t1.kind=?" +
            " and t1.sign=? and t1.locale=? and t2.version=? and t2.period=?) and t2.period=?";

    private String period;

    /**
     * Getter for period.
     *
     * @return Period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Setter for period.
     *
     * @param period Period
     */
    public void setPeriod(final String period) {
        this.period = period;
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final HoroscopesRequest request) {
        Result result;
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

            translateAll(request, where);
            final HoroscopesResponse response = DatabaseHelper.actionWithDatabase((connection) ->
                    internalAction(connection, request, where));
            result = Controller.ok(response.asJson());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
        return result;
    }

    private void translateAll(final HoroscopesRequest request, final CharSequence where) throws SQLException {
        final String sql = this.period == null ? TRANSLATE_HOROSCOPE_SQL : TRANSLATE_HOROSCOPE_PERIOD_SQL;
        DatabaseHelper.actionWithDatabase((connection) -> translateAll(connection, sql + where, request));
    }

    /**
     * {@inheritDoc}
     */
    protected void bindStatement(final PreparedStatement statement, final HoroscopesRequest request,
                                 final String locale) throws SQLException {
        final int version = request.getVersion();
        statement.setString(1, request.getType());
        statement.setString(2, request.getKind());
        statement.setString(3, request.getSign());
        statement.setString(4, DEFAULT_LOCALE);
        statement.setInt(5, version);
        statement.setString(6, request.getType());
        statement.setString(7, request.getKind());
        statement.setString(8, request.getSign());
        statement.setString(9, locale);
        statement.setInt(10, version);
        if (this.period != null) {
            statement.setString(11, this.period);
            statement.setString(12, this.period);
        }
    }

    private HoroscopesResponse internalAction(final Connection connection, final HoroscopesRequest request,
                                              final CharSequence where) throws SQLException {
        final String sql = this.period == null ? GET_HOROSCOPE_SQL : GET_HOROSCOPE_PERIOD_SQL;
        try (PreparedStatement statement = connection.prepareStatement(sql + where)) {
            return this.internalAction(statement, request);
        }
    }

    private HoroscopesResponse internalAction(final PreparedStatement statement, final HoroscopesRequest request) throws SQLException {
        statement.setString(1, request.getType());
        statement.setString(2, request.getKind());
        statement.setString(3, request.getSign());
        statement.setString(4, this.getLocale(request));
        statement.setInt(5, request.getVersion());
        if (this.period != null) {
            statement.setString(6, this.period);
        }

        try (ResultSet resultSet = statement.executeQuery()) {
            final Map<String, String> horoscopes = new HashMap<>();
            while (resultSet.next()) {
                horoscopes.put(resultSet.getString(1), resultSet.getString(2));
            }
            final HoroscopesDto horoscopesDto = new HoroscopesDto();
            horoscopesDto.setType(request.getType());
            horoscopesDto.setKind(request.getKind());
            horoscopesDto.setSign(request.getSign());
            horoscopesDto.setHoroscopes(horoscopes);
            final HoroscopesResponse response = this.factory.newOkResponse();
            response.setContent(horoscopesDto);
            return response;
        }
    }
}
