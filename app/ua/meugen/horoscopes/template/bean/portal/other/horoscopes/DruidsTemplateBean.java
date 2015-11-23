package ua.meugen.horoscopes.template.bean.portal.other.horoscopes;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class DruidsTemplateBean extends IndexTemplateBean {

    public DruidsTemplateBean() {
        super("otherhoroscopes-druids");
    }

    @Override
    public String title() {
        return Messages.get("other.horoscopes.druids.title");
    }
}
