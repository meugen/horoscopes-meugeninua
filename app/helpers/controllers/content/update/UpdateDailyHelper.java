package helpers.controllers.content.update;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.libs.XML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 24.10.2014.
 */
final class UpdateDailyHelper extends AbstractUpdateHelper {

    private static final Log LOG = LogFactory.getLog(UpdateDailyHelper.class);

    private static final String TYPE_DAILY = "daily";
    private static final Map<String, String> DAILY_KINDS;

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
    }

    /**
     * Constructor.
     *
     * @param uri URI
     */
    UpdateDailyHelper(final String uri) {
        super(uri);
    }

    public void internalAction(final Connection connection) throws SQLException {
        try {
            this.initStatements(connection);

            for (Map.Entry<String, String> entry : DAILY_KINDS.entrySet()) {
                final String xml = fileGetContents(entry.getValue());
                final Document data = XML.fromString(xml);
                final NodeList signs = data.getFirstChild().getChildNodes();
                for (int i = 0; i < signs.getLength(); i++) {
                    final Node sign = signs.item(i);
                    final NodeList periods = sign.getChildNodes();
                    for (int j = 0; j < periods.getLength(); j++) {
                        final Node period = periods.item(j);

                        updateContentStatement.clearParameters();
                        updateContentStatement.setString(1, period.getTextContent());
                        updateContentStatement.setString(2, TYPE_DAILY);
                        updateContentStatement.setString(3, entry.getKey());
                        updateContentStatement.setString(4, sign.getNodeName());
                        updateContentStatement.setString(5, period.getNodeName());
                        if (updateContentStatement.executeUpdate() == 0) {
                            insertContentStatement.clearParameters();
                            insertContentStatement.setString(1, TYPE_DAILY);
                            insertContentStatement.setString(2, entry.getKey());
                            insertContentStatement.setString(3, sign.getNodeName());
                            insertContentStatement.setString(4, period.getNodeName());
                            insertContentStatement.setString(5, period.getTextContent());
                            insertContentStatement.executeUpdate();
                        }

                        final String periodValue = data.getElementsByTagName("date")
                                .item(0).getAttributes().getNamedItem(period.getNodeName())
                                .getTextContent();
                        updateContentStatement.clearParameters();
                        updateContentStatement.setString(1, period.getTextContent());
                        updateContentStatement.setString(2, TYPE_DAILY);
                        updateContentStatement.setString(3, entry.getKey());
                        updateContentStatement.setString(4, sign.getNodeName());
                        updateContentStatement.setString(5, periodValue);
                        if (updateContentStatement.executeUpdate() == 0) {
                            insertContentStatement.clearParameters();
                            insertContentStatement.setString(1, TYPE_DAILY);
                            insertContentStatement.setString(2, entry.getKey());
                            insertContentStatement.setString(3, sign.getNodeName());
                            insertContentStatement.setString(4, periodValue);
                            insertContentStatement.setString(5, period.getTextContent());
                            insertContentStatement.executeUpdate();
                        }

                        updatePeriodStatement.clearParameters();
                        updatePeriodStatement.setString(1, periodValue);
                        updatePeriodStatement.setString(2, TYPE_DAILY);
                        updatePeriodStatement.setString(3, period.getNodeName());
                        if (updatePeriodStatement.executeUpdate() == 0) {
                            insertPeriodStatement.clearParameters();
                            insertPeriodStatement.setString(1, TYPE_DAILY);
                            insertPeriodStatement.setString(2, period.getNodeName());
                            insertPeriodStatement.setString(3, periodValue);
                            insertPeriodStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            this.clearStatements();
        }
    }
}
