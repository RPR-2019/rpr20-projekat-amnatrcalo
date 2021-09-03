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
    }
}
