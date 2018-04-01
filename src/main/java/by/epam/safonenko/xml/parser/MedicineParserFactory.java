package by.epam.safonenko.xml.parser;

public class MedicineParserFactory {
    private enum TypeParser {
        SAX, STAX, DOM, JAXB
    }
    public MedicineParser createMedicineParser(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new MedicineDOMParser();
            case STAX:
                return new MedicineStAXParser();
            case SAX:
                return new MedicineSAXParser();
            case JAXB:
                return new UnMarshalParser();
            default:
                throw new EnumConstantNotPresentException (type.getDeclaringClass(), type.name());
        }
    }
}
