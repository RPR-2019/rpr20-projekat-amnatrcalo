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
    TEXTLENGTHERRORHEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Selected file contains more than 100 characters";
            else
                return "Odabrani fajl sadrži više od 100 karaktera";
        }
    },
    TEXTLENGTHERRORCONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Max number of characters in this text area is 100.";
            else
                return "Max broj karaktera u ovom teksutalnom polju je 100.";
        }
    }

}
