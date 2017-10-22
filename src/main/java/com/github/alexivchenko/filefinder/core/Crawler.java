package com.github.alexivchenko.filefinder.core;

import com.github.alexivchenko.filefinder.CrawlerBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipFile;

/**
 * @author Alex Ivchenko
 */
public class Crawler {
    private final XmlCrawler xmlCrawler;
    private final XmlChecker xmlChecker;
    private final ZipCrawler zipCrawler;
    private final boolean includeXml;
    private final boolean includeZip;
    private final boolean recursive;

    public Crawler(XmlCrawler xmlCrawler, XmlChecker xmlChecker, ZipCrawler zipCrawler, boolean includeXml, boolean includeZip, boolean recursive) {
        this.xmlCrawler = xmlCrawler;
        this.xmlChecker = xmlChecker;
        this.zipCrawler = zipCrawler;
        this.includeXml = includeXml;
        this.includeZip = includeZip;
        this.recursive = recursive;
    }

    public static CrawlerBuilder builder() {
        return new CrawlerBuilder();
    }

    public List<DetectedString> crawl(File file) {
        List<DetectedString> detected = new LinkedList<>();
        if (recursive) {
            for (File sub : asDir(file)) {
                detected.addAll(crawl(sub));
            }
        }
        if (includeZip) {
            tryToGetAsZip(file).ifPresent(zipFile -> detected.addAll(zipCrawler.crawl(zipFile)));
        }
        if (includeXml && isXml(file)) {
            detected.addAll(xmlCrawler.crawl(file));
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
        return xmlChecker.isXml(file);
    }

    private Optional<ZipFile> tryToGetAsZip(File file) {
        try {
            return Optional.of(new ZipFile(file));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
