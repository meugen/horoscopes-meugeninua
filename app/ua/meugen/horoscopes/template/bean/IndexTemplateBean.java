package ua.meugen.horoscopes.template.bean;

public class IndexTemplateBean extends BaseTemplateBean {

    private final String currentKey;
    private final String currentGroup;

    public IndexTemplateBean() {
        this("", "");
    }

    protected IndexTemplateBean(final String currentKey) {
        this(currentKey, currentKey.split("-")[0]);
    }

    protected IndexTemplateBean(final String currentKey, final String currentGroup) {
        this.currentKey = currentKey;
        this.currentGroup = currentGroup;
    }

    public final String currentKey() {
        return this.currentKey;
    }

    public final String currentGroup() {
        return this.currentGroup;
    }
}
