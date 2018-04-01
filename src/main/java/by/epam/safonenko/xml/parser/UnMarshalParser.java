package by.epam.safonenko.xml.parser;

import by.epam.safonenko.xml.entity.MedicineCloud;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class UnMarshalParser extends MedicineParser {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void buildMedicineSet(String fileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MedicineCloud.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            FileReader reader = new FileReader(fileName);
            MedicineCloud medicineCloud = (MedicineCloud) unmarshaller.unmarshal(reader);
            medicines = medicineCloud.getMedicines();
        } catch (JAXBException|FileNotFoundException e) {
            LOGGER.catching(e);
        }
    }
}
