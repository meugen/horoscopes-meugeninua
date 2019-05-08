package ua.meugen.horoscopes.template.bean.portal.numerology;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChisloSerdtsaTemplateBean extends IndexTemplateBean {

    public ChisloSerdtsaTemplateBean() {
        super("numerology-chislo_serdtsa");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "numerology.chislo_serdtsa.title");
    }
}
