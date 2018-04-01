package by.epam.safonenko.xml.entity;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fabricator", namespace = "http://www.example.com/medicines", propOrder = {
        "certificate",
        "versions"
})
public class Fabricator {
    @XmlElement(namespace = "http://www.example.com/medicines", required = true)
    private Fabricator.Certificate certificate;
    @XmlElement(namespace = "http://www.example.com/medicines", required = true)
    private Fabricator.VersionCloud versions;
    @XmlAttribute(name = "pharm", required = true)
    private String pharm;

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate value) {
        this.certificate = value;
    }

    public Fabricator.VersionCloud getVersions() {
        return versions;
    }

    public void setVersions(Fabricator.VersionCloud value) {
        this.versions = value;
    }

    public String getPharm() {
        return pharm;
    }

    public void setPharm(String value) {
        this.pharm = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "versions"
    })
    public static class VersionCloud{
        @XmlElement(name = "version", namespace = "http://www.example.com/medicines", required = true)
        private Set<Version> versions;

        public VersionCloud(){
            versions = new HashSet<>();
        }

        public Set<Version> getVersions() {
            return versions;
        }

        public void addVersion(Version version) {
            versions.add(version);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof VersionCloud)) {
                return false;
            }
            VersionCloud that = (VersionCloud) o;
            return Objects.equals(versions, that.versions);
        }

        @Override
        public int hashCode() {

            return Objects.hash(versions);
        }

        @Override
        public String toString() {
            String result = "";
            for (Version version: versions){
                result += version + "\n";
            }
            return result;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Certificate", namespace = "http://www.example.com/medicines")
    public static class Certificate {

        @XmlAttribute(name = "number", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        private String number;
        @XmlAttribute(name = "issue")
        @XmlSchemaType(name = "date")
        private XMLGregorianCalendar issue;
        @XmlAttribute(name = "expiration", required = true)
        @XmlSchemaType(name = "date")
        private XMLGregorianCalendar expiration;

        public String getNumber() {
            return number;
        }

        public void setNumber(String value) {
            this.number = value;
        }

        public XMLGregorianCalendar getIssue() {
            return issue;
        }

        public void setIssue(XMLGregorianCalendar value) {
            this.issue = value;
        }

        public XMLGregorianCalendar getExpiration() {
            return expiration;
        }

        public void setExpiration(XMLGregorianCalendar value) {
            this.expiration = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Certificate)) {
                return false;
            }
            Certificate that = (Certificate) o;
            return Objects.equals(number, that.number) &&
                    Objects.equals(issue, that.issue) &&
                    Objects.equals(expiration, that.expiration);
        }

        @Override
        public int hashCode() {

            return Objects.hash(number, issue, expiration);
        }

        @Override
        public String toString() {
            String result = "{ Number: " + number + "\n";
            if (issue != null){
                result += "Start:" + issue + "\n";
            }
            result += "End:" + expiration + "}" + "\n";
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fabricator)) {
            return false;
        }
        Fabricator that = (Fabricator) o;
        return Objects.equals(certificate, that.certificate) &&
                Objects.equals(versions, that.versions) &&
                Objects.equals(pharm, that.pharm);
    }

    @Override
    public int hashCode() {

        return Objects.hash(certificate, versions, pharm);
    }

    @Override
    public String toString() {
        return "Fabricator:" + pharm + "\n" + "{" +
                "Versions:" + versions + "\n" +
                "Certificate: " + certificate + "}";
    }

}
