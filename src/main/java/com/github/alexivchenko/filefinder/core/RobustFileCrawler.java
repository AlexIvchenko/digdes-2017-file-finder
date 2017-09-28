package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class RobustFileCrawler implements FileCrawler {
    private final FileCrawler delegate;

    public RobustFileCrawler(FileCrawler delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<DetectedURL> crawl(File file) {
        try {
            return delegate.crawl(file);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DetectedURL.FileStageBuilder> crawl(InputStream is) {
        try {
            return delegate.crawl(is);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }
}
