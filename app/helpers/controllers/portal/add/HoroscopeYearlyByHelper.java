package helpers.controllers.portal.add;

import controllers.portal.add.routes;
import helpers.controllers.AbstractControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by admin on 31.10.2014.
 */
final class HoroscopeYearlyByHelper extends AbstractControllerHelper {

    private static final Logger.ALogger LOG = Logger.of(HoroscopeYearlyByHelper.class);
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MMM.yyyy", Locale.ENGLISH);

    private String year;

    private Calendar calendar;

    private void prepareCalendar() throws ParseException {
        final Date date = FORMATTER.parse(String.format("01.Jan.%s", this.year));
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(date.getTime());
    }

    /**
     * Getter for year.
     * @return Year
     */
    public String getYear() {
        return year;
    }

    /**
     * Setter for year.
     * @param year Year
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * {@inheritDoc}
     */
    protected Result action() {
        try {
            this.prepareCalendar();
            return Controller.ok(DateFormat.getDateInstance(DateFormat.LONG).format(this.calendar.getTime()));
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            return Controller.redirect(routes.Horoscope.yearly());
        }
    }
}
