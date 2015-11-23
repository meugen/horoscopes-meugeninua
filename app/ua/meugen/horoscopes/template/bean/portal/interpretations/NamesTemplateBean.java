package ua.meugen.horoscopes.template.bean.portal.interpretations;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class NamesTemplateBean extends IndexTemplateBean {

    public NamesTemplateBean() {
        super("interpretations-names");
    }

    @Override
    public String title() {
        return Messages.get("interpretations.names.title");
    }
}
