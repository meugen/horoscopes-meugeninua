package helpers.controllers.content.update;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by admin on 27.10.2014.
 */
public class UpdateMonthlyHelper extends AbstractUpdateHelper {

    private String month;
    private String year;
    private boolean rewrite;
    private String url;

    /**
     * Constructor.
     *
     * @param uri URI
     */
    public UpdateMonthlyHelper(final String uri) {
        super(uri);
    }

    /**
     * Getter for month.
     *
     * @return Month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Setter for month.
     *
     * @param month Month
     */
    public void setMonth(final String month) {
        this.month = month;
    }

    /**
     * Getter for year.
     *
     * @return Year
     */
    public String getYear() {
        return year;
    }

    /**
     * Setter for year.
     *
     * @param year Year
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * Getter for rewrite option.
     *
     * @return Rewrite option
     */
    public boolean isRewrite() {
        return rewrite;
    }

    /**
     * Setter for rewrite option.
     *
     * @param rewrite Rewrite option
     */
    public void setRewrite(final boolean rewrite) {
        this.rewrite = rewrite;
    }

    /**
     * Getter for url.
     *
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for url.
     *
     * @param url URL
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * {@inheritDoc}
     */
    public void internalAction(final Connection connection) throws SQLException {

    }
}
