package ua.meugen.horoscopes.controllers.portal;

import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.template.bean.portal.numerology.*;
import views.html.numerology.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public final class Numerology {

    @Inject
    private ChisloDomaTemplateBean chisloDomaTemplateBean;

    @Inject
    private ChisloDruzhbyTemplateBean chisloDruzhbyTemplateBean;

    @Inject
    private ChisloImeniTemplateBean chisloImeniTemplateBean;

    @Inject
    private ChisloSerdtsaTemplateBean chisloSerdtsaTemplateBean;

    @Inject
    private ChisloSovmestimostiTemplateBean chisloSovmestimostiTemplateBean;

    @Inject
    private ChisloUdachiTemplateBean chisloUdachiTemplateBean;

    public CompletionStage<Result> chisloDoma() {
        return CompletableFuture.supplyAsync(() -> Results.ok(chislodoma.render(this.chisloDomaTemplateBean)));
    }

    public CompletionStage<Result> chisloDruzhby() {
        return CompletableFuture.supplyAsync(() -> Results.ok(chislodruzhby.render(this.chisloDruzhbyTemplateBean)));
    }

    public CompletionStage<Result> chisloImeni() {
        return CompletableFuture.supplyAsync(() -> Results.ok(chisloimeni.render(this.chisloImeniTemplateBean)));
    }

    public CompletionStage<Result> chisloSerdtsa() {
        return CompletableFuture.supplyAsync(() -> Results.ok(chisloserdtsa.render(this.chisloSerdtsaTemplateBean)));
    }

    public CompletionStage<Result> chisloSovmestimosti() {
        return CompletableFuture.supplyAsync(() -> Results.ok(chislosovmestimosti.render(this.chisloSovmestimostiTemplateBean)));
    }

    public CompletionStage<Result> chisloUdachi() {
        return CompletableFuture.supplyAsync(() -> Results.ok(chisloudachi.render(this.chisloUdachiTemplateBean)));
    }
}
