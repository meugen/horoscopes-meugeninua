package ua.meugen.horoscopes.template.bean;

import java.util.List;

/**
 * Created by meugen on 16.08.15.
 */
public final class WelcomeTemplateBean extends BaseTemplateBean {

    @Override
    protected void fetchCss(List<String> cssList) {
        // No css
    }

    @Override
    protected void fetchJs(List<String> jsList) {
        // No js
    }

    public String message() {
        return "Welcome to Play";
    }
}
