package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public interface ZipCrawler {
    List<DetectedURL> crawl(File zip) throws ParseException;
}
