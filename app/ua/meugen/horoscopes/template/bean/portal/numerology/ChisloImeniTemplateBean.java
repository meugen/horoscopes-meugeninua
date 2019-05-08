package ua.meugen.horoscopes.template.bean.portal.numerology;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChisloImeniTemplateBean extends IndexTemplateBean {

    public ChisloImeniTemplateBean() {
        super("numerology-chislo_imeni");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "numerology.chislo_imeni.title");
    }
}
