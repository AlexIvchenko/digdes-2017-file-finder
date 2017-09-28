package com.github.alexivchenko.filefinder.core;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alex Ivchenko
 */
public class BasicFileCrawler implements FileCrawler {
    private final SAXParserFactory factory;
    private final SAXParser parser;
    private final URLHandler handler;

    public BasicFileCrawler() {
        factory = SAXParserFactory.newInstance();
        handler = new URLHandler();
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new UnsupportedOperationException("cannot create " + BasicFileCrawler.class.getCanonicalName(), e);
        }
    }

    @Override
    public List<DetectedURL> crawl(File file) {
        try {
            return doParse(file);
        } catch (FileNotFoundException e) {
            throw new ParseException(e);
        }
    }

    @Override
    public List<DetectedURL.FileStageBuilder> crawl(InputStream is) {
        try {
            return doParse(is);
        } catch (SAXException | IOException e) {
            throw new ParseException(e);
        }
    }

    private List<DetectedURL> doParse(File file) throws FileNotFoundException {
        return crawl(new FileInputStream(file)).stream()
                .map(fileStageBuilder -> fileStageBuilder.inFile(file))
                .collect(Collectors.toList());
    }

    private List<DetectedURL.FileStageBuilder> doParse(InputStream is) throws IOException, SAXException {
        parser.parse(is, handler);
        return handler.urls;
    }

    private static class URLHandler extends DefaultHandler {
        private Locator locator;
        private List<DetectedURL.FileStageBuilder> urls;

        @Override
        public void startDocument() throws SAXException {
            urls = new ArrayList<>();
        }

        @Override
        public void setDocumentLocator(Locator locator) {
            super.setDocumentLocator(locator);
            this.locator = locator;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String url = new String(ch, start, length);
            if (isURL(url)) {
                urls.add(DetectedURL.builder().detected(url).inLine(locator.getLineNumber()));
            }
        }

        private boolean isURL(String str) {
            return str.startsWith("http://") ||
                    str.startsWith("https://") ||
                    str.startsWith("ftp://") ||
                    str.startsWith("mailto:") ||
                    str.startsWith("file://");
        }
    }
}
