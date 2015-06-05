package ua.meugen.horoscopes.actions.controllers.content.update;

import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.Logger;
import play.libs.XML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 24.10.2014.
 */
@Component
public final class UpdateDailyAction extends AbstractUpdateAction<BaseResponse> {

    private static final Logger.ALogger LOG = Logger.of(UpdateDailyAction.class);

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

    public UpdateDailyAction() {

    }

    public UpdateDailyAction(final String uri) {
        this.setUri(uri);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseResponse newResponse() {
        return new BaseResponse();
    }

    public BaseResponse internalAction(final Connection connection) throws SQLException {
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

                        final String periodValue = data.getElementsByTagName("date")
                                .item(0).getAttributes().getNamedItem(period.getNodeName())
                                .getTextContent();
                        this.insertOrUpdateContent(TYPE_DAILY, entry.getKey(), sign.getNodeName(), periodValue,
                                period.getTextContent());
                        this.insertOrUpdatePeriod(TYPE_DAILY, period.getNodeName(), periodValue);
                    }
                }
            }
            return this.factory.newOkResponse();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            this.clearStatements();
        }
    }
}
