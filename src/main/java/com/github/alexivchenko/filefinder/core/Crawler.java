package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipFile;

/**
 * @author Alex Ivchenko
 */
public class Crawler {
    private final FileCrawler fileCrawler;
    private final ZipCrawler zipCrawler;

    public Crawler(FileCrawler fileCrawler, ZipCrawler zipCrawler) {
        this.fileCrawler = fileCrawler;
        this.zipCrawler = zipCrawler;
    }

    public List<DetectedURL> crawl(File file) {
        List<DetectedURL> detected = new LinkedList<>();
        for (File sub: asDir(file)) {
            detected.addAll(crawl(sub));
        }
        tryToGetAsZip(file).ifPresent(zipFile -> detected.addAll(zipCrawler.crawl(zipFile)));
        if (isXml(file)) {
            detected.addAll(fileCrawler.crawl(file));
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

    private Optional<ZipFile> tryToGetAsZip(File file) {
        try {
            return Optional.of(new ZipFile(file));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
