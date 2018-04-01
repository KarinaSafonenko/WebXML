package by.epam.safonenko.xml.parser;

import by.epam.safonenko.xml.entity.Medicine;

import java.util.HashSet;
import java.util.Set;

public abstract class MedicineParser {
        protected Set<Medicine> medicines;

        public  MedicineParser(){
            medicines = new HashSet<>();
        }

        public Set<Medicine> getMedicines() {
            return medicines;
        }
        abstract public void buildMedicineSet(String fileName);
}
