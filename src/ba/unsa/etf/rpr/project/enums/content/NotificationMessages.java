package ba.unsa.etf.rpr.project.enums.content;

import java.util.Locale;

public enum NotificationMessages {
    REMINDER_INFORMATION {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Starts in ";
            else
                return "Počinje za ";
        }
    },
    EMAIL_SUBJECT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Reminder: To Do App";
            else
                return "Podsjetnik: To Do App";
        }
    },
    H2_MAIL_TEXT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Upcoming task: ";
            else
                return "Nadolazeći zadatak: ";
        }
    }
}
