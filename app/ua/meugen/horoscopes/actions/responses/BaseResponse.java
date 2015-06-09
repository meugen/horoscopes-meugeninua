package ua.meugen.horoscopes.actions.responses;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

/**
 * Response for controller's methods.
 *
 * @author meugen
 */
public class BaseResponse {

    private String message;
    private Status status;

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
     * @return BaseResponse json
     */
    public final JsonNode asJson() {
        return Json.toJson(this);
    }

    /**
     * Response status.
     */
    public enum Status {
        OK, ERROR, NOT_FOUND
    }

}
