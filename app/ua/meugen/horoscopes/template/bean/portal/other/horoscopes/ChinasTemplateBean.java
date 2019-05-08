package ua.meugen.horoscopes.template.bean.portal.other.horoscopes;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChinasTemplateBean extends IndexTemplateBean {

    public ChinasTemplateBean() {
        super("otherhoroscopes-chinas");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "other.horoscopes.chinas.title");
    }
}
