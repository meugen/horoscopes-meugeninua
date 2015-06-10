package ua.meugen.horoscopes.actions.responses;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 02.06.2015.
 */
public final class UpdateAllResponse extends BaseResponse {

    private Map<String, BaseResponse> content = new HashMap<>();

    /**
     * Getter for content.
     *
     * @return Content
     */
    public Map<String, BaseResponse> getContent() {
        return content;
    }

    /**
     * Setter for content.
     *
     * @param content Content
     */
    public void setContent(final Map<String, BaseResponse> content) {
        this.content = content;
    }
}
