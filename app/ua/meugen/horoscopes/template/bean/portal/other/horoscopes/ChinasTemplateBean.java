package ua.meugen.horoscopes.template.bean.portal.other.horoscopes;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class ChinasTemplateBean extends IndexTemplateBean {

    public ChinasTemplateBean() {
        super("otherhoroscopes-chinas");
    }

    @Override
    public String title() {
        return Messages.get("other.horoscopes.chinas.title");
    }
}
