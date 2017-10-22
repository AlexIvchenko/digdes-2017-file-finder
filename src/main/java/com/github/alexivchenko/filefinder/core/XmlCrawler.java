package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public interface XmlCrawler {
    List<DetectedString> crawl(File xml) throws ParseException;

    List<DetectedString.FileStageBuilder> crawl(InputStream is) throws ParseException;
}
