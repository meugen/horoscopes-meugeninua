package ua.meugen.horoscopes.template.bean;

import play.Configuration;
import play.i18n.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 18.06.2015.
 */
public class BaseTemplateBean {

    private List<String> cssList;
    private List<String> jsList;

    public final List<String> css() {
        if (this.cssList == null) {
            this.cssList = new ArrayList<>();
            this.addItemsFromProperty(this.cssList, "horoscopes.templates.base.css-list");
            this.fetchAdditionalCss(this.cssList);
        }
        return this.cssList;
    }

    public final List<String> js() {
        if (this.jsList == null) {
            this.jsList = new ArrayList<>();
            this.addItemsFromProperty(this.jsList, "horoscopes.templates.base.js-list");
            this.fetchAdditionalJs(this.jsList);
        }
        return this.jsList;
    }

    protected final void addItemsFromProperty(final List<String> items, final String name) {
        final List<String> newItems = Configuration.root().getStringList(name);
        items.addAll(newItems);
    }

    protected void fetchAdditionalCss(final List<String> cssList) {
        // Nothing to do
    }

    protected void fetchAdditionalJs(final List<String> jsList) {
        // Nothing to do
    }

    public String title() {
        return Messages.get("views.base.title");
    }
}
