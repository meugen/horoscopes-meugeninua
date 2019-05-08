package ua.meugen.horoscopes.actions.actions.content.update;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.Logger;
import play.libs.XML;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.entities.Period;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UpdateWeeklyAction extends AbstractUpdateAction<BaseResponse> {

    private static final Logger.ALogger LOG = Logger.of(UpdateWeeklyAction.class);

    private static final String TYPE_WEEKLY = "weekly";

    @Inject @Named("monday-weekly-periods")
    private Map<String, Object> mondayWeeklyPeriods;
    @Inject @Named("saturday-weekly-periods")
    private Map<String, Object> saturdayWeeklyPeriods;
    @Inject @Named("month-codes")
    private Map<String, Integer> monthCodes;

    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseResponse newResponse() {
        return new BaseResponse();
    }

    /**
     * {@inheritDoc}
     */
    public BaseResponse internalAction() {
        final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            this.internalAction(saturdayWeeklyPeriods);
        } else {
            this.internalAction(mondayWeeklyPeriods);
        }
        return this.factory.newOkResponse();
    }

    private void internalAction(final Map<String, Object> periods) {
        try {
            for (Map.Entry<String, Object> entry : periods.entrySet()) {
                final Object value = entry.getValue();
                if (value instanceof Integer && (Integer) value == -1) {
                    this.server.createNamedQuery(Horoscope.class, Horoscope.HOROSCOPE_DELETE_OLD_PERIODS)
                            .setParameter("type", TYPE_WEEKLY)
                            .setParameter("period", entry.getKey())
                            .asUpdate().update();
                    this.server.createNamedQuery(Period.class, Period.PERIOD_DELETE_OLD_PERIODS)
                            .setParameter("type", TYPE_WEEKLY)
                            .setParameter("period", entry.getKey())
                            .asUpdate().update();
                } else if (value instanceof String) {
                    final String xml = fileGetContents(value.toString());
                    final Document data = XML.fromString(xml);
                    final NodeList signs = data.getFirstChild().getChildNodes();
                    final String periodValue = data.getElementsByTagName("date")
                            .item(0).getAttributes().getNamedItem(TYPE_WEEKLY)
                            .getTextContent();
                    final String internationalPeriod = this.toInternationalPeriod(periodValue);
                    for (int i = 0; i < signs.getLength(); i++) {
                        final Node sign = signs.item(i);
                        final NodeList kinds = sign.getChildNodes();
                        for (int j = 0; j < kinds.getLength(); j++) {
                            final Node kind = kinds.item(j);

                            this.insertOrUpdateContent(TYPE_WEEKLY, kind.getNodeName(), sign.getNodeName(),
                                    periodValue, kind.getTextContent());
                            this.insertOrUpdateContent(TYPE_WEEKLY, kind.getNodeName(), sign.getNodeName(),
                                    internationalPeriod, kind.getTextContent());
                        }
                    }
                    this.insertOrUpdatePeriod(TYPE_WEEKLY, entry.getKey(), internationalPeriod);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private String toInternationalPeriod(final String ruPeriod) throws ParseException {
        final Pattern pattern = Pattern.compile("^(\\d+) (([^\\d\\s]+) )?- (\\d+) ([^\\d\\s]+)$");
        final Matcher matcher = pattern.matcher(ruPeriod);
        if (!matcher.find()) {
            throw new ParseException("Not valid period: " + ruPeriod, 0);
        }
        final Calendar from = Calendar.getInstance();
        from.set(Calendar.DAY_OF_MONTH, Integer.valueOf(matcher.group(1)));
        from.set(Calendar.MONTH, monthCodes.get(matcher.group(3) == null
                ? matcher.group(5) : matcher.group(3)));
        final Calendar to = Calendar.getInstance();
        to.set(Calendar.DAY_OF_MONTH, Integer.valueOf(matcher.group(4)));
        to.set(Calendar.MONTH, monthCodes.get(matcher.group(5)));

        return String.format(Locale.ENGLISH, "%1$td.%1$tm-%2$td.%2$tm", from, to);
    }
}
