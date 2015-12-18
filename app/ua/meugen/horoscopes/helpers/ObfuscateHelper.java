package ua.meugen.horoscopes.helpers;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;

import java.util.regex.Pattern;

/**
 * @author meugen
 */
public final class ObfuscateHelper {

    private static final Pattern START = Pattern.compile(">\\s+");
    private static final Pattern END = Pattern.compile("\\s+<");

    @Inject @Named("html.obfuscate")
    private Boolean obfuscate;

    public Result ok(final Content content) {
        String body = content.body();
        if (this.obfuscate) {
            body = START.matcher(content.body()).replaceAll(">");
            body = END.matcher(body).replaceAll("<");
        }
        return Results.ok(body).as(content.contentType());
    }
}
