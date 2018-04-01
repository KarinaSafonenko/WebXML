package by.epam.safonenko.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Analog", namespace = "http://www.example.com/medicines")
public class Analog {

    @XmlAttribute(name = "substitute", required = true)
    private String substitute;

    public String getSubstitute() {
        return substitute;
    }

    public void setSubstitute(String value) {
        this.substitute = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Analog)) {
            return false;
        }
        Analog analog = (Analog) o;
        return Objects.equals(substitute, analog.substitute);
    }

    @Override
    public int hashCode() {

        return Objects.hash(substitute);
    }

    @Override
    public String toString() {
        return substitute;
    }
}
