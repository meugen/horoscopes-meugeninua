package ua.meugen.horoscopes.actions.actions.content.get;

import com.avaje.ebean.Model;
import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.actions.responses.ContentResponse;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class SimpleGetByIdAction<Entity> extends AbstractGetByIdAction<BaseContentDto, Entity> {

    /**
     * Constructor.
     * @param find Model find implementation
     */
    public SimpleGetByIdAction(final Model.Find<Integer, Entity> find,
                               final EntityToDtoFetcher<Entity, BaseContentDto> fetcher) {
        super(find, fetcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContentResponse<BaseContentDto> newResponse() {
        return new ContentResponse<>();
    }
}
