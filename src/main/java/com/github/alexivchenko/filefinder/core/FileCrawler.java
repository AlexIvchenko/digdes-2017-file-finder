package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public interface FileCrawler {
    List<DetectedURL> parse(File file) throws ParseException;

    List<DetectedURL.FileStageBuilder> parse(InputStream is) throws ParseException;
}
