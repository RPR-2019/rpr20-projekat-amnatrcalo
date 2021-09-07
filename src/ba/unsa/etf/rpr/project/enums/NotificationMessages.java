package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum NotificationMessages {
    REMINDERINFORMATION{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Starts in ";
            else
                return "Počinje za ";
        }
    },
    EMAILSUBJECT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Reminder: To Do App";
            else
                return "Podsjetnik: To Do App";
        }
    },
    H2MAILTEXT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Upcoming task: ";
            else
                return "Nadolazeći zadatak: ";
        }
    }
}
