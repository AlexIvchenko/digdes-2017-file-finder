package com.github.alexivchenko.filefinder.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Alex Ivchenko
 */
public class BasicZipCrawler implements ZipCrawler {
    private final FileCrawler fileCrawler;

    public BasicZipCrawler(FileCrawler fileCrawler) {
        this.fileCrawler = fileCrawler;
    }

    @Override
    public List<DetectedURL> crawl(File zip) throws ParseException {
        try {
            return doCrawl(zip);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }

    private List<DetectedURL> doCrawl(File zip) throws IOException {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
        ZipEntry entry = zis.getNextEntry();
        List<DetectedURL> urls = new ArrayList<>();
        while (entry != null) {
            if (!entry.isDirectory()) {
                String filename = entry.getName();
                if (filename.endsWith(".xml")) {
                    urls.addAll(fileCrawler.parse(new FakeClosedInputStream(zis))
                            .stream()
                            .map(fileStageBuilder -> fileStageBuilder.inZip(zip, filename))
                            .collect(Collectors.toList()));
                }
            }
            entry = zis.getNextEntry();
        }
        return urls;
    }

    private static class FakeClosedInputStream extends InputStream {
        private final InputStream delegate;

        private FakeClosedInputStream(InputStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public int read() throws IOException {
            return delegate.read();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return delegate.read(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return delegate.read(b, off, len);
        }

        @Override
        public long skip(long n) throws IOException {
            return delegate.skip(n);
        }

        @Override
        public int available() throws IOException {
            return delegate.available();
        }

        @Override
        public synchronized void mark(int readlimit) {
            delegate.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            delegate.reset();
        }

        @Override
        public boolean markSupported() {
            return delegate.markSupported();
        }

        @Override
        public void close() throws IOException {

        }
    }
}
