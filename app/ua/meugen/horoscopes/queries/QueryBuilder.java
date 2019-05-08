package ua.meugen.horoscopes.queries;

import io.ebean.Query;

public interface QueryBuilder<Entity, Request> {

    /**
     * Build query by specified request.
     *
     * @param request Request
     * @return Builded query
     */
    Query<Entity> build(Request request);
}
