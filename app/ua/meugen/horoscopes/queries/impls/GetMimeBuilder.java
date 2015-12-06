package ua.meugen.horoscopes.queries.impls;

import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import ua.meugen.horoscopes.entities.Upload;
import ua.meugen.horoscopes.queries.QueryBuilder;

public final class GetMimeBuilder implements QueryBuilder<Upload, String> {

    @Inject
    private Model.Find<Integer, Upload> find;

    /**
     * {@inheritDoc}
     */
    @Override
    public Query<Upload> build(final String request) {
        return find.select("mime").where().eq("name", request).query();
    }
}
