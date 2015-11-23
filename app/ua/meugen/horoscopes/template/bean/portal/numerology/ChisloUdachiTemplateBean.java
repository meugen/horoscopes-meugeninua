package ua.meugen.horoscopes.template.bean.portal.numerology;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChisloUdachiTemplateBean extends IndexTemplateBean {

    public ChisloUdachiTemplateBean() {
        super("numerology-chislo_udachi");
    }

    @Override
    public String title() {
        return Messages.get("numerology.chislo_udachi.title");
    }
}
