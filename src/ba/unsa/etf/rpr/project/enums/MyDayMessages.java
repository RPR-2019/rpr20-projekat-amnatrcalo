package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum MyDayMessages {


    TEXTFLOWSTARTDATE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Start date: ";
            else
                return "Datum početka: ";
        }
    },
    TEXTFLOWALLDAYTASK{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "All-day task";
            else
                return "Cjelodnevni zadatak";
        }
    },

    TEXTFLOWSTARTTIME{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Start time: ";
            else
                return "Vrijeme početka: ";
        }
    },
    TEXTFLOWENDDATE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "End date: ";
            else
                return "Datum kraja: ";
        }
    },
    TEXTFLOWENDTIME{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "End time: ";
            else
                return "Vrijeme kraja: ";
        }
    },
    TEXTFLOWREMINDERISSET{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Reminder: ";
            else
                return "Podsjetnik: ";
        }
    },
    TEXTFLOWREMINDERBEFORE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return " before";
            else
                return " prije";
        }
    },
    TEXTFLOWEMAILALERT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "email";
            else
                return "email";
        }
    },
    TEXTFLOWNOTIFICATIONALERT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "notification";
            else
                return "notifikacija";
        }
    },
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
