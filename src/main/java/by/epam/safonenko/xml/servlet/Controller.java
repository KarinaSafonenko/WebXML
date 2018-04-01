package by.epam.safonenko.xml.servlet;

import by.epam.safonenko.xml.command.Command;
import by.epam.safonenko.xml.command.CommandFactory;
import by.epam.safonenko.xml.exception.XMLParsingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ControllerServlet")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(COMMAND);
        Command command = null;
        try {
            command = new CommandFactory().defineCommand(action);
        } catch (XMLParsingException e) {
            LOGGER.catching(e);
        }
        String page = command.execute(req);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
