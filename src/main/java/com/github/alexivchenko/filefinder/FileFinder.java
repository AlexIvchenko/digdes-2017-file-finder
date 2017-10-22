package com.github.alexivchenko.filefinder;

import com.github.alexivchenko.filefinder.core.*;

import java.io.File;
import java.util.Scanner;

/**
 * @author Alex Ivchenko
 */
public class FileFinder {
    private static final Scanner scanner = new Scanner(System.in);
    private static final XmlCrawler XML_CRAWLER = new RobustXmlCrawler(new LoggedXmlCrawler(new BasicXmlCrawler()));
    private static final ZipCrawler zipCrawler = new RobustZipCrawler(new LoggedZipCrawler(new BasicZipCrawler(XML_CRAWLER)));
    private static final Crawler crawler = new Crawler(XML_CRAWLER, zipCrawler);

    public static void main(final String... args) {
        System.out.print("Enter directory: ");
        String path = scanner.nextLine();
        crawler.crawl(new File(path)).forEach(System.out::println);
    }
}
