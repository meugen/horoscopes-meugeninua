package ua.meugen.horoscopes.template.bean;

import javax.inject.Singleton;
import java.util.List;

@Singleton
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
