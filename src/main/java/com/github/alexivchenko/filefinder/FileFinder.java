package com.github.alexivchenko.filefinder;

import com.github.alexivchenko.filefinder.core.*;

import java.io.File;
import java.util.Scanner;

/**
 * @author Alex Ivchenko
 */
public class FileFinder {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FileCrawler fileCrawler = new LoggedFileCrawler(new RobustFileCrawler(new BasicFileCrawler()));
    private static final ZipCrawler zipCrawler = new LoggedZipCrawler(new RobustZipCrawler(new BasicZipCrawler(fileCrawler)));
    private static final Crawler crawler = new Crawler(fileCrawler, zipCrawler);

    public static void main(final String... args) {
        System.out.print("Enter directory: ");
        String path = scanner.nextLine();
        crawler.scan(new File(path)).forEach(System.out::println);
    }
}
