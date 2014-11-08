package helpers.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

/**
 * Response for controller's methods.
 *
 * @author meugen
 */
public class Response {

    private String message;
    private Status status;

    /**
     * Create response with error.
     *
     * @param error Error
     * @return Created response
     */
    public static Response error(final Exception error) {
        final Response response = new Response();
        response.setMessage(error.getMessage());
        response.setStatus(Status.ERROR);
        return response;
    }

    /**
     * Create response with content.
     *
     * @param content Content
     * @return Created response
     */
    public static Response content(final JsonNode content) {
        final WithContent response = new WithContent();
        response.setStatus(Status.OK);
        response.setMessage("");
        response.setContent(content);
        return response;
    }

    private Response() {
    }

    /**
     * Create empty response.
     *
     * @return Created response
     */
    public static Response empty() {
        final Response response = new Response();
        response.setMessage("");
        response.setStatus(Status.OK);
        return response;
    }

    /**
     * Getter for message.
     *
     * @return Message
     */
    public final String getMessage() {
        return this.message;
    }

    /**
     * Setter for message.
     *
     * @param message Message
     */
    public final void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for status.
     *
     * @return Status
     */
    public final Status getStatus() {
        return this.status;
    }

    /**
     * Setter for status.
     *
     * @param status Status
     */
    public final void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Convert response to json.
     *
     * @return Response json
     */
    public final JsonNode asJson() {
        return Json.toJson(this);
    }

    /**
     * Response status.
     */
    public enum Status {
        OK, ERROR
    }

    private static class WithContent extends Response {

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
}
