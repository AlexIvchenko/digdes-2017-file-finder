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
    public List<DetectedURL> parse(File file) {
        try {
            return delegate.parse(file);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<DetectedURL.FileStageBuilder> parse(InputStream is) {
        try {
            return delegate.parse(is);
        } catch (ParseException e) {
            return Collections.emptyList();
        }
    }
}
