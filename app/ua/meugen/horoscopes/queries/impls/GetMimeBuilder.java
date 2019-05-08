package ua.meugen.horoscopes.queries.impls;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Upload;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class GetMimeBuilder implements QueryBuilder<Upload, String> {

    @Inject
    private Finder<Integer, Upload> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Upload> build(final String request) {
        return find.query().select("mime").where().eq("name", request).query();
    }
}
