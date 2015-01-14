package helpers.controllers.content.image;

import helpers.DatabaseHelper;
import helpers.controllers.AbstractControllerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 23.10.2014.
 */
public final class ImageIndexHelper extends AbstractControllerHelper {

    private static final Logger.ALogger LOG = Logger.of(ImageIndexHelper.class);

    private static final String UPLOADS_PATH = "/home/meugen/horoscopes.meugen.in.ua/uploads";
    private static final String GET_MIME_SQL = "select mime from horo_uploads where name=?";

    private final String name;

    /**
     * Constructor.
     *
     * @param name Image name
     */
    public ImageIndexHelper(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    protected Result action() {
        Result result;

        final String mime = getMimeByName();
        if (mime == null) {
            result = Controller.notFound();
        } else {
            final File image = new File(UPLOADS_PATH, name);
            result = image.exists() ? Controller.ok(image, true).as(mime) : Controller.notFound();
        }
        return result;
    }

    private String getMimeByName() {
        String mime = null;
        try {
            mime = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<String>() {
                public String onAction(PreparedStatement statement) throws SQLException {
                    return getMimeByName(statement);
                }
            }, GET_MIME_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return mime;
    }

    private String getMimeByName(final PreparedStatement statement) throws SQLException {
        statement.setString(1, this.name);
        final ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getString(1) : null;
    }
}
