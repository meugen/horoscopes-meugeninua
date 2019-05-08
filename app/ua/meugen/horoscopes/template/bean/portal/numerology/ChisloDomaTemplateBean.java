package ua.meugen.horoscopes.template.bean.portal.numerology;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChisloDomaTemplateBean extends IndexTemplateBean {

    public ChisloDomaTemplateBean() {
        super("numerology-chislo_doma");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "numerology.chislo_doma.title");
    }
}
