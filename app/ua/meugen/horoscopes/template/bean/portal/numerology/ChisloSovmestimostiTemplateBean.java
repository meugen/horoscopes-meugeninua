package ua.meugen.horoscopes.template.bean.portal.numerology;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChisloSovmestimostiTemplateBean extends IndexTemplateBean {

    public ChisloSovmestimostiTemplateBean() {
        super("numerology-chislo_sovmestimosti");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "numerology.chislo_sovmestimosti.title");
    }
}
