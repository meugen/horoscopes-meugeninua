package ua.meugen.horoscopes.filters;

import akka.stream.Materializer;
import com.google.inject.Inject;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.mohiva.play.htmlcompressor.HTMLCompressorFilter;
import play.Environment;
import play.api.Configuration;

public final class CustomHTMLCompressorFilter extends HTMLCompressorFilter {

    @Inject
    private Configuration configuration;
    @Inject
    private Environment environment;
    @Inject
    private Materializer materializer;

    @Override
    public HtmlCompressor compressor() {
        HtmlCompressor compressor = new HtmlCompressor();
        compressor.setEnabled(environment.isProd());
        compressor.setPreserveLineBreaks(false);
        compressor.setRemoveComments(true);
        compressor.setRemoveIntertagSpaces(true);
        compressor.setRemoveHttpProtocol(true);
        compressor.setRemoveHttpsProtocol(true);
        return compressor;
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

    @Override
    public Materializer mat() {
        return materializer;
    }
}
