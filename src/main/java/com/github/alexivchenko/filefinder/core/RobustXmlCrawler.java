package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class RobustXmlCrawler implements XmlCrawler {
    private final XmlCrawler delegate;

    public RobustXmlCrawler(XmlCrawler delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DetectedString> crawl(File xml) {
        try {
            return delegate.crawl(xml);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DetectedString.FileStageBuilder> crawl(InputStream is) {
        try {
            return delegate.crawl(is);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }
}
