package ua.meugen.horoscopes.actions.actions.content.get;

import io.ebean.Ebean;
import io.ebean.Query;
import ua.meugen.horoscopes.actions.TranslateHelper;
import ua.meugen.horoscopes.actions.actions.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract class TranslateHoroscopesAction<Req extends BaseHoroscopesRequest> extends AbstractJsonControllerAction<Req> {

    /**
     * Default constructor.
     *
     * @param reqClass Class for request
     */
    protected TranslateHoroscopesAction(final Class<Req> reqClass) {
        super(reqClass);
    }

    protected final Void translateAll(final QueryBuilder<Horoscope, Req> queryBuilder,
                                      final Req request) {
        final String locale = HoroscopeUtils.getLocale(request);
        if (HoroscopeUtils.DEFAULT_LOCALE.equals(locale)) {
            return null;
        }

        synchronized (TranslateHoroscopesAction.class) {
            try {
                final Query<Horoscope> query = queryBuilder.build(request);
                final List<Horoscope> horoscopes = query.findList();

                final List<String> contents = horoscopes.stream()
                        .map(Horoscope::getContent).collect(Collectors.toList());
                if (!contents.isEmpty()) {
                    final TranslateHelper helper = new TranslateHelper();
                    helper.setTarget(locale);
                    final List<String> translatedContents = helper.translateAll(contents);

                    final int count = translatedContents.size();
                    final List<Horoscope> newHoroscopes = new ArrayList<>(count);
                    for (int i = 0; i < count; i++) {
                        final Horoscope horoscope = horoscopes.get(i);

                        final Horoscope newHoroscope = new Horoscope();
                        newHoroscope.setType(horoscope.getType());
                        newHoroscope.setKind(this.getKind(request));
                        newHoroscope.setSign(request.getSign());
                        newHoroscope.setPeriod(horoscope.getPeriod());
                        newHoroscope.setLocale(locale);
                        newHoroscope.setContent(translatedContents.get(i));
                        newHoroscopes.add(newHoroscope);
                    }
                    Ebean.save(newHoroscopes);
                }
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract String getKind(final Req request);
}
