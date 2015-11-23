package ua.meugen.horoscopes.template.bean.portal.interpretations;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class DreamsTemplateBean extends IndexTemplateBean {

    public DreamsTemplateBean() {
        super("interpretations-dreams");
    }

    @Override
    public String title() {
        return Messages.get("interpretations.dreams.title");
    }
}
