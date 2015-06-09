package ua.meugen.horoscopes.actions.requests;

/**
 * Created by meugen on 09.06.15.
 */
public class BaseTranslateRequest {

    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(final String lang) {
        this.lang = lang;
    }
}
