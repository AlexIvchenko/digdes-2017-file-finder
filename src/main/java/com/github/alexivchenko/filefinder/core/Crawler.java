package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alex Ivchenko
 */
public class Crawler {
    private final FileCrawler fileCrawler = new FileCrawler();
    private final ZipCrawler zipCrawler = new ZipCrawler();

    public List<DetectedURL> scan(File file) {
        List<DetectedURL> detected = new LinkedList<>();
        for (File sub: asDir(file)) {
            detected.addAll(scan(sub));
        }
        if (isZip(file)) {
            detected.addAll(zipCrawler.craw(file));
        }
        if (isXml(file)) {
            detected.addAll(fileCrawler.parse(file));
        }
        return detected;
    }

    private List<File> asDir(File file) {
        if (!file.isDirectory()) {
            return Collections.emptyList();
        }
        File[] files = file.listFiles();
        if (files != null) {
            return Arrays.asList(files);
        } else {
            return Collections.emptyList();
        }
    }

    private boolean isXml(File file) {
        return file.getName().endsWith(".xml");
    }

    private boolean isZip(File file) {
        return file.getName().endsWith(".zip");
    }
}
