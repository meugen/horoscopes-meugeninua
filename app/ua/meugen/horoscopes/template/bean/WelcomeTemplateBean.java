package ua.meugen.horoscopes.template.bean;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public final class WelcomeTemplateBean extends BaseTemplateBean {

    public String message() {
        return "Welcome to Play";
    }
}
