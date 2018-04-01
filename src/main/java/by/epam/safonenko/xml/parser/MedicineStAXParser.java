package by.epam.safonenko.xml.parser;

import by.epam.safonenko.xml.entity.*;
import by.epam.safonenko.xml.type.MedicineAttribute;
import by.epam.safonenko.xml.type.MedicineElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MedicineStAXParser extends MedicineParser {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void buildMedicineSet(String fileName) {
        FileInputStream inputStream = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName().toUpperCase();
                    if (name.equals(MedicineElement.MEDICAMENT.toString())) {
                        Medicament medicament = buildMedicament(reader);
                        medicines.add(medicament);
                    }else if (name.equals(MedicineElement.SUPPLEMENT.toString())){
                        Supplement supplement = buildSupplement(reader);
                        medicines.add(supplement);
                    }
                }
            }
        } catch (XMLStreamException|FileNotFoundException e) {
            LOGGER.catching(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.catching(e);
            }
        }

    }

    private void buidMedicine(Medicine medicine, XMLStreamReader reader) throws XMLStreamException {
        String id = reader.getAttributeValue(null, MedicineAttribute.ID.toString().toLowerCase());
        medicine.setId(id);
        String name = reader.getAttributeValue(null, MedicineAttribute.NAME.toString().toLowerCase());
        medicine.setName(name);

        Medicine.Dosage dosage;
        Fabricator.Certificate certificate;
        Fabricator fabricator = new Fabricator();
        Fabricator.VersionCloud versions = new Fabricator.VersionCloud();
        Version version = new Version();
        Version.PackageCloud packs = new Version.PackageCloud();
        Pack pack = new Pack();
        Medicine.FabricatorsCloud fabricators = new Medicine.FabricatorsCloud();

        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName().toUpperCase();
                    switch (MedicineElement.valueOf(name)) {
                        case DOSAGE:
                            dosage = buildDosage(reader);
                            medicine.setDosage(dosage);
                            break;
                        case FABRICATORS:
                            fabricators = new Medicine.FabricatorsCloud();
                            break;
                        case FABRICATOR:
                            fabricator = new Fabricator();
                            fabricator.setPharm(reader.getAttributeValue(null, MedicineAttribute.PHARM.toString().toLowerCase()));
                            break;
                        case CERTIFICATE:
                            certificate = buildCertificate(reader);
                            fabricator.setCertificate(certificate);
                            break;
                        case VERSION:
                            version = new Version();
                            version.setForm(reader.getAttributeValue(null, MedicineAttribute.FORM.toString().toLowerCase()));
                            break;
                        case VERSIONS:
                            versions = new Fabricator.VersionCloud();
                            break;
                        case PACKS:
                            packs = new Version.PackageCloud();
                            break;
                        case PACK:
                            pack = new Pack();
                            break;
                        case TYPE:
                            pack.setType(getXMLText(reader));
                            break;
                        case QUANTITY:
                            pack.setQuantity(getXMLText(reader));
                            break;
                        case PRICE:
                            pack.setPrice(getXMLText(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName().toUpperCase();
                        switch (MedicineElement.valueOf(name)) {
                            case FABRICATORS:
                                medicine.setFabricators(fabricators);
                                return;
                            case FABRICATOR:
                                fabricators.addFabricator(fabricator);
                                break;
                            case VERSION:
                                versions.addVersion(version);
                                break;
                            case VERSIONS:
                                fabricator.setVersions(versions);
                                break;
                            case PACKS:
                                version.setPackages(packs);
                                break;
                            case PACK:
                                packs.addPack(pack);
                                break;
                    }
                break;
            }
        }

    }
    private Medicine.Dosage buildDosage(XMLStreamReader reader){
        Medicine.Dosage dosage = new Medicine.Dosage();
        dosage.setPortion(reader.getAttributeValue(null, MedicineAttribute.PORTION.toString().toLowerCase()));
        dosage.setPeriod(reader.getAttributeValue(null, MedicineAttribute.PERIOD.toString().toLowerCase()));
        return dosage;
    }

    private Fabricator.Certificate buildCertificate(XMLStreamReader reader){
        Fabricator.Certificate certificate = new Fabricator.Certificate();
        certificate.setNumber(reader.getAttributeValue(null, MedicineAttribute.NUMBER.toString().toLowerCase()));
        String attributeValue = reader.getAttributeValue(null, MedicineAttribute.ISSUE.toString().toLowerCase());
        ParserUtil.setCertificateIssue(certificate, attributeValue);
        attributeValue = reader.getAttributeValue(null, MedicineAttribute.EXPIRATION.toString().toLowerCase());
        ParserUtil.setCertificateExpiration(certificate, attributeValue);
        return certificate;
    }

    private Medicament buildMedicament(XMLStreamReader reader) throws XMLStreamException {
        Medicament medicament = new Medicament();
        buidMedicine(medicament, reader);
        Medicament.AnalogCloud analogs = new Medicament.AnalogCloud();
        Analog analog = new Analog();
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName().toUpperCase();
                    switch (MedicineElement.valueOf(name)) {
                        case GROUP:
                            medicament.setGroup(getXMLText(reader));
                            break;
                        case ANALOG:
                            analog = new Analog();
                            analog.setSubstitute(reader.getAttributeValue(null, MedicineAttribute.SUBSTITUTE.toString().toLowerCase()));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName().toUpperCase();
                        switch (MedicineElement.valueOf(name)) {
                            case ANALOG:
                                analogs.addAnalog(analog);
                                break;
                            case ANALOGS:
                                medicament.setAnalogs(analogs);
                                return  medicament;
                            case MEDICAMENT:
                                return medicament;
                        }
                break;
            }
        }
        return medicament;
    }

    private Supplement buildSupplement(XMLStreamReader reader) throws XMLStreamException {
        Supplement supplement = new Supplement();
        buidMedicine(supplement, reader);
        Supplement.VitaminCloud vitamins = new Supplement.VitaminCloud();
        String vitamin = "";
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName().toUpperCase();
                    switch (MedicineElement.valueOf(name)) {
                        case VITAMIN:
                            vitamin = getXMLText(reader);
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName().toUpperCase();
                    switch (MedicineElement.valueOf(name)) {
                        case VITAMIN:
                            vitamins.addVitamin(vitamin);
                            break;
                        case VITAMINS:
                            supplement.setVitamins(vitamins);
                            return  supplement;
                        case SUPPLEMENT:
                            return supplement;
                    }
                break;
            }
        }
        return supplement;
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}

