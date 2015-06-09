package ua.meugen.horoscopes.actions.responses;

import java.util.Map;

/**
 * Created by meugen on 08.06.15.
 */
public final class KeysForResponse extends BaseResponse {

    private Map<String, String> keys;

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(final Map<String, String> keys) {
        this.keys = keys;
    }
}
