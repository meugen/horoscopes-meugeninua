package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by meugen on 10.01.15.
 */
public final class TranslateHelper {

    private static final Log LOG = LogFactory.getLog(TranslateHelper.class);

    private static final String KEY = "AIzaSyCUQ-JS3EKrwyAdHU9UWl0F_heREO_PY-k";
    private static final String URL = "https://www.googleapis.com/language/translate/v2";

    private Format format = Format.TEXT;
    private boolean prettyPrint = false;
    private String source = "ru";
    private String target = "en";

    public Format getFormat() {
        return format;
    }

    public void setFormat(final Format format) {
        this.format = format;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public void setPrettyPrint(final boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(final String target) {
        this.target = target;
    }

    public List<String> translateAll(final List<String> queries) throws IOException {
        final JsonNode json = Json.parse(this.openTranslateStream(queries));
        LOG.error(json);
        final Iterator<JsonNode> translations = json.get("data")
                .get("translations").elements();

        final List<String> result = new ArrayList<>();
        while (translations.hasNext()) {
            result.add(translations.next().get("translatedText").textValue());
        }
        return result;
    }

    private InputStream openTranslateStream(final List<String> queries) throws IOException {
        // LOG.error(URL);

        final HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
        connection.addRequestProperty("X-HTTP-Method-Override", "GET");
        connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        final StringBuilder builder = new StringBuilder();
        builder.append("key=").append(KEY);
        builder.append("&format=").append(this.format.name().toLowerCase());
        builder.append("&prettyprint=").append(this.prettyPrint);
        builder.append("&source=").append(this.source);
        builder.append("&target=").append(this.target);
        for (String query : queries) {
            builder.append("&q=").append(URLEncoder.encode(query, "UTF-8"));
        }
        // LOG.error(builder);

        try (OutputStream output = connection.getOutputStream()) {
            final PrintWriter writer = new PrintWriter(output);
            writer.write(builder.toString());
            writer.close();
        }
        return connection.getInputStream();
    }

    public enum Format {
        TEXT, HTML
    }
}
