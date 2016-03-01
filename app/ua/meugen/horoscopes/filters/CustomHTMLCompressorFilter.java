package ua.meugen.horoscopes.filters;

import com.google.inject.Inject;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.mohiva.play.htmlcompressor.HTMLCompressorFilter;
import play.api.Configuration;

public final class CustomHTMLCompressorFilter extends HTMLCompressorFilter {

    private final Configuration configuration;

    @Inject
    public CustomHTMLCompressorFilter(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public HtmlCompressor compressor() {
        HtmlCompressor compressor = new HtmlCompressor();
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
}
