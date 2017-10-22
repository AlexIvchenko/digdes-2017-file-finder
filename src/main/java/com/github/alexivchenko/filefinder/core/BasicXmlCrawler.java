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
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Alex Ivchenko
 */
public class BasicXmlCrawler implements XmlCrawler {
    private final SAXParserFactory factory;
    private final SAXParser parser;
    private final URLHandler handler;

    public BasicXmlCrawler(Predicate<String> filter) {
        factory = SAXParserFactory.newInstance();
        handler = new URLHandler(filter);
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new UnsupportedOperationException("cannot create " + BasicXmlCrawler.class.getCanonicalName(), e);
        }
    }

    @Override
    public List<DetectedString> crawl(File xml) {
        try {
            return doParse(xml);
        } catch (FileNotFoundException e) {
            throw new ParseException(e);
        }
    }

    @Override
    public List<DetectedString.FileStageBuilder> crawl(InputStream is) {
        try {
            return doParse(is);
        } catch (SAXException | IOException e) {
            throw new ParseException(e);
        }
    }

    private List<DetectedString> doParse(File file) throws FileNotFoundException {
        return crawl(new FileInputStream(file)).stream()
                .map(fileStageBuilder -> fileStageBuilder.inFile(file))
                .collect(Collectors.toList());
    }

    private List<DetectedString.FileStageBuilder> doParse(InputStream is) throws IOException, SAXException {
        parser.parse(is, handler);
        return handler.urls;
    }

    private static class URLHandler extends DefaultHandler {
        private final Predicate<String> filter;
        private Locator locator;
        private List<DetectedString.FileStageBuilder> urls;

        private URLHandler(Predicate<String> filter) {
            this.filter = filter;
        }

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
            String str = new String(ch, start, length);
            if (filter.test(str)) {
                urls.add(DetectedString.builder()
                        .detected(str)
                        .inLine(locator.getLineNumber()));
            }
        }
    }
}
