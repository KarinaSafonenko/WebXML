package by.epam.safonenko.xml.entity;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Medicine", namespace = "http://www.example.com/medicines", propOrder = {
        "dosage",
        "fabricators"
})
@XmlSeeAlso({
        Supplement.class,
        Medicament.class
})
public abstract class Medicine {

    @XmlElement(namespace = "http://www.example.com/medicines", required = true)
    private Medicine.Dosage dosage;
    @XmlElement(namespace = "http://www.example.com/medicines", required = true)
    private Medicine.FabricatorsCloud fabricators;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    private String id;
    @XmlAttribute(name = "name", required = true)
    private String name;

    public Dosage getDosage() {
        return dosage;
    }


    public void setDosage(Dosage value) {
        this.dosage = value;
    }


    public Medicine.FabricatorsCloud getFabricators() {
        return fabricators;
    }


    public void setFabricators(Medicine.FabricatorsCloud value) {
        this.fabricators = value;
    }


    public String getId() {
        return id;
    }


    public void setId(String value) {
        this.id = value;
    }


    public String getName() {
        return name;
    }


    public void setName(String value) {
        this.name = value;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Dosage", namespace = "http://www.example.com/medicines")
    public static class Dosage {

        @XmlAttribute(name = "portion", required = true)
        private String portion;
        @XmlAttribute(name = "period", required = true)
        private String period;

        public String getPortion() {
            return portion;
        }

        public void setPortion(String value) {
            this.portion = value;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String value) {
            this.period = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Dosage)) {
                return false;
            }
            Dosage dosage = (Dosage) o;
            return Objects.equals(portion, dosage.portion) &&
                    Objects.equals(period, dosage.period);
        }

        @Override
        public int hashCode() {

            return Objects.hash(portion, period);
        }

        @Override
        public String toString() {
            return "Potion = " + portion + '\n' +
                    "Period = " + period;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "fabricators"
    })

    public static class FabricatorsCloud{
        @XmlElement(name = "fabricator", namespace = "http://www.example.com/medicines", required = true)
        protected List<Fabricator> fabricators;

        public FabricatorsCloud(){
            fabricators = new ArrayList<>();
        }

        public List<Fabricator> getFabricators() {
            return fabricators;
        }

        public void addFabricator(Fabricator fabricator) {
            fabricators.add(fabricator);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FabricatorsCloud)) {
                return false;
            }
            FabricatorsCloud that = (FabricatorsCloud) o;
            return Objects.equals(fabricators, that.fabricators);
        }

        @Override
        public int hashCode() {

            return Objects.hash(fabricators);
        }

        @Override
        public String toString() {
            String result = "";
            for (Fabricator fabricator: fabricators){
                result += fabricator + "\n";
            }
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicine)) {
            return false;
        }
        Medicine medicine = (Medicine) o;
        return Objects.equals(dosage, medicine.dosage) &&
                Objects.equals(fabricators, medicine.fabricators) &&
                Objects.equals(id, medicine.id) &&
                Objects.equals(name, medicine.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dosage, fabricators, id, name);
    }
}
