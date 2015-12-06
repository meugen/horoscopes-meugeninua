package ua.meugen.horoscopes.actions.actions.content.get;

import com.avaje.ebean.Model;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.AbstractJsonControllerAction;
import ua.meugen.horoscopes.actions.actions.ControllerResponsesFactory;
import ua.meugen.horoscopes.actions.responses.ContentResponse;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

abstract class AbstractGetByIdAction<Dto, Entity> extends AbstractJsonControllerAction<Integer> {

    private static final Logger.ALogger LOG = Logger.of(AbstractGetByIdAction.class);

    private final ControllerResponsesFactory<ContentResponse<Dto>> factory;
    private final Model.Find<Integer, Entity> find;
    private final EntityToDtoFetcher<Entity, Dto> fetcher;

    /**
     * Constructor.
     * @param find Find entity util
     * @param fetcher Fetcher from entity to Dto
     */
    protected AbstractGetByIdAction(final Model.Find<Integer, Entity> find,
                                    final EntityToDtoFetcher<Entity, Dto> fetcher) {
        super(Integer.class);
        this.find = find;
        this.fetcher = fetcher;
        this.factory = new ControllerResponsesFactory<>(this::newResponse);
    }

    /**
     * Create new response.
     * @return New response
     */
    protected abstract ContentResponse<Dto> newResponse();

    /**
     * {@inheritDoc}
     */
    protected Result action(final Integer request) {
        Result result;
        try {
            final Entity entity = this.find.byId(request);

            ContentResponse<Dto> response;
            if (entity == null) {
                response = this.factory.newNotFoundResponse();
            } else {
                response = this.factory.newOkResponse();
                response.setContent(this.fetcher.fetchEntityToDto(entity));
            }
            result = Controller.ok(response.asJson());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = Controller.internalServerError(this.factory.newErrorResponse(e).asJson());
        }
        return result;
    }

}
