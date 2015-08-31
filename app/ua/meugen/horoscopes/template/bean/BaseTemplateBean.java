package ua.meugen.horoscopes.template.bean;

import play.Configuration;
import play.i18n.Messages;

import java.util.ArrayList;
import java.util.List;

public class BaseTemplateBean {

    private List<String> cssList;
    private List<String> jsList;

    public final List<String> css() {
        if (this.cssList == null) {
            this.cssList = new ArrayList<>();
            this.fetchCss(this.cssList);
        }
        return this.cssList;
    }

    public final List<String> js() {
        if (this.jsList == null) {
            this.jsList = new ArrayList<>();
            this.fetchJs(this.jsList);
        }
        return this.jsList;
    }

    protected final void addItemsFromProperty(final List<String> items, final String name) {
        final List<String> newItems = Configuration.root().getStringList(name);
        items.addAll(newItems);
    }

    protected void fetchCss(final List<String> cssList) {
        this.addItemsFromProperty(this.cssList, "horoscopes.templates.base.css-list");
    }

    protected void fetchJs(final List<String> jsList) {
        this.addItemsFromProperty(this.jsList, "horoscopes.templates.base.js-list");
    }

    public String title() {
        return Messages.get("views.base.title");
    }
}
