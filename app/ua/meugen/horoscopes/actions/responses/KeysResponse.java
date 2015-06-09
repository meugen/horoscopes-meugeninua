package ua.meugen.horoscopes.actions.responses;

import java.util.List;

/**
 * Created by meugen on 08.06.15.
 */
public final class KeysResponse extends BaseResponse {

    private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(final List<String> keys) {
        this.keys = keys;
    }
}
