package ua.meugen.horoscopes.template.bean;

import java.util.List;

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
