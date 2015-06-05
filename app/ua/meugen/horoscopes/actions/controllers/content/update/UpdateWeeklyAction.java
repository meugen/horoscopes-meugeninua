package ua.meugen.horoscopes.actions.controllers.content.update;

import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.Logger;
import play.i18n.Messages;
import play.libs.XML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 24.10.2014.
 */
@Component
public final class UpdateWeeklyAction extends AbstractUpdateAction<BaseResponse> {

    private static final Logger.ALogger LOG = Logger.of(UpdateWeeklyAction.class);

    private static final String TYPE_WEEKLY = "weekly";
    private static final String DELETE_CONTENT_SQL = "delete from horo_texts where type=? and period=?";
    private static final String DELETE_PERIOD_SQL = "delete from horo_periods where type=? and period=?";

    private static final Map<String, Object> MONDAY_WEEKLY_PERIODS;
    private static final Map<String, Object> SATURDAY_WEEKLY_PERIODS;
    private static final Map<String, Integer> MONTH_CODES;

    static {
        MONDAY_WEEKLY_PERIODS = new HashMap<>();
        MONDAY_WEEKLY_PERIODS.put("next", -1);
        MONDAY_WEEKLY_PERIODS.put("cur", "http://img.ignio.com/r/export/utf/xml/weekly/cur.xml");
        MONDAY_WEEKLY_PERIODS.put("prev", "http://img.ignio.com/r/export/utf/xml/weekly/prev.xml");

        SATURDAY_WEEKLY_PERIODS = new HashMap<>();
        SATURDAY_WEEKLY_PERIODS.put("next", "http://img.ignio.com/r/export/utf/xml/weekly/cur.xml");
        SATURDAY_WEEKLY_PERIODS.put("cur", "http://img.ignio.com/r/export/utf/xml/weekly/prev.xml");
        SATURDAY_WEEKLY_PERIODS.put("prev", 0);

        MONTH_CODES = new HashMap<>();
        MONTH_CODES.put(Messages.get("month.january"), Calendar.JANUARY);
        MONTH_CODES.put(Messages.get("month.february"), Calendar.FEBRUARY);
        MONTH_CODES.put(Messages.get("month.march"), Calendar.MARCH);
        MONTH_CODES.put(Messages.get("month.april"), Calendar.APRIL);
        MONTH_CODES.put(Messages.get("month.may"), Calendar.MAY);
        MONTH_CODES.put(Messages.get("month.june"), Calendar.JUNE);
        MONTH_CODES.put(Messages.get("month.july"), Calendar.JULY);
        MONTH_CODES.put(Messages.get("month.august"), Calendar.AUGUST);
        MONTH_CODES.put(Messages.get("month.september"), Calendar.SEPTEMBER);
        MONTH_CODES.put(Messages.get("month.october"), Calendar.OCTOBER);
        MONTH_CODES.put(Messages.get("month.november"), Calendar.NOVEMBER);
        MONTH_CODES.put(Messages.get("month.december"), Calendar.DECEMBER);
    }

    private PreparedStatement deleteContentStatement;
    private PreparedStatement deletePeriodStatement;

    public UpdateWeeklyAction() {

    }

    public UpdateWeeklyAction(final String uri) {
        this.setUri(uri);
    }

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
    public BaseResponse internalAction(final Connection connection) throws SQLException {
        final int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            this.internalAction(connection, SATURDAY_WEEKLY_PERIODS);
        } else {
            this.internalAction(connection, MONDAY_WEEKLY_PERIODS);
        }
        return this.factory.newOkResponse();
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
                    this.insertOrUpdatePeriod(TYPE_WEEKLY, entry.getKey(), periodValue);
                    this.insertOrUpdatePeriod(TYPE_WEEKLY, entry.getKey(), internationalPeriod, 2);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SQLException(e);
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

    private String toInternationalPeriod(final String ruPeriod) throws ParseException {
        final Pattern pattern = Pattern.compile("^(\\d+) (([^\\d\\s]+) )?- (\\d+) ([^\\d\\s]+)$");
        final Matcher matcher = pattern.matcher(ruPeriod);
        if (!matcher.find()) {
            throw new ParseException("Not valid period: " + ruPeriod, 0);
        }
        final Calendar from = Calendar.getInstance();
        from.set(Calendar.DAY_OF_MONTH, Integer.valueOf(matcher.group(1)));
        from.set(Calendar.MONTH, MONTH_CODES.get(matcher.group(3) == null
                ? matcher.group(5) : matcher.group(3)));
        final Calendar to = Calendar.getInstance();
        to.set(Calendar.DAY_OF_MONTH, Integer.valueOf(matcher.group(4)));
        to.set(Calendar.MONTH, MONTH_CODES.get(matcher.group(5)));

        return String.format(Locale.ENGLISH, "%1$td.%1$tm-%2$td.%2$tm", from, to);
    }
}
