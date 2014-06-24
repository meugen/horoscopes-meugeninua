package controllers.content;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Response for controller's methods.
 * @author meugen
 */
public final class Response {

    private JsonNode content;
    private String message;
    private Status status;

    /**
     * Create response with error.
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
     * @param content Content
     * @return Created response
     */
    public static Response content(final JsonNode content) {
        final Response response = new Response();
        response.setStatus(Status.OK);
        response.setMessage("");
        response.setContent(content);
        return response;
    }

    /**
     * Getter for content.
     * @return Content
     */
    public JsonNode getContent() {
        return this.content;
    }

    /**
     * Setter for content.
     * @param content Content
     */
    public void setContent(JsonNode content) {
        this.content = content;
    }

    /**
     * Getter for message.
     * @return Message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Setter for message.
     * @param message Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for status.
     * @return Status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Setter for status.
     * @param status Status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Convert response to json.
     * @return Response json
     */
    public JsonNode asJson() {
        return Json.toJson(this);
    }

    /**
     * Response status.
     */
    public enum Status {
        OK, ERROR
    }
}
