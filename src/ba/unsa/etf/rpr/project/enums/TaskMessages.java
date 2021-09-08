package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum TaskMessages {
    DATATYPE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Text data";
            else
                return "Tekstualna datoteka";
        }
    },
    CHOOSE_FILE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Choose file: ";
            else
                return "Izaberite datoteku: ";
        }
    },


}
