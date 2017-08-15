package ua.meugen.horoscopes.template.bean;

import play.i18n.Messages;

public class BaseTemplateBean {

    public String title() {
        return Messages.get("views.base.title");
    }
}
