package by.epam.safonenko.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pack", namespace = "http://www.example.com/medicines", propOrder = {
        "type",
        "quantity",
        "price"
})
public class Pack {

    @XmlElement(namespace = "http://www.example.com/medicines", required = true, defaultValue = "\u0436\u0435\u0441\u0442\u043a\u0430\u044f")
    private String type;
    @XmlElement(namespace = "http://www.example.com/medicines", required = true)
    private String quantity;
    @XmlElement(namespace = "http://www.example.com/medicines", required = true)
    private String price;

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String value) {
        this.quantity = value;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String value) {
        this.price = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pack)) {
            return false;
        }
        Pack pack = (Pack) o;
        return Objects.equals(type, pack.type) &&
                Objects.equals(quantity, pack.quantity) &&
                Objects.equals(price, pack.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, quantity, price);
    }

    @Override
    public String toString() {
        return "{Type: " + type + "\n" +
                "Quantity: " + quantity + '\n' +
                "Price: " + price + "}" + "\n";
    }
}

