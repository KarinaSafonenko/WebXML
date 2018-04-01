package by.epam.safonenko.xml.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class XMLValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SCHEMA_DIR = "/in/medicines.xsd";

    public void validate(String fileName) throws SAXException, IOException {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = null;
        try {
            schemaLocation = new File(this.getClass().getResource(SCHEMA_DIR).toURI());
        } catch (URISyntaxException e) {
            LOGGER.catching(e);
        }
        Schema schema = factory.newSchema(schemaLocation);
        Validator validator = schema.newValidator();
        Source source = new StreamSource(fileName);
        validator.validate(source);
    }
}
