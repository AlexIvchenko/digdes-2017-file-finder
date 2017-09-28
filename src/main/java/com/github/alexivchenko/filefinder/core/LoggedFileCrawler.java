package com.github.alexivchenko.filefinder.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class LoggedFileCrawler implements FileCrawler {
    private static final Logger log = LoggerFactory.getLogger(LoggedFileCrawler.class);
    private final FileCrawler delegate;

    public LoggedFileCrawler(FileCrawler delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DetectedURL> crawl(File file) throws ParseException {
        try {
            List<DetectedURL> ret = delegate.crawl(file);
            log.info("crawling file: " + file.getAbsolutePath() + " success");
            return ret;
        } catch (Exception e) {
            log.error("crawling file: " + file.getAbsolutePath() + " failure", e);
            throw e;
        }
    }

    @Override
    public List<DetectedURL.FileStageBuilder> crawl(InputStream is) throws ParseException {
        log.info("parsing input stream: " + is);
        try {
            List<DetectedURL.FileStageBuilder> ret = delegate.crawl(is);
            log.info("parsing success");
            return ret;
        } catch (Exception e) {
            log.error("parsing failure", e);
            throw e;
        }
    }
}
