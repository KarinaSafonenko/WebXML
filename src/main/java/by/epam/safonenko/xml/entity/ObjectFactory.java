package by.epam.safonenko.xml.entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
class ObjectFactory {

    private final static QName _Supplement_QNAME = new QName("http://www.example.com/medicines", "supplement");
    private final static QName _Medicament_QNAME = new QName("http://www.example.com/medicines", "medicament");
    private final static QName _Medicine_QNAME = new QName("http://www.example.com/medicines", "medicine");

    @XmlElementDecl(namespace = "http://www.example.com/medicines", name = "supplement", substitutionHeadNamespace = "http://www.example.com/medicines", substitutionHeadName = "medicine")
    private JAXBElement<Supplement> createSupplement(Supplement value) {
        return new JAXBElement<Supplement>(_Supplement_QNAME, Supplement.class, null, value);
    }


    @XmlElementDecl(namespace = "http://www.example.com/medicines", name = "medicament", substitutionHeadNamespace = "http://www.example.com/medicines", substitutionHeadName = "medicine")
    private JAXBElement<Medicament> createMedicament(Medicament value) {
        return new JAXBElement<Medicament>(_Medicament_QNAME, Medicament.class, null, value);
    }

    @XmlElementDecl(namespace = "http://www.example.com/medicines", name = "medicine")
    private JAXBElement<Medicine> createMedicine(Medicine value) {
        return new JAXBElement<Medicine>(_Medicine_QNAME, Medicine.class, null, value);
    }
}
