package com.daneking;

import com.daneking.errors.ErrorMap;
import com.daneking.errors.XsdErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Consumer;


public class ParseXML {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ParseXML.class);

    public static void main(String[] args) {
        if (args.length < 2) {
            LOGGER.error("Run program with java -jar ParseXML.jar <xsd absolute path>, <source xml absolute path>");
        }
        try {
            LOGGER.info(MessageFormat.format("Args: {0} {1}", args[0], args[1]));
            File xsdPath = new File(args[0]);
            File sourcePath = new File(args[1]);
            LOGGER.info("Starting to validate XML against XSD Schema at path ");
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdPath);
            Validator validator = schema.newValidator();
            ErrorMap errorMap = new ErrorMap();
            validator.setErrorHandler(new XsdErrorHandler(errorMap));
            validator.validate(new StreamSource(new FileReader(sourcePath)));
            //validator.validate(new StreamSource(new StringReader(xml)));
            if (errorMap.getParseExceptions().isEmpty()) {
                LOGGER.info("Validation is successful");
            } else errorMap.getParseExceptions()
                    .forEach(printException());

        } catch (IOException e) {
            LOGGER.error("Error reading files, check paths");
        } catch (SAXException e) {
            LOGGER.error("Custom Error Handler while Validating XML against XSD");
        }
    }

    private static Consumer<ErrorMap.Pair<ErrorMap.ERROR_TYPE, SAXParseException>> printException() {
        return m -> LOGGER.info(getErrorMessage(m.getLeft().toString(), m.getRight()));
    }

    private static String getErrorMessage(String errorType, SAXParseException exception) {
        String exceptionMessage = "at line/col: " + exception.getLineNumber() + "/" + exception.getColumnNumber() + System.lineSeparator() +
                exception.getMessage();
        return MessageFormat.format("{0} {1}", errorType, exceptionMessage);
    }
}

