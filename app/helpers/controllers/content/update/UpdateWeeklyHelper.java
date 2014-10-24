package helpers.controllers.content.update;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.DatabaseHelper;
import helpers.controllers.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.libs.XML;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 24.10.2014.
 */
final class UpdateWeeklyHelper extends AbstractUpdateHelper {

    private static final Log LOG = LogFactory.getLog(UpdateWeeklyHelper.class);

    private static final String TYPE_WEEKLY = "weekly";
    private static final String DELETE_CONTENT_SQL = "delete from horo_texts where type=? and period=?";
    private static final String DELETE_PERIOD_SQL = "delete from horo_periods where type=? and period=?";

    private static final Map<String, Object> MONDAY_WEEKLY_PERIODS;
    private static final Map<String, Object> SATURDAY_WEEKLY_PERIODS;

    static {
        MONDAY_WEEKLY_PERIODS = new HashMap<>();
        MONDAY_WEEKLY_PERIODS.put("next", -1);
        MONDAY_WEEKLY_PERIODS.put("cur", "http://img.ignio.com/r/export/utf/xml/weekly/cur.xml");
        MONDAY_WEEKLY_PERIODS.put("prev", "http://img.ignio.com/r/export/utf/xml/weekly/prev.xml");

        SATURDAY_WEEKLY_PERIODS = new HashMap<>();
        SATURDAY_WEEKLY_PERIODS.put("next", "http://img.ignio.com/r/export/utf/xml/weekly/cur.xml");
        SATURDAY_WEEKLY_PERIODS.put("cur", "http://img.ignio.com/r/export/utf/xml/weekly/prev.xml");
        SATURDAY_WEEKLY_PERIODS.put("prev", 0);
    }

    private PreparedStatement deleteContentStatement;
    private PreparedStatement deletePeriodStatement;

    /**
     * Constructor.
     * @param uri URI
     */
    UpdateWeeklyHelper(final String uri) {
        super(uri);
    }

    public void internalAction(final Connection connection) throws SQLException {
        final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            this.internalAction(connection, SATURDAY_WEEKLY_PERIODS);
        } else {
            this.internalAction(connection, MONDAY_WEEKLY_PERIODS);
        }
    }

    private void internalAction(final Connection connection, final Map<String, Object> periods) throws SQLException {
        try {
            initStatements(connection);

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
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            clearStatements();
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void initStatements(final Connection connection) throws SQLException {
        super.initStatements(connection);
        this.deleteContentStatement = connection.prepareStatement(DELETE_CONTENT_SQL);
        this.deletePeriodStatement = connection.prepareStatement(DELETE_PERIOD_SQL);
    }

    /**
     * {@inheritDoc}
     */
    protected void clearStatements() throws SQLException {
        super.clearStatements();
        if (this.deleteContentStatement != null) {
            this.deleteContentStatement.close();
            this.deleteContentStatement = null;
        }
        if (this.deletePeriodStatement != null) {
            this.deletePeriodStatement.close();
            this.deletePeriodStatement = null;
        }
    }
}
