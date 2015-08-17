package ua.meugen.horoscopes.template.bean;

import java.util.List;

/**
 * Created by admin on 18.06.2015.
 */
public final class IndexTemplateBean extends BaseTemplateBean {

    @Override
    protected void fetchCss(final List<String> cssList) {
        super.fetchCss(cssList);
        this.addItemsFromProperty(cssList, "horoscopes.templates.index.css-list");
    }

    @Override
    public void fetchJs(final List<String> jsList) {
        super.fetchJs(jsList);
        this.addItemsFromProperty(jsList, "horoscopes.templates.index.js-list");
    }
}
