package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum MyDayMessages {



    GOODMORNING{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Good morning, ";
            else
                return "Dobro jutro, ";
        }
    },
    GOODAFTERNOON{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Good afternoon, ";
            else
                return "Dobar dan, ";
        }
    },
    GOODEVENING{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Good evening, ";
            else
                return "Dobra večer, ";
        }
    },
    CLOCK{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "hh:mm";
            else
                return "HH:mm";
        }
    },
    DATE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "MMM dd, YYYY";
            else
                return "dd. MMM YYYY.";
        }
    }


}
