package ua.meugen.horoscopes.template.bean;

import play.Configuration;
import play.i18n.Messages;

import java.util.ArrayList;
import java.util.List;

public class BaseTemplateBean {

    public String title() {
        return Messages.get("views.base.title");
    }
}
