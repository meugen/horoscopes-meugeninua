package ua.meugen.horoscopes.actions.requests;

/**
 * Created by meugen on 09.06.15.
 */
public class LimitTranslateRequest extends BaseTranslateRequest {

    private int limit;

    public int getLimit() {
        return limit;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }
}
