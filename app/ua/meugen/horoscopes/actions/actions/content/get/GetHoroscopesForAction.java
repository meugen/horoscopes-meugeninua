package ua.meugen.horoscopes.actions.actions.content.get;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.dto.HoroscopesForDto;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.actions.responses.HoroscopesForResponse;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.queries.QueryBuilder;
import ua.meugen.horoscopes.utils.HoroscopeUtils;

import java.util.stream.Collectors;

/**
 * Helper for get horoscopes for sign.
 *
 * @author meugen
 */
public final class GetHoroscopesForAction extends TranslateHoroscopesAction<BaseHoroscopesRequest> {

    private static final Logger.ALogger LOG = Logger.of(GetHoroscopesForAction.class);

    private final ControllerResponsesFactory<HoroscopesForResponse> factory;

    @Inject @Named("translate")
    private QueryBuilder<Horoscope, BaseHoroscopesRequest> translateQueryBuilder;
    @Inject @Named("get")
    private QueryBuilder<Horoscope, BaseHoroscopesRequest> getQueryBuilder;

    /**
     * Default constructor.
     */
    public GetHoroscopesForAction() {
        super(BaseHoroscopesRequest.class);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    private HoroscopesForResponse newResponse() {
        return new HoroscopesForResponse();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final BaseHoroscopesRequest request) {
        this.translateAll(translateQueryBuilder, request);
        final HoroscopesForResponse response = internalAction(request);
        return Controller.ok(response.asJson());
    }

    private HoroscopesForResponse internalAction(final BaseHoroscopesRequest request) {
        final HoroscopesForDto dto = new HoroscopesForDto();
        dto.setSign(request.getSign());
        dto.setItems(getQueryBuilder.build(request).findList().stream()
                .map(this::toContainer).collect(Collectors.toList()));

        final HoroscopesForResponse response = factory.newOkResponse();
        response.setContent(dto);
        return response;
    }

    private HoroscopesForDto.Container toContainer(final Horoscope horoscope) {
        final HoroscopesForDto.Container container = new HoroscopesForDto.Container();
        container.setType(horoscope.getType());
        container.setKind(HoroscopeUtils.DEFAULT_KIND);
        container.setPeriod(horoscope.getPeriod());
        container.setHoroscope(horoscope.getContent());
        return container;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getKind(final BaseHoroscopesRequest request) {
        return HoroscopeUtils.DEFAULT_KIND;
    }
}
