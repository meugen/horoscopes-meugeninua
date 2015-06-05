package ua.meugen.horoscopes.actions.controllers.content.image;

import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.DatabaseHelper;
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
public final class ImageIndexAction extends AbstractSimpleControllerAction {

    private static final Logger.ALogger LOG = Logger.of(ImageIndexAction.class);

    private static final String UPLOADS_PATH = "/home/meugen/horoscopes.meugen.in.ua/uploads";
    private static final String GET_MIME_SQL = "select mime from horo_uploads where name=?";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
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
            mime = DatabaseHelper.actionWithStatement(this::getMimeByName, GET_MIME_SQL);
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
