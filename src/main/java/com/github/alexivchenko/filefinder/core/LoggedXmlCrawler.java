package com.github.alexivchenko.filefinder.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class LoggedXmlCrawler implements XmlCrawler {
    private static final Logger log = LoggerFactory.getLogger(LoggedXmlCrawler.class);
    private final XmlCrawler delegate;

    public LoggedXmlCrawler(XmlCrawler delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DetectedString> crawl(File xml) throws ParseException {
        try {
            List<DetectedString> ret = delegate.crawl(xml);
            log.info("crawling file: " + xml.getAbsolutePath() + " success");
            return ret;
        } catch (Exception e) {
            log.error("crawling file: " + xml.getAbsolutePath() + " failure", e);
            throw e;
        }
    }

    @Override
    public List<DetectedString.FileStageBuilder> crawl(InputStream is) throws ParseException {
        log.info("parsing input stream: " + is);
        try {
            List<DetectedString.FileStageBuilder> ret = delegate.crawl(is);
            log.info("parsing success");
            return ret;
        } catch (Exception e) {
            log.error("parsing failure", e);
            throw e;
        }
    }
}
