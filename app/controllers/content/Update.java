package controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.controllers.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.libs.F;
import play.libs.Json;
import play.libs.XML;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meugen on 03.07.14.
 */
public final class Update extends Controller {

    private static final Log LOG = LogFactory.getLog(Update.class);

    private static final int BUF_SIZE = 1024;

    private static final String REPLACE_CONTENT_SQL = "REPLACE INTO horo_texts" +
            " (type, kind, sign, period, content) VALUE (?, ?, ?, ?, ?)";
    private static final String REPLACE_PERIOD_SQL = "replace into horo_periods" +
            " (type, period, `key`) value (?, ?, ?)";
    private static final String DELETE_CONTENT_SQL = "delete from horo_texts where type=? and period=?";
    private static final String DELETE_PERIOD_SQL = "delete from horo_periods where type=? and period=?";
    private static final String INSERT_RESPONSE_SQL = "INSERT INTO horo_updates (uri, response) VALUE (?, ?)";
    private static final String TYPE_DAILY = "daily";
    private static final String TYPE_WEEKLY = "weekly";

    private static final Map<String, String> DAILY_KINDS;
    private static final Map<String, Object> MONDAY_WEEKLY_PERIODS;
    private static final Map<String, Object> SATURDAY_WEEKLY_PERIODS;

    private static PreparedStatement replaceContentStatement;
    private static PreparedStatement replacePeriodStatement;
    private static PreparedStatement deleteContentStatement;
    private static PreparedStatement deletePeriodStatement;

    static {
        DAILY_KINDS = new HashMap<>();
        DAILY_KINDS.put("common", "http://img.ignio.com/r/export/utf/xml/daily/com.xml");
        DAILY_KINDS.put("erotic", "http://img.ignio.com/r/export/utf/xml/daily/ero.xml");
        DAILY_KINDS.put("anti", "http://img.ignio.com/r/export/utf/xml/daily/anti.xml");
        DAILY_KINDS.put("business", "http://img.ignio.com/r/export/utf/xml/daily/bus.xml");
        DAILY_KINDS.put("health", "http://img.ignio.com/r/export/utf/xml/daily/hea.xml");
        DAILY_KINDS.put("cook", "http://img.ignio.com/r/export/utf/xml/daily/cook.xml");
        DAILY_KINDS.put("love", "http://img.ignio.com/r/export/utf/xml/daily/lov.xml");
        DAILY_KINDS.put("mobile", "http://img.ignio.com/r/export/utf/xml/daily/mob.xml");

        MONDAY_WEEKLY_PERIODS = new HashMap<>();
        MONDAY_WEEKLY_PERIODS.put("next", -1);
        MONDAY_WEEKLY_PERIODS.put("cur", "http://img.ignio.com/r/export/utf/xml/weekly/cur.xml");
        MONDAY_WEEKLY_PERIODS.put("prev", "http://img.ignio.com/r/export/utf/xml/weekly/prev.xml");

        SATURDAY_WEEKLY_PERIODS = new HashMap<>();
        SATURDAY_WEEKLY_PERIODS.put("next", "http://img.ignio.com/r/export/utf/xml/weekly/cur.xml");
        SATURDAY_WEEKLY_PERIODS.put("cur", "http://img.ignio.com/r/export/utf/xml/weekly/prev.xml");
        SATURDAY_WEEKLY_PERIODS.put("prev", 0);
    }

    public static F.Promise<Result> all() {
        final F.Promise<JsonNode> promise = F.Promise.promise(new F.Function0<JsonNode>() {
            public JsonNode apply() throws Throwable {
                return internalAll();
            }
        });
        return promise.map(new F.Function<JsonNode, Result>() {
            public Result apply(final JsonNode json) throws Throwable {
                return ok(json);
            }
        });
    }

