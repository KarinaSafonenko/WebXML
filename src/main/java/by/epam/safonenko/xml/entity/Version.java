package by.epam.safonenko.xml.entity;

import javax.xml.bind.annotation.*;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Version", namespace = "http://www.example.com/medicines", propOrder = {
        "packages"
})
public class Version {
    @XmlElement(name = "packs", namespace = "http://www.example.com/medicines", required = true)
    private Version.PackageCloud packages;
    @XmlAttribute(name = "form", required = true)
    private String form;

    public Version.PackageCloud getPackages() {
        return packages;
    }

    public void setPackages(Version.PackageCloud value) {
        this.packages = value;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String value) {
        this.form = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "packs"
    })

    public static class PackageCloud {
        @XmlElement(name = "pack", namespace = "http://www.example.com/medicines", required = true)
        private Set<Pack> packs;

        public PackageCloud(){
            packs = new HashSet<>();
        }

        public Set<Pack> getPacks() {
            return packs;
        }

        public void addPack(Pack pack) {
            packs.add(pack);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof PackageCloud)) {
                return false;
            }
            PackageCloud that = (PackageCloud) o;
            return Objects.equals(packs, that.packs);
        }

        @Override
        public int hashCode() {

            return Objects.hash(packs);
        }

        @Override
        public String toString() {
            String result = "";
            for (Pack pack: packs){
                result += pack + "\n";
            }
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Version)) {
            return false;
        }
        Version version = (Version) o;
        return Objects.equals(packages, version.packages) &&
                Objects.equals(form, version.form);
    }

    @Override
    public int hashCode() {

        return Objects.hash(packages, form);
    }

    @Override
    public String toString() {
        return "{Form: " + form + "{" +
                "Packages: " + packages + "} }" + "\n";
    }
}
