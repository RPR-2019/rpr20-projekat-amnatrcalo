package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum TaskMessages {
    DATA_TYPE {
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
    START_DATE {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Start date: ";
            else
                return "Datum početka: ";
        }
    },
    ALL_DAY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "All-day task";
            else
                return "Cjelodnevni zadatak";
        }
    },

    START_TIME {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Start time: ";
            else
                return "Vrijeme početka: ";
        }
    },
    END_DATE {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "End date: ";
            else
                return "Datum kraja: ";
        }
    },
    END_TIME {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "End time: ";
            else
                return "Vrijeme kraja: ";
        }
    },
    REMINDER_SET {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Reminder: ";
            else
                return "Podsjetnik: ";
        }
    },
    REMINDER_BEFORE {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return " before";
            else
                return " prije";
        }
    },
    EMAIL_ALERT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "email";
            else
                return "email";
        }
    },
    NOTIFICATION_ALERT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "notification";
            else
                return "notifikacija";
        }
    }


}
