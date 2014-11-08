package helpers.controllers.content.update;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by admin on 27.10.2014.
 */
public class UpdateYearlyHelper extends AbstractUpdateHelper {

    private String url;
    private boolean rewrite;
    private String year;

    /**
     * Constructor.
     *
     * @param uri URI
     */
    public UpdateYearlyHelper(final String uri) {
        super(uri);
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
     * Getter for year.
     *
     * @return year
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
     * {@inheritDoc}
     */
    public void internalAction(final Connection connection) throws SQLException {

    }
}
