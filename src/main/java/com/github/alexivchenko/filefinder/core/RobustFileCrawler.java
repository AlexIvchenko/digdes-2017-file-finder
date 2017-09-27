package com.github.alexivchenko.filefinder.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class RobustFileCrawler implements FileCrawler {
    private static final Logger log = LoggerFactory.getLogger(RobustFileCrawler.class);
    private final FileCrawler delegate;

    public RobustFileCrawler(FileCrawler delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DetectedURL> parse(File file) {
        try {
            return delegate.parse(file);
        } catch (ParseException e) {
            log.warn("cannot parse file " + file.getAbsolutePath(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<DetectedURL.FileStageBuilder> parse(InputStream is) {
        try {
            return delegate.parse(is);
        } catch (ParseException e) {
            log.warn("cannot parse input stream", e);
            return Collections.emptyList();
        }
    }
}
