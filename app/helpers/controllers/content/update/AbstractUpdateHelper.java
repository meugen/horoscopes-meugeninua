package helpers.controllers.content.update;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.DatabaseHelper;
import helpers.controllers.Response;
import helpers.controllers.AbstractControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by admin on 24.10.2014.
 */
abstract class AbstractUpdateHelper extends AbstractControllerHelper {

    private static final Log LOG = LogFactory.getLog(AbstractUpdateHelper.class);

    private static final int BUF_SIZE = 1024;

    private static final String REPLACE_CONTENT_SQL = "REPLACE INTO horo_texts" +
            " (type, kind, sign, period, content) VALUE (?, ?, ?, ?, ?)";
    private static final String REPLACE_PERIOD_SQL = "replace into horo_periods" +
            " (type, period, `key`) value (?, ?, ?)";
    private static final String INSERT_RESPONSE_SQL = "INSERT INTO horo_updates (uri, response) VALUE (?, ?)";

    private final String uri;

    protected PreparedStatement replaceContentStatement;
    protected PreparedStatement replacePeriodStatement;

    /**
     * Constructor.
     * @param uri URI
     */
    protected AbstractUpdateHelper(final String uri) {
        this.uri = uri;
    }

    /**
     * Init statements with connection.
     * @param connection Connection
     * @throws SQLException On sql error
     */
    protected void initStatements(final Connection connection) throws SQLException {
        this.replaceContentStatement = connection.prepareStatement(REPLACE_CONTENT_SQL);
        this.replacePeriodStatement = connection.prepareStatement(REPLACE_PERIOD_SQL);
    }

    /**
     * Clear statements.
     * @throws SQLException On sql error
     */
    protected void clearStatements() throws SQLException {
        if (this.replaceContentStatement != null) {
            this.replaceContentStatement.close();
            this.replaceContentStatement = null;
        }
        if (this.replacePeriodStatement != null) {
            this.replacePeriodStatement.close();
            this.replacePeriodStatement = null;
        }
    }

    /**
     * Load file contents by url.
     * @param urlString URL
     * @return Contents
     * @throws IOException On some I/O error
     */
    protected final String fileGetContents(final String urlString) throws IOException {
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

    /**
     * {@inheritDoc}
     */
    protected final Result action() {
        Result result;
        JsonNode response;
        try {
            DatabaseHelper.actionWithDatabase(new DatabaseHelper.ConnectionAction<Void>() {
                public Void onAction(final Connection connection) throws SQLException {
                    AbstractUpdateHelper.this._internalAction(connection);
                    return null;
                }
            });
            response = Response.empty().asJson();
            result = Controller.ok(response);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            response = Response.error(e).asJson();
            result = Controller.internalServerError(response);
        }
        this.storeResponse(response);
        return result;
    }

    private void _internalAction(final Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        this.internalAction(connection);
        connection.commit();
    }

    /**
     * Internal action implementation.
     * @param connection Connection
     * @throws SQLException On some sql error
     */
    public abstract void internalAction(final Connection connection) throws SQLException;

    private void storeResponse(final JsonNode response) {
        try {
            DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Void>() {
                public Void onAction(final PreparedStatement statement) throws SQLException {
                    AbstractUpdateHelper.this.storeResponse(statement, response);
                    return null;
                }
            }, INSERT_RESPONSE_SQL);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void storeResponse(final PreparedStatement statement, final JsonNode response) throws SQLException {
        statement.setString(1, this.uri);
        statement.setString(2, response.toString());
        statement.executeUpdate();
    }
}
