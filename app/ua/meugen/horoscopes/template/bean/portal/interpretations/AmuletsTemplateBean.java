package ua.meugen.horoscopes.template.bean.portal.interpretations;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class AmuletsTemplateBean extends IndexTemplateBean {

    public AmuletsTemplateBean() {
        super("interpretations-amulets");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "interpretations.amulets.title");
    }
}
