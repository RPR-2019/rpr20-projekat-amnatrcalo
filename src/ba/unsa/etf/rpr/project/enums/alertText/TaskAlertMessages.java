package ba.unsa.etf.rpr.project.enums.alertText;

import java.util.Locale;

public enum TaskAlertMessages {
    TASKNAMEREQUIREDHEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Task name field is required";
            else
                return "Unos naziva zadatka je obavezno";
        }
    },
    TASKNAMEREQUIREDCONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "You didn't enter the name of Your task.";
            else
                return "Niste ukucali naziv zadatka.";
        }
    },
    NAMENOTAPPROVEDHEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This name is not approved";
            else
                return "Ovo ime nije dozvoljeno";
        }
    },
    NAMENOTAPPROVEDCONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Task with this name already exists.";
            else
                return "Zadatak sa ovim imenom već postoji.";
        }
    },
    TASKS_OVERLAPPIN_GHEADER {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This task overlaps with another task";
            else
                return "Vrijeme zadatka se poklapa sa drugim zadatkom";
        }
    },
    TASKS_OVERLAPPING_CONTENT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Are You ok with this?";
            else
                return "Je li Vam to u redu?";
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
