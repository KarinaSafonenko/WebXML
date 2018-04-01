package by.epam.safonenko.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Medicament", namespace = "http://www.example.com/medicines", propOrder = {
        "group",
        "analogs"
})
public class Medicament extends Medicine {
    @XmlElement(namespace = "http://www.example.com/medicines", required = true)
    private String group;
    @XmlElement(namespace = "http://www.example.com/medicines")
    private Medicament.AnalogCloud analogs;

    public String getGroup() {
        return group;
    }

    public void setGroup(String value) {
        this.group = value;
    }

    public Medicament.AnalogCloud getAnalogs() {
        return analogs;
    }

    public void setAnalogs(Medicament.AnalogCloud value) {
        this.analogs = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "analogs"
    })
    public static class AnalogCloud {

        @XmlElement(name = "analog", namespace = "http://www.example.com/medicines", required = true)
        private Set<Analog> analogs;

        public AnalogCloud(){
            analogs = new HashSet<>();
        }

        public Set<Analog> getAnalogs() {
            return analogs;
        }

        public void addAnalog(Analog analog) {
            analogs.add(analog);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof AnalogCloud)) {
                return false;
            }
            AnalogCloud that = (AnalogCloud) o;
            return Objects.equals(analogs, that.analogs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(analogs);
        }

        @Override
        public String toString() {
            String result = "";
            for (Analog analog: analogs){
                result += analog + "\n";
            }
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicament)) {
            return false;
        }
        Medicament that = (Medicament) o;
        return Objects.equals(group, that.group) &&
                Objects.equals(analogs, that.analogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, analogs);
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "group='" + group + '\'' +
                ", analogs=" + analogs +
                '}';
    }
}
