package com.github.alexivchenko.filefinder;

import com.github.alexivchenko.filefinder.core.*;

import java.util.function.Predicate;

/**
 * @author Alex Ivchenko
 */
public class CrawlerBuilder {
    private boolean enableLog = false;
    private boolean robust = true;
    private boolean recursive = true;
    private boolean includeZip = true;
    private boolean includeXml = true;
    private Predicate<String> filter = str -> false;

    public CrawlerBuilder enableLog() {
        this.enableLog = true;
        return this;
    }

    public CrawlerBuilder disableLog() {
        this.enableLog = false;
        return this;
    }

    public CrawlerBuilder robust() {
        this.robust = true;
        return this;
    }

    public CrawlerBuilder weak() {
        this.robust = false;
        return this;
    }

    public CrawlerBuilder filter(Predicate<String> filter) {
        this.filter = filter;
        return this;
    }

    public CrawlerBuilder recursive(boolean recursive) {
        this.recursive = recursive;
        return this;
    }

    public CrawlerBuilder includeZip() {
        this.includeZip = true;
        return this;
    }

    public CrawlerBuilder includeXml() {
        this.includeXml = true;
        return this;
    }

    public CrawlerBuilder excludeZip() {
        this.includeZip = false;
        return this;
    }

    public CrawlerBuilder excludeXml() {
        this.includeXml = false;
        return this;
    }

    public Crawler build() {
        XmlCrawler xmlCrawler = new BasicXmlCrawler(filter);
        if (enableLog) {
            xmlCrawler = new LoggedXmlCrawler(xmlCrawler);
        }
        if (robust) {
            xmlCrawler = new RobustXmlCrawler(xmlCrawler);
        }
        ZipCrawler zipCrawler = new BasicZipCrawler(xmlCrawler);
        if (enableLog) {
            zipCrawler = new LoggedZipCrawler(zipCrawler);
        }
        if (robust) {
            zipCrawler = new RobustZipCrawler(zipCrawler);
        }
        return new Crawler(xmlCrawler, new ExtensionBasedXmlChecker(), zipCrawler,
                includeXml, includeZip, recursive);
    }
}
