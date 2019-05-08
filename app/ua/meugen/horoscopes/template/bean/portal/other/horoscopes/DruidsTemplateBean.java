package ua.meugen.horoscopes.template.bean.portal.other.horoscopes;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class DruidsTemplateBean extends IndexTemplateBean {

    public DruidsTemplateBean() {
        super("otherhoroscopes-druids");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "other.horoscopes.druids.title");
    }
}
