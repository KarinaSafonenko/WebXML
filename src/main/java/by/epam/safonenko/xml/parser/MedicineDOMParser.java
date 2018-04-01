package by.epam.safonenko.xml.parser;

import by.epam.safonenko.xml.entity.*;
import by.epam.safonenko.xml.type.MedicineAttribute;
import by.epam.safonenko.xml.type.MedicineElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MedicineDOMParser extends MedicineParser {
    private static final Logger LOGGER = LogManager.getLogger();
    private DocumentBuilder docBuilder;

    public MedicineDOMParser() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.catching(e);
        }
    }

    @Override
    public void buildMedicineSet(String fileName) {
        Document doc;
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();

            NodeList medicicamentList = root.getElementsByTagName(MedicineElement.MEDICAMENT.toString().toLowerCase());
            NodeList supplementList = root.getElementsByTagName(MedicineElement.SUPPLEMENT.toString().toLowerCase());

            for (int index = 0; index < medicicamentList.getLength(); index++) {
                Element medicamentElement = (Element) medicicamentList.item(index);
                Medicament medicament = buildMedicament(medicamentElement);
                medicines.add(medicament);
            }

            for (int index = 0; index < supplementList.getLength(); index++) {
                Element supplementElement = (Element) supplementList.item(index);
                Supplement supplement = buildSupplement(supplementElement);
                medicines.add(supplement);
            }
        } catch (SAXException|IOException e) {
            LOGGER.catching(e);
        }
    }

    private Medicament buildMedicament(Element medicamentElement) {
        Medicament medicament = new Medicament();
        buidMedicine(medicament, medicamentElement);

        NodeList groupList = medicamentElement.getElementsByTagName(MedicineElement.GROUP.toString().toLowerCase());
        medicament.setGroup(groupList.item(0).getTextContent());

        Medicament.AnalogCloud analogs = new Medicament.AnalogCloud();

        NodeList analogsList = medicamentElement.getElementsByTagName(MedicineElement.ANALOGS.toString().toLowerCase());
        Element analogsElement = (Element) analogsList.item(0);
        NodeList analogList = analogsElement.getElementsByTagName(MedicineElement.ANALOG.toString().toLowerCase());

        for (int index = 0; index < analogList.getLength(); index++) {
            Analog analog = new Analog();
            Element analogElement = (Element) analogList.item(index);
            analog.setSubstitute(analogElement.getAttribute(MedicineAttribute.SUBSTITUTE.toString().toLowerCase()));
            analogs.addAnalog(analog);
        }

        medicament.setAnalogs(analogs);
        return medicament;
    }

    private Supplement buildSupplement(Element supplementElement) {
        Supplement supplement = new Supplement();
        buidMedicine(supplement, supplementElement);

        Supplement.VitaminCloud vitamins = new Supplement.VitaminCloud();

        NodeList vitaminsList = supplementElement.getElementsByTagName(MedicineElement.VITAMINS.toString().toLowerCase());
        Element vitaminsElement = (Element) vitaminsList.item(0);
        NodeList vitaminList = vitaminsElement.getElementsByTagName(MedicineElement.VITAMIN.toString().toLowerCase());

        for (int index = 0; index < vitaminList.getLength(); index++) {
            vitamins.addVitamin(vitaminList.item(index).getTextContent());
        }

        supplement.setVitamins(vitamins);
        return supplement;
    }

    private void buidMedicine(Medicine medicine, Element medicineElement){
        medicine.setId(medicineElement.getAttribute(MedicineAttribute.ID.toString().toLowerCase()));
        medicine.setName(medicineElement.getAttribute(MedicineAttribute.NAME.toString().toLowerCase()));

        NodeList dosageList = medicineElement.getElementsByTagName(MedicineElement.DOSAGE.toString().toLowerCase());

        Medicine.Dosage dosage = new Medicine.Dosage();
        Element currentDosage = (Element) dosageList.item(0);
        dosage.setPortion(currentDosage.getAttribute(MedicineAttribute.PORTION.toString().toLowerCase()));
        dosage.setPeriod(currentDosage.getAttribute(MedicineAttribute.PERIOD.toString().toLowerCase()));

        medicine.setDosage(dosage);

        NodeList fabricatorsList = medicineElement.getElementsByTagName(MedicineElement.FABRICATORS.toString().toLowerCase());

        Element fabricatorsElement = (Element) fabricatorsList.item(0);

        Medicine.FabricatorsCloud fabricators = new Medicine.FabricatorsCloud();
        NodeList fabricatorList = fabricatorsElement.getElementsByTagName(MedicineElement.FABRICATOR.toString().toLowerCase());

        for (int index = 0; index < fabricatorList.getLength(); index++) {
            Fabricator fabricator = new Fabricator();

            Element fabricatorElement = (Element) fabricatorList.item(index);

            fabricator.setPharm(fabricatorElement.getAttribute(MedicineAttribute.PHARM.toString().toLowerCase()));

            Fabricator.Certificate certificate = new Fabricator.Certificate();
            NodeList certificateList = fabricatorElement.getElementsByTagName(MedicineElement.CERTIFICATE.toString().toLowerCase());
            Element currentCertificate = (Element) certificateList.item(0);
            certificate.setNumber(currentCertificate.getAttribute(MedicineAttribute.NUMBER.toString().toLowerCase()));

            String attributeValue = currentCertificate.getAttribute(MedicineAttribute.EXPIRATION.toString().toLowerCase());
            XMLGregorianCalendar data = null;
            try {
                data = DatatypeFactory.newInstance().newXMLGregorianCalendar(attributeValue.trim());
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            certificate.setExpiration(data);

            attributeValue = currentCertificate.getAttribute(MedicineAttribute.ISSUE.toString().toLowerCase());
            if (!attributeValue.isEmpty()){
                try {
                    data = DatatypeFactory.newInstance().newXMLGregorianCalendar(attributeValue.trim());
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
                certificate.setIssue(data);
            }

            fabricator.setCertificate(certificate);

            NodeList versionsList = fabricatorElement.getElementsByTagName(MedicineElement.VERSIONS.toString().toLowerCase());
            Element versionsElement = (Element) versionsList.item(0);
            NodeList versionList = versionsElement.getElementsByTagName(MedicineElement.VERSION.toString().toLowerCase());

            Fabricator.VersionCloud versions = new Fabricator.VersionCloud();

            for (int position = 0; position < versionList.getLength(); position++) {
                Version version = new Version();
                Version.PackageCloud packages = new Version.PackageCloud();
                Element versionElement = (Element) versionList.item(position);

                version.setForm(versionElement.getAttribute(MedicineAttribute.FORM.toString().toLowerCase()));

                NodeList packsList = versionElement.getElementsByTagName(MedicineElement.PACKS.toString().toLowerCase());
                Element packsElement = (Element) packsList.item(0);
                NodeList packList = packsElement.getElementsByTagName(MedicineElement.PACK.toString().toLowerCase());

                for (int number = 0; number < packList.getLength(); number++){
                    Pack pack = new Pack();
                    Element packElement = (Element) packList.item(number);

                    NodeList typeList = packElement.getElementsByTagName(MedicineElement.TYPE.toString().toLowerCase());
                    NodeList quantityList = packElement.getElementsByTagName(MedicineElement.QUANTITY.toString().toLowerCase());
                    NodeList priceList = packElement.getElementsByTagName(MedicineElement.PRICE.toString().toLowerCase());

                    pack.setType(typeList.item(0).getTextContent());
                    pack.setQuantity(quantityList.item(0).getTextContent());
                    pack.setPrice(priceList.item(0).getTextContent());

                    packages.addPack(pack);
                }

                version.setPackages(packages);
                versions.addVersion(version);
            }

            fabricator.setVersions(versions);
            fabricators.addFabricator(fabricator);
        }
        medicine.setFabricators(fabricators);
    }

}
