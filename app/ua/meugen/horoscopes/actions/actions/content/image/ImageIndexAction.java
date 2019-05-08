package ua.meugen.horoscopes.actions.actions.content.image;

import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.AbstractJsonControllerAction;
import ua.meugen.horoscopes.entities.Upload;
import ua.meugen.horoscopes.queries.QueryBuilder;

import javax.annotation.Nullable;
import java.io.File;

public final class ImageIndexAction extends AbstractJsonControllerAction<String> {

    private static final String UPLOADS_PATH = "/home/meugen/horo.meugen.in.ua/uploads";

    @Inject
    private QueryBuilder<Upload, String> builder;

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

    @Nullable
    private String getMimeByName(final String name) {
        final Upload upload = builder.build(name).findOne();
        return upload == null ? null : upload.getMime();
    }
}
