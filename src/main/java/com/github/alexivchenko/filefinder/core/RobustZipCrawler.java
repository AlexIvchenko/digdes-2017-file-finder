package com.github.alexivchenko.filefinder.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class RobustZipCrawler implements ZipCrawler {
    private static final Logger log = LoggerFactory.getLogger(RobustZipCrawler.class);
    private final ZipCrawler delegate;

    public RobustZipCrawler(ZipCrawler delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DetectedURL> crawl(File zip) {
        try {
            return delegate.crawl(zip);
        } catch (ParseException e) {
            log.warn("cannot parse zip " + zip.getAbsolutePath());
            return Collections.emptyList();
        }
    }
}
