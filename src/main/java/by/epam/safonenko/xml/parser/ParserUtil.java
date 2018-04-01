package by.epam.safonenko.xml.parser;

import by.epam.safonenko.xml.entity.Fabricator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class ParserUtil {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void setCertificateExpiration(Fabricator.Certificate certificate, String value){
        XMLGregorianCalendar data = null;
        try {
            data = DatatypeFactory.newInstance().newXMLGregorianCalendar(value.trim());
        } catch (DatatypeConfigurationException e) {
            LOGGER.catching(e);
        }
        certificate.setExpiration(data);
    }

    public static void setCertificateIssue(Fabricator.Certificate certificate, String value){
        XMLGregorianCalendar data = null;
        if (value != null){
            try {
                data = DatatypeFactory.newInstance().newXMLGregorianCalendar(value.trim());
            } catch (DatatypeConfigurationException e) {
                LOGGER.catching(e);
            }
            certificate.setIssue(data);
        }
    }
}
