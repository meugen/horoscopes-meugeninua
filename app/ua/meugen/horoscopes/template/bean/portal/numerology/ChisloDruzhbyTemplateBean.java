package ua.meugen.horoscopes.template.bean.portal.numerology;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChisloDruzhbyTemplateBean extends IndexTemplateBean {

    public ChisloDruzhbyTemplateBean() {
        super("numerology-chislo_druzhby");
    }

    @Override
    public String title() {
        return Messages.get("numerology.chislo_druzhby.title");
    }
}
