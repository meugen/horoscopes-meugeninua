package ua.meugen.horoscopes.template.bean.portal.numerology;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChisloDruzhbyTemplateBean extends IndexTemplateBean {

    public ChisloDruzhbyTemplateBean() {
        super("numerology-chislo_druzhby");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "numerology.chislo_druzhby.title");
    }
}
