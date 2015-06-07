package ua.meugen.horoscopes.actions.responses;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by admin on 02.06.2015.
 */
public final class ContentResponse extends BaseResponse {

    private JsonNode content;

    /**
     * Getter for content.
     *
     * @return Content
     */
    public JsonNode getContent() {
        return this.content;
    }

    /**
     * Getter for content.
     *
     * @param content Content
     */
    public void setContent(final JsonNode content) {
        this.content = content;
    }
}
