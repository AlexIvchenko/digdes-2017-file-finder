package com.github.alexivchenko.filefinder.core;

import java.io.File;

/**
 * @author Alex Ivchenko
 */
public class ExtensionBasedXmlChecker implements XmlChecker {
    @Override
    public boolean isXml(File file) {
        return file.getName().endsWith(".xml");
    }
}
