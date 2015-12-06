package ua.meugen.horoscopes.actions.actions.content.get;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.dto.HoroscopesDto;
import ua.meugen.horoscopes.actions.requests.HoroscopesRequest;
import ua.meugen.horoscopes.actions.responses.HoroscopesResponse;
import ua.meugen.horoscopes.entities.Horoscope;
import ua.meugen.horoscopes.queries.QueryBuilder;

import java.util.stream.Collectors;

public final class GetHoroscopeAction extends TranslateHoroscopesAction<HoroscopesRequest> {

    private static final Logger.ALogger LOG = Logger.of(GetHoroscopeAction.class);

    private final ControllerResponsesFactory<HoroscopesResponse> factory;

    @Inject @Named("translate")
    private QueryBuilder<Horoscope, HoroscopesRequest> translateQueryBuilder;
    @Inject @Named("get")
    private QueryBuilder<Horoscope, HoroscopesRequest> getQueryBuilder;

    /**
     * Default constructor.
     */
    public GetHoroscopeAction() {
        super(HoroscopesRequest.class);
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    private HoroscopesResponse newResponse() {
        return new HoroscopesResponse();
    }

    /**
     * {@inheritDoc}
     */
    protected Result action(final HoroscopesRequest request) {
        translateAll(this.translateQueryBuilder, request);
        final HoroscopesResponse response = internalAction(request);
        return Controller.ok(response.asJson());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getKind(final HoroscopesRequest request) {
        return request.getKind();
    }

    private HoroscopesResponse internalAction(final HoroscopesRequest request) {
        final HoroscopesDto dto = new HoroscopesDto();
        dto.setType(request.getType());
        dto.setKind(request.getKind());
        dto.setSign(request.getSign());
        dto.setHoroscopes(this.getQueryBuilder.build(request).findList().stream()
                .collect(Collectors.toMap(Horoscope::getPeriod, Horoscope::getContent)));

        final HoroscopesResponse response = this.factory.newOkResponse();
        response.setContent(dto);
        return response;
    }
}
