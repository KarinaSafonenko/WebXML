package epam.safonenko.xml.parser;

import by.epam.safonenko.xml.entity.Medicine;
import by.epam.safonenko.xml.parser.MedicineDOMParser;
import by.epam.safonenko.xml.parser.MedicineSAXParser;
import by.epam.safonenko.xml.parser.MedicineStAXParser;
import by.epam.safonenko.xml.parser.UnMarshalParser;
import by.epam.safonenko.xml.validator.XMLValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class MedicineParserTest {
    private XMLValidator validator;
    private String filename;

    @BeforeClass
    public void setUP(){
        validator = new XMLValidator();
    }

    @Test(expectedExceptions = SAXException.class)
    public void incorrectInputFileTest() throws IOException, SAXException {
        filename = "in/incorrect.xml";
        validator.validate(filename);
    }

    @Test(expectedExceptions = IOException.class)
    public void noSuchInputFileTest() throws IOException, SAXException {
        filename = "in/unknown.xml";
        validator.validate(filename);
    }

    @Test
    public void MedicineDOMParserTest(){
        filename = "in/test.xml";
        String expected = "Cetrifacson";
        MedicineDOMParser domParser = new MedicineDOMParser();
        domParser.buildMedicineSet(filename);
        Set<Medicine> domMedicines = domParser.getMedicines();
        Iterator<Medicine> it = domMedicines.iterator();
        Medicine current = it.next();
        Assert.assertEquals(current.getName(), expected);
    }

    @Test
    public void MedicineDOMSAXParserTest(){
        filename = "in/test.xml";
        MedicineDOMParser domParser = new MedicineDOMParser();
        domParser.buildMedicineSet(filename);
        Set<Medicine> domMedicines = domParser.getMedicines();
        MedicineSAXParser saxParser = new MedicineSAXParser();
        saxParser.buildMedicineSet(filename);
        Set<Medicine> saxMedicines = saxParser.getMedicines();
        Assert.assertEquals(domMedicines, saxMedicines);
    }

    @Test
    public void MedicineDOMStAXParserTest(){
        filename = "in/test.xml";
        MedicineDOMParser domParser = new MedicineDOMParser();
        domParser.buildMedicineSet(filename);
        Set<Medicine> domMedicines = domParser.getMedicines();
        MedicineStAXParser stAXParser = new MedicineStAXParser();
        stAXParser.buildMedicineSet(filename);
        Set<Medicine> staxMedicines = stAXParser.getMedicines();
        Assert.assertEquals(domMedicines, staxMedicines);
    }

    @Test
    public void MedicineDOMUnMarshalParserTest(){
        filename = "in/test.xml";
        MedicineDOMParser domParser = new MedicineDOMParser();
        domParser.buildMedicineSet(filename);
        Set<Medicine> domMedicines = domParser.getMedicines();
        UnMarshalParser jaxbParser = new UnMarshalParser();
        jaxbParser.buildMedicineSet(filename);
        Set<Medicine> jaxbMedicines = jaxbParser.getMedicines();
        Assert.assertEquals(domMedicines, jaxbMedicines);
    }
}
