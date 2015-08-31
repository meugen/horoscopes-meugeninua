package ua.meugen.horoscopes.template.bean;

import java.util.List;

public class IndexTemplateBean extends BaseTemplateBean {

    private final String currentKey;

    public IndexTemplateBean() {
        this("");
    }

    protected IndexTemplateBean(final String currentKey) {
        this.currentKey = currentKey;
    }

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

    public final String currentKey() {
        return this.currentKey;
    }
}
