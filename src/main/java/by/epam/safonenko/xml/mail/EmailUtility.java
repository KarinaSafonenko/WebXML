package by.epam.safonenko.xml.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;


public class EmailUtility {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PROPERTY_PATH = "/property/mail.properties";
    private static final String PROPERTY_HOST = "mail.smtp.host";
    private static final String PROPERTY_PORT = "mail.smtp.port";
    private static final String PROPERTY_SSL = "mail.smtp.ssl.enable";
    private static final String PROPERTY_AUTH = "mail.smtp.auth";


    private enum EmailProperty{
        PORT, HOST, FROM, USERNAME, PASSWORD
    }

    public void sendEmail(String to, String subject, String body){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File(this.getClass().getResource(PROPERTY_PATH).toURI())));
        } catch (IOException|URISyntaxException e) {
            LOGGER.catching(e);
        }
        String port = properties.getProperty(EmailProperty.PORT.toString().toLowerCase());
        String host = properties.getProperty(EmailProperty.HOST.toString().toLowerCase());
        String from = properties.getProperty(EmailProperty.FROM.toString().toLowerCase());
        String username = properties.getProperty(EmailProperty.USERNAME.toString().toLowerCase());
        String password = properties.getProperty(EmailProperty.PASSWORD.toString().toLowerCase());

        properties = new Properties();
        properties.put(PROPERTY_HOST, host);
        properties.put(PROPERTY_PORT, port);
        properties.put(PROPERTY_SSL, true);
        properties.put(PROPERTY_AUTH, true);

        Authenticator authenticator = new Authenticator() {
            private PasswordAuthentication authentication = new PasswordAuthentication(username, password);
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return authentication;
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.catching(e);
        }
    }
}
