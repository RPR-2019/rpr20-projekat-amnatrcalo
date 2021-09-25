package ba.unsa.etf.rpr.project.enums.content;

import java.util.Locale;

public enum MyDayMessages {



    GOOD_MORNING {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Good morning, ";
            else
                return "Dobro jutro, ";
        }
    },
    GOOD_AFTERNOON {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Good afternoon, ";
            else
                return "Dobar dan, ";
        }
    },
    GOOD_EVENING {
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
