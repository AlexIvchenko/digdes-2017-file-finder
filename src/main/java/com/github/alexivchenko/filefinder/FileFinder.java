package com.github.alexivchenko.filefinder;

import com.github.alexivchenko.filefinder.core.*;

import java.io.File;
import java.util.Scanner;

/**
 * @author Alex Ivchenko
 */
public class FileFinder {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Crawler crawler = Crawler.builder()
            .enableLog()
            .robust()
            .includeXml()
            .includeZip()
            .recursive(true)
            .filter(FileFinder::isURL)
            .build();

    public static void main(final String... args) {
        System.out.print("Enter directory: ");
        String path = scanner.nextLine();
        crawler.crawl(new File(path)).forEach(System.out::println);
    }

    private static boolean isURL(String str) {
        return str.startsWith("http://") ||
                str.startsWith("https://") ||
                str.startsWith("ftp://") ||
                str.startsWith("mailto:") ||
                str.startsWith("file://");
    }
}
