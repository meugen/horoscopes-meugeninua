package controllers.content;

import helpers.DatabaseHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.db.DB;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 24.06.14.
 */
public final class Image extends Controller {

    private static final Log LOG = LogFactory.getLog(Image.class);

    private static final String UPLOADS_PATH = "/home/meugen/horoscopes.meugen.in.ua/uploads";
    private static final String GET_MIME_SQL = "select mime from horo_uploads where name=?";

    public static Result index(final String name) {
        Result result;

        final String mime = getMimeByName(name);
        if (mime == null) {
            result = notFound();
        } else {
            final File image = new File(UPLOADS_PATH, name);
            result = image.exists() ? ok(image, true).as(mime) : notFound();
        }
        return result;
    }

    private static String getMimeByName(final String name) {
        String mime = null;
        try {
            mime = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<String>() {
                public String onAction(PreparedStatement statement) throws SQLException {
                    return getMimeByName(statement, name);
                }
            }, GET_MIME_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return mime;
    }

    private static String getMimeByName(final PreparedStatement statement, final String name) throws SQLException {
        statement.setString(1, name);
        final ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getString(1) : null;
    }
}
