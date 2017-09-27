package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class RobustZipCrawler implements ZipCrawler {
    private final ZipCrawler delegate;

    public RobustZipCrawler(ZipCrawler delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DetectedURL> crawl(File zip) {
        try {
            return delegate.crawl(zip);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }
}
