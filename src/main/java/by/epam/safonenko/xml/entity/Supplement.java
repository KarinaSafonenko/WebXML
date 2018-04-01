package by.epam.safonenko.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Supplement", namespace = "http://www.example.com/medicines", propOrder = {
        "vitamins"
})
public class Supplement extends Medicine {
    @XmlElement(namespace = "http://www.example.com/medicines")
    private Supplement.VitaminCloud vitamins;

    public Supplement.VitaminCloud getVitamins() {
        return vitamins;
    }

    public void setVitamins(Supplement.VitaminCloud value) {
        this.vitamins = value;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "vitamins"
    })
    public static class VitaminCloud {
        @XmlElement(name = "vitamin", namespace = "http://www.example.com/medicines", required = true)
        private List<String> vitamins;

        public VitaminCloud() {
            vitamins = new ArrayList<>();
        }

        public void addVitamin(String vitamin) {
            vitamins.add(vitamin);
        }

        public List<String> getVitamins() {
            return vitamins;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof VitaminCloud)) {
                return false;
            }
            VitaminCloud that = (VitaminCloud) o;
            return Objects.equals(vitamins, that.vitamins);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vitamins);
        }

        @Override
        public String toString() {
            String result = "";
            for (String vitamin: vitamins){
                result += vitamin + "\n";
            }
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supplement)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Supplement that = (Supplement) o;
        return Objects.equals(vitamins, that.vitamins);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), vitamins);
    }
}