package ua.meugen.horoscopes.actions.actions.content.update;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.Logger;
import play.libs.XML;
import ua.meugen.horoscopes.actions.responses.BaseResponse;

import java.io.IOException;
import java.util.Map;

public final class UpdateDailyAction extends AbstractUpdateAction<BaseResponse> {

    private static final Logger.ALogger LOG = Logger.of(UpdateDailyAction.class);

    private static final String TYPE_DAILY = "daily";

    @Inject @Named("daily-kinds")
    private Map<String, String> dailyKinds;

    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseResponse newResponse() {
        return new BaseResponse();
    }

    public BaseResponse internalAction() {
        try {
            for (Map.Entry<String, String> entry : dailyKinds.entrySet()) {
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
        }
    }
}
