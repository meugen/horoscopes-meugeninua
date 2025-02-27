package ua.meugen.horoscopes.configs;

import com.google.inject.Inject;
import com.mohiva.play.xmlcompressor.XMLCompressorFilter;
import play.http.HttpFilters;
import play.mvc.EssentialFilter;
import ua.meugen.horoscopes.filters.CustomHTMLCompressorFilter;

import java.util.Arrays;
import java.util.List;

public final class Filters implements HttpFilters {

    private final CustomHTMLCompressorFilter htmlCompressorFilter;
    private final XMLCompressorFilter xmlCompressorFilter;

    @Inject
    public Filters(final CustomHTMLCompressorFilter htmlCompressorFilter,
                   final XMLCompressorFilter xmlCompressorFilter) {
        this.htmlCompressorFilter = htmlCompressorFilter;
        this.xmlCompressorFilter = xmlCompressorFilter;
    }

    @Override
    public List<EssentialFilter> getFilters() {
        return Arrays.asList(
                htmlCompressorFilter.asJava(),
                xmlCompressorFilter.asJava());
    }
}
