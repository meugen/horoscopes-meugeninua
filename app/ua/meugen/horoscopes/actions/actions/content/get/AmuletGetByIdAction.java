package ua.meugen.horoscopes.actions.actions.content.get;

import com.avaje.ebean.Model;
import com.google.inject.Inject;
import ua.meugen.horoscopes.actions.dto.AmuletContentDto;
import ua.meugen.horoscopes.actions.responses.ContentResponse;
import ua.meugen.horoscopes.entities.Amulet;
import ua.meugen.horoscopes.fetchers.EntityToDtoFetcher;

public final class AmuletGetByIdAction extends AbstractGetByIdAction<AmuletContentDto, Amulet> {

    /**
     * Default constructor.
     */
    @Inject
    public AmuletGetByIdAction(final Model.Find<Integer, Amulet> find,
                               final EntityToDtoFetcher<Amulet, AmuletContentDto> fetcher) {
        super(find, fetcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContentResponse<AmuletContentDto> newResponse() {
        return new ContentResponse<>();
    }
}
