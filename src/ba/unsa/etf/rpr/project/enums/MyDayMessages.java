package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum MyDayMessages {
    NOTSELECTED{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "No tasks are selected.";
            else
                return "Nije odabran ni jedan zadatak.";
        }
    },
    DELETECONFIRMATIONHEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Are you sure you want to delete ";
            else
                return "Jeste li sigurni da želite obrisati ";
        }
    },
    DELETECONFIRMATIONCONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This task will be deleted immediately. You can't undo this action.";
            else
                return "Odabrani zadatak će se obrisati odmah. Brisanje nije moguće poništiti.";
        }
    },
    REMINDERINFORMATION{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Starts in ";
            else
                return "Počinje za ";
        }
    },
    TEXTFLOWTASKNAME{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Task name: ";
            else
                return "Ime zadatka: ";
        }
    },
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
    }


}
