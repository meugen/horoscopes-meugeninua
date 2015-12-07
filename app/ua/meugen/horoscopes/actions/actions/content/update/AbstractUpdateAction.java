package ua.meugen.horoscopes.actions.actions.content.update;

import com.avaje.ebean.EbeanServer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.responses.BaseResponse;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.entities.Period;
import ua.meugen.horoscopes.entities.Update;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

abstract class AbstractUpdateAction<Resp extends BaseResponse> extends AbstractJsonControllerAction<String> {

    private static final Logger.ALogger LOG = Logger.of(AbstractUpdateAction.class);

    private static final int BUF_SIZE = 1024;

    /**
     * Factory for create responses.
     */
    protected final ControllerResponsesFactory<Resp> factory;

    @Inject
    protected EbeanServer server;

    protected AbstractUpdateAction() {
        super(String.class);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    /**
     * Create new response.
     *
     * @return Response
     */
    protected abstract Resp newResponse();

    private int getCount(final String type, final String kind, final String sign,
                         final String period) {
        return server.createNamedQuery(Horoscope.class, Horoscope.HOROSCOPE_COUNT)
                .setParameter("type", type).setParameter("kind", kind)
                .setParameter("sign", sign).setParameter("period", period)
                .findRowCount();
    }

    protected final void insertOrUpdateContent(final String type, final String kind, final String sign,
                                               final String period, final String content) {
        if (this.getCount(type, kind, sign, period) == 0) {
            final Horoscope horoscope = new Horoscope();
            horoscope.setType(type);
            horoscope.setKind(kind);
            horoscope.setSign(sign);
            horoscope.setPeriod(period);
            horoscope.setContent(content);
            horoscope.setLocale(HoroscopeUtils.DEFAULT_LOCALE);
            this.server.save(horoscope);
        }
    }

    protected final void insertOrUpdatePeriod(final String type, final String periodValue,
                                              final String periodKey) {
        Period period = this.server.createNamedQuery(Period.class, Period.PERIOD_BY_TYPE)
                .setParameter("type", type).setParameter("period", periodValue).findUnique();
        if (period == null) {
            period = new Period();
            period.setType(type);
            period.setValue(periodValue);
        }
        period.setKey(periodKey);
        this.server.save(period);
    }

    /**
     * Load file contents by url.
     *
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
    protected final Result action(final String request) {
        Result result;
        JsonNode response;
        try {
            response = server.execute(this::internalAction).asJson();
            result = Controller.ok(response);
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
            response = this.factory.newErrorResponse(e).asJson();
            result = Controller.internalServerError(response);
        }
        this.storeResponse(response, request);
        return result;
    }

    /**
     * Internal action implementation.
     */
    public abstract Resp internalAction();

    private void storeResponse(final JsonNode response, final String uri) {
        try {
            final Update update = new Update();
            update.setRespone(response.toString());
            update.setUri(uri);
            this.server.save(update);
        } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
