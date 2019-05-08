package ua.meugen.horoscopes.template.bean.portal.other.horoscopes;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class FlowersTemplateBean extends IndexTemplateBean {

    public FlowersTemplateBean() {
        super("otherhoroscopes-flowers");
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "other.horoscopes.flowers.title");
    }
}
