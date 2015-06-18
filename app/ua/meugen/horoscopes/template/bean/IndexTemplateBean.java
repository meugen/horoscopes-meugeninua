package ua.meugen.horoscopes.template.bean;

import java.util.List;

/**
 * Created by admin on 18.06.2015.
 */
public final class IndexTemplateBean extends BaseTemplateBean {

    @Override
    protected void fetchAdditionalCss(final List<String> cssList) {
        super.fetchAdditionalCss(cssList);
        this.addItemsFromProperty(cssList, "horoscopes.templates.index.css-list");
    }

    @Override
    public void fetchAdditionalJs(final List<String> jsList) {
        super.fetchAdditionalJs(jsList);
        this.addItemsFromProperty(jsList, "horoscopes.templates.index.js-list");
    }
}
