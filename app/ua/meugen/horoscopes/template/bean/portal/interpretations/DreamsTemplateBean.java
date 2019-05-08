package ua.meugen.horoscopes.template.bean.portal.interpretations;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class DreamsTemplateBean extends IndexTemplateBean {

    public DreamsTemplateBean() {
        super("interpretations-dreams");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "interpretations.dreams.title");
    }
}
