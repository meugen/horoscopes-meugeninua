package ua.meugen.horoscopes.template.bean;

import com.google.inject.Inject;
import play.i18n.Lang;
import play.i18n.MessagesApi;

public class BaseTemplateBean {

    @Inject
    protected MessagesApi messages;

    public String title() {
        return messages.get(Lang.defaultLang(), "views.base.title");
    }
}
