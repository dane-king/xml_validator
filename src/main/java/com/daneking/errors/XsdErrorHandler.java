package com.daneking.errors;

import org.slf4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.slf4j.LoggerFactory.getLogger;

public class XsdErrorHandler implements ErrorHandler {
    private static final Logger LOGGER = getLogger(XsdErrorHandler.class);
    private final ErrorMap errorMap;

    public XsdErrorHandler(ErrorMap errorMap) {
        this.errorMap = errorMap;
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        errorMap.add(ErrorMap.ERROR_TYPE.WARNING, exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        errorMap.add(ErrorMap.ERROR_TYPE.ERROR, exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        errorMap.add(ErrorMap.ERROR_TYPE.FATAL, exception);
    }
}
