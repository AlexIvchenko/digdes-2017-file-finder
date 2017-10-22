package com.github.alexivchenko.filefinder.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.zip.ZipFile;

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
    public List<DetectedString> crawl(ZipFile zip) throws ParseException {
        try {
            List<DetectedString> ret = delegate.crawl(zip);
            log.info("crawling zip: " + zip.getName() + " success");
            return ret;
        } catch (Exception e) {
            log.error("crawling zip: "  + zip.getName() + " failure", e);
            throw e;
        }
    }
}
