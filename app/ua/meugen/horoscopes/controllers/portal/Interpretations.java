package ua.meugen.horoscopes.controllers.portal;

import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.template.bean.portal.interpretations.AmuletsTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.interpretations.DreamsTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.interpretations.NamesTemplateBean;
import views.html.interpretations.amulets;
import views.html.interpretations.dreams;
import views.html.interpretations.names;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public final class Interpretations {

    @Inject
    private NamesTemplateBean namesTemplateBean;

    @Inject
    private DreamsTemplateBean dreamsTemplateBean;

    @Inject
    private AmuletsTemplateBean amuletsTemplateBean;

    public CompletionStage<Result> names() {
        return CompletableFuture.supplyAsync(() -> Results.ok(names.render(this.namesTemplateBean)));
    }

    public CompletionStage<Result> dreams() {
        return CompletableFuture.supplyAsync(() -> Results.ok(dreams.render(this.dreamsTemplateBean)));
    }

    public CompletionStage<Result> amulets() {
        return CompletableFuture.supplyAsync(() -> Results.ok(amulets.render(this.amuletsTemplateBean)));
    }
}
