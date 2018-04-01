package by.epam.safonenko.xml.command;

import by.epam.safonenko.xml.entity.Medicament;
import by.epam.safonenko.xml.entity.Medicine;
import by.epam.safonenko.xml.entity.Supplement;
import by.epam.safonenko.xml.parser.MedicineParser;
import by.epam.safonenko.xml.parser.MedicineParserFactory;
import by.epam.safonenko.xml.servlet.ServletUtil;
import by.epam.safonenko.xml.validator.XMLValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ParserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LOCALE = "locale";
    private static final String PARSER = "parser";
    private static final String MEDICAMENT_SET = "medicamentSet";
    private static final String SUPPLEMENT_SET = "supplementSet";

    @Override
    public String execute(HttpServletRequest request) {
        String file = (String) request.getSession().getAttribute(ServletUtil.FILE);
        if (file == null){
            request.setAttribute(ServletUtil.MESSAGE, "There is no any loaded file!");
            return ServletUtil.RESPONSE_PATH;
        }

        XMLValidator validator = new XMLValidator();
        try {
            validator.validate(file);
        } catch (SAXException|IOException e) {
            LOGGER.catching(e);
        }

        String locale = request.getParameter(LOCALE);
        request.setAttribute(LOCALE, locale);

        String parser = request.getParameter(PARSER);
        MedicineParser medicineParser = new MedicineParserFactory().createMedicineParser(parser);
        medicineParser.buildMedicineSet(file);

        Set<Medicine> medicines = medicineParser.getMedicines();
        Set<Medicament> medicaments = new HashSet<>();
        Set<Supplement> supplements = new HashSet<>();

        for (Medicine medicine: medicines){
            if(medicine instanceof Medicament){
                medicaments.add((Medicament) medicine);
            }else if(medicine instanceof Supplement){
                supplements.add((Supplement)medicine);
            }
        }
        request.setAttribute(MEDICAMENT_SET, medicaments);
        request.setAttribute(SUPPLEMENT_SET, supplements);
        return ServletUtil.RESULT_PATH;
    }
}
