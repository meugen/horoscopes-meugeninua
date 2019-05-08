package ua.meugen.horoscopes.template.bean.portal.other.horoscopes;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class JapansTemplateBean extends IndexTemplateBean {

    public JapansTemplateBean() {
        super("otherhoroscopes-japans");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "other.horoscopes.japans.title");
    }
}
