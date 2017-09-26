package com.github.alexivchenko.filefinder;

import com.github.alexivchenko.filefinder.core.Crawler;

import java.io.File;
import java.util.Scanner;

/**
 * @author Alex Ivchenko
 */
public class FileFinder {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Crawler crawler = new Crawler();

    public static void main(final String... args) {
        System.out.print("Enter directory: ");
        String path = scanner.nextLine();
        crawler.scan(new File(path)).forEach(System.out::println);
    }
}
