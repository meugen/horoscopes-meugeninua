package ua.meugen.horoscopes.models;

/**
 * Created by meugen on 02.07.14.
 */
public final class MessageInfo {

    private int id;
    private String title;
    private String message;

    /**
     * Getter for id.
     *
     * @return Id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter for id.
     *
     * @param id Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for title.
     *
     * @return Title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for title.
     *
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for message.
     *
     * @return Message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Setter for message.
     *
     * @param message Message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
