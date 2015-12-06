package ua.meugen.horoscopes.actions.dto;

public final class SimpleDto {

    private int id;
    private String name;
    private String icon;
    private String period;

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
     * Getter for name.
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * @param name Name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for icon.
     *
     * @return Icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Setter for icon.
     *
     * @param icon Icon
     */
    public void setIcon(final String icon) {
        this.icon = icon;
    }

    /**
     * Getter for period.
     *
     * @return period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Setter for period.
     *
     * @param period Period
     */
    public void setPeriod(final String period) {
        this.period = period;
    }
}
