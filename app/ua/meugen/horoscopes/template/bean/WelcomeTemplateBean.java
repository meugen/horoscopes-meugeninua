package ua.meugen.horoscopes.template.bean;

import javax.inject.Singleton;

@Singleton
public final class WelcomeTemplateBean extends BaseTemplateBean {

    public String message() {
        return "Welcome to Play";
    }
}
