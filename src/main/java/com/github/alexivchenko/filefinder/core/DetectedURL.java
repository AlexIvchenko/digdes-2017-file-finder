package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipFile;

/**
 * @author Alex Ivchenko
 */
public final class DetectedURL {
    private final String url;
    private final String zip;
    private final int line;
    private final String absolutePath;

    public static URLStageBuilder builder() {
        return new Builder();
    }

    public String url() {
        return url;
    }

    public int line() {
        return line;
    }

    public Optional<String> zip() {
        return Optional.ofNullable(zip);
    }

    public String path() {
        return absolutePath;
    }

    private DetectedURL(String url, String zip, int line, String absolutePath) {
        this.url = url;
        this.zip = zip;
        this.line = line;
        this.absolutePath = absolutePath;
    }

    @Override
    public String toString() {
        if (zip != null) {
            return String.format("%s (%s) %d line: %s", absolutePath, zip, line, url);
        }
        return String.format("%s %d line: %s", absolutePath, line, url);
    }

    private static class Builder implements URLStageBuilder, LineStageBuilder, FileStageBuilder {
        private String url;
        private int line;
        private String zip;
        private File file;

        @Override
        public LineStageBuilder detected(String url) {
            Objects.requireNonNull(url, "url must be not null");
            this.url = url;
            return this;
        }

        @Override
        public FileStageBuilder inLine(int line) {
            if (line < 0) {
                throw new IllegalArgumentException("line cannot be less than zero");
            }
            this.line = line;
            return this;
        }

        @Override
        public DetectedURL inZip(ZipFile zipFile, String pathInZip) {
            Objects.requireNonNull(pathInZip, "path in zip must be not null");
            Objects.requireNonNull(zipFile, "zip file must be not null");
            this.zip = pathInZip;
            return new DetectedURL(url, zip, line, zipFile.getName());
        }

        @Override
        public DetectedURL inFile(File file) {
            this.file = file;
            try {
                String absolutePath = this.file.getCanonicalPath();
                return new DetectedURL(url, zip, line, absolutePath);
            } catch (IOException e) {
                throw new IllegalArgumentException("cannot get canonical path from file: " + file);
            }
        }
    }

    public interface URLStageBuilder {
        LineStageBuilder detected(String url);
    }

    public interface LineStageBuilder {
        FileStageBuilder inLine(int line);
    }

    public interface FileStageBuilder {
        DetectedURL inZip(ZipFile zip, String pathInZip);
        DetectedURL inFile(File file);
    }
}
