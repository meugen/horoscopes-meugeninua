package ua.meugen.horoscopes.template.bean.portal.interpretations;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class AmuletsTemplateBean extends IndexTemplateBean {

    public AmuletsTemplateBean() {
        super("interpretations-amulets");
    }

    @Override
    public String title() {
        return Messages.get("interpretations.amulets.title");
    }
}
