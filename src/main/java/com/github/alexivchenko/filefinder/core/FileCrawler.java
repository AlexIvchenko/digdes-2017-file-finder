package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public interface FileCrawler {
    List<DetectedURL> crawl(File file) throws ParseException;

    List<DetectedURL.FileStageBuilder> crawl(InputStream is) throws ParseException;
}
