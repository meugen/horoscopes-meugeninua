package ua.meugen.horoscopes.actions.controllers.content.image;

import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.DatabaseHelper;
import ua.meugen.horoscopes.actions.controllers.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.controllers.AbstractSimpleControllerAction;
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
@Component
public final class ImageIndexAction extends AbstractJsonControllerAction<String> {

    private static final Logger.ALogger LOG = Logger.of(ImageIndexAction.class);

    private static final String UPLOADS_PATH = "/home/meugen/horoscopes.meugen.in.ua/uploads";
    private static final String GET_MIME_SQL = "select mime from horo_uploads where name=?";

    /**
     * Default constructor.
     */
    public ImageIndexAction() {
        super(String.class);
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final String request) {
        Result result;

        final String mime = getMimeByName(request);
        if (mime == null) {
            result = Controller.notFound();
        } else {
            final File image = new File(UPLOADS_PATH, request);
            result = image.exists() ? Controller.ok(image, true).as(mime) : Controller.notFound();
        }
        return result;
    }

    private String getMimeByName(final String name) {
        String mime = null;
        try {
            mime = DatabaseHelper.actionWithStatement((statement) -> getMimeByName(statement, name), GET_MIME_SQL);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return mime;
    }

    private String getMimeByName(final PreparedStatement statement, final String name) throws SQLException {
        statement.setString(1, name);
        final ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getString(1) : null;
    }
}
