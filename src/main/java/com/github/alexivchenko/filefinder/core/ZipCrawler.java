package com.github.alexivchenko.filefinder.core;

import java.util.List;
import java.util.zip.ZipFile;

/**
 * @author Alex Ivchenko
 */
public interface ZipCrawler {
    List<DetectedURL> crawl(ZipFile zip) throws ParseException;
}
