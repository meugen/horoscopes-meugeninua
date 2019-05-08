package ua.meugen.horoscopes.actions.actions.application;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.io.IOUtils;
import play.Application;
import play.mvc.Result;
import play.mvc.Results;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public final class ApplicationAssetsAction {

    private final String type;
    private final String mime;

    private LoadingCache<String, String> cache;

    private Application application;

    public static ApplicationAssetsAction forJs(final Application app) {
        final ApplicationAssetsAction action = new ApplicationAssetsAction("js", "text/javascript");
        action.application = app;
        return action;
    }

    public static ApplicationAssetsAction forCss(final Application app) {
        final ApplicationAssetsAction action = new ApplicationAssetsAction("css", "text/css");
        action.application = app;
        return action;
    }

    private ApplicationAssetsAction(final String type, final String mime) {
        this.type = type;
        this.mime = mime;

       cache =  CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
           @Override
           public String load(final String key) throws Exception {
               return internalLoad(key);
           }
       });
    }

    public CompletionStage<Result> execute(final String name) {
        return CompletableFuture.supplyAsync(() -> internalExecute(name));
    }

    private Result internalExecute(final String name) {
        Result result;
        try {
            final String content = cache.get(name);
            result = content == null ? Results.notFound() : Results.ok(content).as(mime);
        } catch (Exception e) {
            result = Results.internalServerError(e.getMessage());
        }
        return result;
    }

    private String internalLoad(final String name) {
        String result = null;

        Writer writer = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(assetAsStream(type + "/conf/" + name)));
            writer = new StringWriter();
            while (true) {
                final String asset = reader.readLine();
                if (asset == null || asset.length() == 0) {
                    break;
                }
                writeAssetToWriter(asset, writer);
            }
            result = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(reader);
        }
        return result;
    }

    private InputStream assetAsStream(final String name) throws IOException {
        final InputStream stream = application.environment().resourceAsStream("public/" + name);
        if (stream == null) {
            throw new IOException(String.format("Asset '%s' not found", name));
        }
        return stream;
    }

    private void writeAssetToWriter(final String asset, final Writer writer) throws IOException {
        InputStream input = null;
        try {
            try {
                input = assetAsStream(asset + ".min." + type);
            } catch (IOException e) {
                input = assetAsStream(asset + "." + type);
            }
            writer.append('\n');
            IOUtils.copy(input, writer);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