    private static JsonNode internalAll() throws SQLException {
        final JsonNode response = DatabaseHelper.actionWithDatabase(new DatabaseHelper.ConnectionAction<JsonNode>() {
            public JsonNode onAction(final Connection connection) throws SQLException {
                return update(connection);
            }
        });
        DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Void>() {
            public Void onAction(final PreparedStatement statement) throws SQLException {
                storeResponse(statement, response);
                return null;
            }
        }, INSERT_RESPONSE_SQL);
        return response;
    }

    private static JsonNode update(final Connection connection) throws SQLException {
        JsonNode result;
        try {
            connection.setAutoCommit(false);

            replaceContentStatement = connection.prepareStatement(REPLACE_CONTENT_SQL);
            replacePeriodStatement = connection.prepareStatement(REPLACE_PERIOD_SQL);
            updateDaily();
            deleteContentStatement = connection.prepareStatement(DELETE_CONTENT_SQL);
            deletePeriodStatement = connection.prepareStatement(DELETE_PERIOD_SQL);
            final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.MONDAY) {
                updateWeekly(MONDAY_WEEKLY_PERIODS);
            } else if (dayOfWeek == Calendar.SATURDAY) {
                updateWeekly(SATURDAY_WEEKLY_PERIODS);
            }
            connection.commit();

            result = buildResponse("", Response.Status.OK);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            result = buildResponse(e.getMessage(), Response.Status.ERROR);
        } finally {
            if (replaceContentStatement != null) {
                replaceContentStatement.close();
                replaceContentStatement = null;
            }
            if (replacePeriodStatement != null) {
                replacePeriodStatement.close();
                replacePeriodStatement = null;
            }
            if (deleteContentStatement != null) {
                deleteContentStatement.close();
                deleteContentStatement = null;
            }
            if (deletePeriodStatement != null) {
                deletePeriodStatement.close();
                deletePeriodStatement = null;
            }
        }
        return result;
    }

    private static void updateDaily() throws SQLException, IOException {
        for (Map.Entry<String, String> entry : DAILY_KINDS.entrySet()) {
            final String xml = fileGetContents(entry.getValue());
            final Document data = XML.fromString(xml);
            final NodeList signs = data.getFirstChild().getChildNodes();
            for (int i = 0; i < signs.getLength(); i++) {
                final Node sign = signs.item(i);
                final NodeList periods = sign.getChildNodes();
                for (int j = 0; j < periods.getLength(); j++) {
                    final Node period = periods.item(j);

                    replaceContentStatement.clearParameters();
                    replaceContentStatement.setString(1, TYPE_DAILY);
                    replaceContentStatement.setString(2, entry.getKey());
                    replaceContentStatement.setString(3, sign.getNodeName());
                    replaceContentStatement.setString(4, period.getNodeName());
                    replaceContentStatement.setString(5, period.getTextContent());
                    replaceContentStatement.executeUpdate();

                    final String periodValue = data.getElementsByTagName("date")
                            .item(0).getAttributes().getNamedItem(period.getNodeName())
                            .getTextContent();
                    replaceContentStatement.clearParameters();
                    replaceContentStatement.setString(1, TYPE_DAILY);
                    replaceContentStatement.setString(2, entry.getKey());
                    replaceContentStatement.setString(3, sign.getNodeName());
                    replaceContentStatement.setString(4, periodValue);
                    replaceContentStatement.setString(5, period.getTextContent());
                    replaceContentStatement.executeUpdate();

                    replacePeriodStatement.clearParameters();
                    replacePeriodStatement.setString(1, TYPE_DAILY);
                    replacePeriodStatement.setString(2, period.getNodeName());
                    replacePeriodStatement.setString(3, periodValue);
                    replacePeriodStatement.executeUpdate();
                }
            }
        }
    }

    private static void updateWeekly(final Map<String, Object> periods) throws SQLException, IOException {
        for (Map.Entry<String, Object> entry : periods.entrySet()) {
            final Object value = entry.getValue();
            if (value instanceof Integer && (Integer) value == -1) {
                deleteContentStatement.clearParameters();
                deleteContentStatement.setString(1, TYPE_WEEKLY);
                deleteContentStatement.setString(2, entry.getKey());
                deleteContentStatement.executeUpdate();

                deletePeriodStatement.clearParameters();
                deletePeriodStatement.setString(1, TYPE_WEEKLY);
                deletePeriodStatement.setString(2, entry.getKey());
                deletePeriodStatement.executeUpdate();
            } else if (value instanceof String) {
                final String xml = fileGetContents(value.toString());
                final Document data = XML.fromString(xml);
                final NodeList signs = data.getFirstChild().getChildNodes();
                final String periodValue = data.getElementsByTagName("date")
                        .item(0).getAttributes().getNamedItem(TYPE_WEEKLY)
                        .getTextContent();
                for (int i = 0; i < signs.getLength(); i++) {
                    final Node sign = signs.item(i);
                    final NodeList kinds = sign.getChildNodes();
                    for (int j = 0; j < kinds.getLength(); j++) {
                        final Node kind = kinds.item(j);

                        replaceContentStatement.clearParameters();
                        replaceContentStatement.setString(1, TYPE_WEEKLY);
                        replaceContentStatement.setString(2, kind.getNodeName());
                        replaceContentStatement.setString(3, sign.getNodeName());
                        replaceContentStatement.setString(4, entry.getKey());
                        replaceContentStatement.setString(5, kind.getTextContent());
                        replaceContentStatement.executeUpdate();

                        replaceContentStatement.clearParameters();
                        replaceContentStatement.setString(1, TYPE_WEEKLY);
                        replaceContentStatement.setString(2, kind.getNodeName());
                        replaceContentStatement.setString(3, sign.getNodeName());
                        replaceContentStatement.setString(4, periodValue);
                        replaceContentStatement.setString(5, kind.getTextContent());
                        replaceContentStatement.executeUpdate();
                    }
                }
                replacePeriodStatement.clearParameters();
                replacePeriodStatement.setString(1, TYPE_WEEKLY);
                replacePeriodStatement.setString(2, entry.getKey());
                replacePeriodStatement.setString(3, periodValue);
                replacePeriodStatement.executeUpdate();
            }
        }
    }

    private static String fileGetContents(final String urlString) throws IOException {
        final URL url = new URL(urlString);
        final InputStreamReader in = new InputStreamReader(url.openStream(), "UTF-8");
        final StringWriter out = new StringWriter();
        final char[] buf = new char[BUF_SIZE];
        while (true) {
            final int count = in.read(buf);
            if (count < 0) {
                break;
            }
            out.write(buf, 0, count);
        }
        return out.toString().replace(">\n", ">").replace("\n<", "<");
    }

    private static JsonNode buildResponse(final String message, final Response.Status status) {
        final ObjectNode response = Json.newObject();
        response.put("message", message);
        response.put("status", status.name());
        return response;
    }

    private static void storeResponse(final PreparedStatement statement, final JsonNode response) throws SQLException {
        statement.setString(1, request().uri());
        statement.setString(2, response.toString());
        statement.executeUpdate();
    }
}
