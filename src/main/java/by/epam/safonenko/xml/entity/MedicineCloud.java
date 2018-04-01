package by.epam.safonenko.xml.entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "medicines"
})
@XmlRootElement(name = "medicines", namespace = "http://www.example.com/medicines")
public class MedicineCloud {

    @XmlElementRef(name = "medicine", namespace = "http://www.example.com/medicines", type = JAXBElement.class)
    private List<JAXBElement<? extends Medicine>> medicines;

    public void setMedicines(List<JAXBElement<? extends Medicine>> medicines) {
        this.medicines = medicines;
    }

    public Set<Medicine> getMedicines() {
        Set<Medicine> result = new HashSet<>();
        for (JAXBElement<? extends Medicine> medicine : medicines) {
            result.add(medicine.getValue());
        }
        return result;
    }
}
