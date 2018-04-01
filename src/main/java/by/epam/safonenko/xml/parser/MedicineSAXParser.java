package by.epam.safonenko.xml.parser;

import by.epam.safonenko.xml.entity.*;
import by.epam.safonenko.xml.type.MedicineAttribute;
import by.epam.safonenko.xml.type.MedicineElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;


public class MedicineSAXParser extends MedicineParser {
    private MedicineSAXParser.MedicineHandler handler;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String XMLNS_CONSTANT = "XMLNS";
    private static final String XSI_CONSTANT = "XSI";

    public MedicineSAXParser() {
        handler = new MedicineSAXParser.MedicineHandler();
    }

    private class MedicineHandler extends DefaultHandler {

        private Medicine medicine;
        private String current;
        private Medicine.Dosage dosage;
        private Medicine.FabricatorsCloud fabricators;
        private Fabricator fabricator;
        private Fabricator.Certificate certificate;
        private Fabricator.VersionCloud versions;
        private Version version;
        private Version.PackageCloud packs;
        private Pack pack;
        private Analog analog;
        private Medicament.AnalogCloud analogs;
        private Supplement.VitaminCloud vitamins;


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs) {
            current = qName.toUpperCase();
            MedicineElement type = MedicineElement.valueOf(current);
            switch (type) {
                case MEDICAMENT:
                    medicine = new Medicament();
                    break;
                case SUPPLEMENT:
                    medicine = new Supplement();
                    break;
                case DOSAGE:
                    dosage = new Medicine.Dosage();
                    break;
                case FABRICATORS:
                    fabricators = new Medicine.FabricatorsCloud();
                    break;
                case FABRICATOR:
                    fabricator = new Fabricator();
                    break;
                case CERTIFICATE:
                    certificate = new Fabricator.Certificate();
                    break;
                case VERSIONS:
                    versions = new Fabricator.VersionCloud();
                    break;
                case VERSION:
                    version = new Version();
                    break;
                case PACKS:
                    packs = new Version.PackageCloud();
                    break;
                case PACK:
                    pack = new Pack();
                    break;
                case ANALOGS:
                    analogs = new Medicament.AnalogCloud();
                    break;
                case ANALOG:
                    analog = new Analog();
                    break;
                case VITAMINS:
                    vitamins = new Supplement.VitaminCloud();
                    break;
            }
            for (int index = 0; index < attrs.getLength(); index++) {
                String attribute = attrs.getLocalName(index).toUpperCase();
                String value = attrs.getValue(index).trim();
                processAttribute(attribute, value);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String value = new String(ch, start, length).trim();
            if (value.isEmpty()) {
                return;
            }
            MedicineElement type = MedicineElement.valueOf(current);
            switch (type) {
                case TYPE:
                    pack.setType(value);
                    break;
                case QUANTITY:
                    pack.setQuantity(value);
                    break;
                case PRICE:
                    pack.setPrice(value);
                    break;
                case GROUP:
                    ((Medicament) medicine).setGroup(value);
                    break;
                case VITAMIN:
                    vitamins.addVitamin(value);
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            current = qName.toUpperCase();
            MedicineElement type = MedicineElement.valueOf(current);
            switch (type) {
                case DOSAGE:
                    medicine.setDosage(dosage);
                    break;
                case MEDICAMENT:
                    medicines.add(medicine);
                    break;
                case SUPPLEMENT:
                    medicines.add(medicine);
                    break;
                case FABRICATORS:
                    medicine.setFabricators(fabricators);
                    break;
                case FABRICATOR:
                    fabricators.addFabricator(fabricator);
                    break;
                case CERTIFICATE:
                    fabricator.setCertificate(certificate);
                    break;
                case VERSIONS:
                    fabricator.setVersions(versions);
                    break;
                case VERSION:
                    versions.addVersion(version);
                    break;
                case PACKS:
                    version.setPackages(packs);
                    break;
                case PACK:
                    packs.addPack(pack);
                    break;
                case ANALOG:
                    analogs.addAnalog(analog);
                    break;
                case ANALOGS:
                    Medicament medicament = (Medicament) medicine;
                    medicament.setAnalogs(analogs);
                    break;
                case VITAMINS:
                    Supplement supplement = (Supplement) medicine;
                    supplement.setVitamins(vitamins);
                    break;
            }
        }

        private void processAttribute(String attribute, String value) {
            if (attribute.startsWith(XMLNS_CONSTANT) || attribute.startsWith(XSI_CONSTANT)){
                return;
            }
            MedicineAttribute type = MedicineAttribute.valueOf(attribute);
            switch (type) {
                case ID:
                    medicine.setId(value);
                    break;
                case NAME:
                    medicine.setName(value);
                    break;
                case PORTION:
                    dosage.setPortion(value);
                    break;
                case PERIOD:
                    dosage.setPeriod(value);
                    break;
                case PHARM:
                    fabricator.setPharm(value);
                    break;
                case NUMBER:
                    certificate.setNumber(value);
                    break;
                case SUBSTITUTE:
                    analog.setSubstitute(value);
                    break;
                case ISSUE:
                    ParserUtil.setCertificateIssue(certificate, value);
                    break;
                case EXPIRATION:
                    ParserUtil.setCertificateExpiration(certificate, value);
                    break;
                case FORM:
                    version.setForm(value);
                    break;
            }
        }
    }

    @Override
    public void buildMedicineSet(String fileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
            parser.parse(new File(fileName), handler);
        } catch (ParserConfigurationException|SAXException|IOException e) {
            LOGGER.catching(e);
        }
    }
}
