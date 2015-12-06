package ua.meugen.horoscopes.actions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class SearchInterpretationRequest {

    @JsonProperty(required = true)
    private String search;
    private String locale = "ru";

    /**
     * Getter for search.
     *
     * @return Search
     */
    public String getSearch() {
        return search;
    }

    /**
     * Setter for search.
     *
     * @param search Search
     */
    public void setSearch(final String search) {
        this.search = search;
    }

    /**
     * Getter for locale.
     *
     * @return Locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Setter for locale.
     *
     * @param locale Locale
     */
    public void setLocale(final String locale) {
        this.locale = locale;
    }
}
