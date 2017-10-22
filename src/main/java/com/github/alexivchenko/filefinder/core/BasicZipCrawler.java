package com.github.alexivchenko.filefinder.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Alex Ivchenko
 */
public class BasicZipCrawler implements ZipCrawler {
    private final XmlCrawler xmlCrawler;

    public BasicZipCrawler(XmlCrawler xmlCrawler) {
        this.xmlCrawler = xmlCrawler;
    }

    @Override
    public List<DetectedString> crawl(ZipFile zip) throws ParseException {
        try {
            return doCrawl(zip);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }

    private List<DetectedString> doCrawl(ZipFile zip) throws IOException {
        List<DetectedString> urls = new ArrayList<>();
        for (ZipEntry entry : zip.stream().collect(Collectors.toList())) {
            urls.addAll(crawlZipEntry(zip, entry));
        }
        return urls;
    }

    private List<DetectedString> crawlZipEntry(ZipFile zipFile, ZipEntry zipEntry) throws IOException {
        if (!zipEntry.isDirectory()) {
            InputStream is = zipFile.getInputStream(zipEntry);
            String filename = zipEntry.getName();
            // TODO invent something better
            if (filename.endsWith(".xml")) {
                return (xmlCrawler.crawl(is)
                        .stream()
                        .map(fileStageBuilder -> fileStageBuilder.inZip(zipFile, filename))
                        .collect(Collectors.toList()));
            }
        }
        return Collections.emptyList();
    }
}
