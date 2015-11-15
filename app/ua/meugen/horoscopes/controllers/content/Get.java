package ua.meugen.horoscopes.controllers.content;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.get.*;

import javax.inject.Inject;
import javax.inject.Named;

public final class Get {

    @Inject
    private GetHoroscopeAction getHoroscopeAction;

    @Inject
    private GetHoroscopesForAction getHoroscopesForAction;

    @Inject
    private GetKeysAction getKeysAction;

    @Inject
    private GetKeysForAction getKeysForAction;

    @Inject
    private AmuletGetByIdAction getAmuletAction;

    @Inject
    @Named("getDreamAction")
    private SimpleGetByIdAction getDreamAction;

    @Inject
    @Named("getNameAction")
    private SimpleGetByIdAction getNameAction;

    @Inject
    @Named("getDruidAction")
    private SimpleGetByIdAction getDruidAction;

    @Inject
    @Named("getChinaAction")
    private SimpleGetByIdAction getChinaAction;

    @Inject
    @Named("getFlowerAction")
    private SimpleGetByIdAction getFlowerAction;

    @Inject
    @Named("getJapanAction")
    private SimpleGetByIdAction getJapanAction;

    public F.Promise<Result> amuletById(final Integer id) {
        return this.getAmuletAction.execute(id);
    }

    public F.Promise<Result> chinaById(final Integer id) {
        return this.getChinaAction.execute(id);
    }

    public F.Promise<Result> dreamById(final Integer id) {
        return this.getDreamAction.execute(id);
    }

    public F.Promise<Result> druidById(final Integer id) {
        return this.getDruidAction.execute(id);
    }

    public F.Promise<Result> flowerById(final Integer id) {
        return this.getFlowerAction.execute(id);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> horoscope() {
        return this.getHoroscopeAction.execute(Controller.request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> horoscopesFor() {
        return this.getHoroscopesForAction.execute(Controller.request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> keys() {
        return this.getKeysAction.execute(Controller.request().body().asJson());
    }

    public F.Promise<Result> keysFor() {
        return this.getKeysForAction.execute();
    }

    public F.Promise<Result> japanById(final Integer id) {
        return this.getJapanAction.execute(id);
    }

    public F.Promise<Result> nameById(final Integer id) {
        return this.getNameAction.execute(id);
    }
}
