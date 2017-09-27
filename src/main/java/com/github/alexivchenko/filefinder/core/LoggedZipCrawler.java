package com.github.alexivchenko.filefinder.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class LoggedZipCrawler implements ZipCrawler {
    private static final Logger log = LoggerFactory.getLogger(LoggedZipCrawler.class);
    private final ZipCrawler delegate;

    public LoggedZipCrawler(ZipCrawler delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DetectedURL> crawl(File zip) throws ParseException {
        log.info("crawl zip: " + zip.getAbsolutePath());
        try {
            List<DetectedURL> ret = delegate.crawl(zip);
            log.info("crawling " + zip.getAbsolutePath() + " success");
            return ret;
        } catch (Exception e) {
            log.error("crawling failure", e);
            throw e;
        }
    }
}
