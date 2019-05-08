package ua.meugen.horoscopes.template.bean.portal.interpretations;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class NamesTemplateBean extends IndexTemplateBean {

    public NamesTemplateBean() {
        super("interpretations-names");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "interpretations.names.title");
    }
}
