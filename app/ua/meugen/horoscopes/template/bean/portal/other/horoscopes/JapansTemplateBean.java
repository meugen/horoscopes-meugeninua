package ua.meugen.horoscopes.template.bean.portal.other.horoscopes;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class JapansTemplateBean extends IndexTemplateBean {

    public JapansTemplateBean() {
        super("otherhoroscopes-japans");
    }

    @Override
    public String title() {
        return Messages.get("other.horoscopes.japans.title");
    }
}
