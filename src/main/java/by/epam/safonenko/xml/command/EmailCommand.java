package by.epam.safonenko.xml.command;

import by.epam.safonenko.xml.mail.EmailUtility;
import by.epam.safonenko.xml.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;

public class EmailCommand implements Command{
    private static final String TO = "to";
    private static final String SUBJECT = "subject";
    private static final String BODY = "body";

    @Override
    public String execute(HttpServletRequest request) {
        EmailUtility emailUtility = new EmailUtility();
        String to = request.getParameter(TO);
        String subject = request.getParameter(SUBJECT);
        String body = request.getParameter(BODY);
        emailUtility.sendEmail(to, subject, body);
        request.setAttribute(ServletUtil.MESSAGE, "Message was sent successfully");
        return ServletUtil.RESPONSE_PATH;
    }
}
