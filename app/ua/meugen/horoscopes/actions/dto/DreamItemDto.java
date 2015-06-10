package ua.meugen.horoscopes.actions.dto;

/**
 * Created by admin on 03.06.2015.
 */
public final class DreamItemDto {

    private int id;
    private int type;
    private String dream;

    /**
     * Getter for id.
     *
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id.
     *
     * @param id Id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Getter for type.
     *
     * @return Type
     */
    public int getType() {
        return type;
    }

    /**
     * Setter for type.
     *
     * @param type Type
     */
    public void setType(final int type) {
        this.type = type;
    }

    /**
     * Getter for dream.
     *
     * @return Dream
     */
    public String getDream() {
        return dream;
    }

    /**
     * Setter for dream.
     *
     * @param dream Dream
     */
    public void setDream(final String dream) {
        this.dream = dream;
    }
}
