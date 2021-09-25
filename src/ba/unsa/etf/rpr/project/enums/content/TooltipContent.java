package ba.unsa.etf.rpr.project.enums.content;

import java.util.Locale;

public enum TooltipContent {
    EMAIL{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "We need your email address to send You reminder alert.";
            else
                return "Potrebna nam je Vaša email adresa ukoliko odlučite da želite dobiti podsjetnik email-om.";
        }
    },
    EDIT_TASK {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Edit task";
            else
                return "Uredi zadatak";
        }
    },
    DELETE_TASK {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Delete task";
            else
                return "Obriši zadatak";
        }
    },
    COLLAPSE_DETAILS {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Collaps details";
            else
                return "Sakrij detalje";
        }
    },
    ADD_NEW_TASK {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Add new task";
            else
                return "Dodaj novi zadatak";
        }
    },
    ADD_NEW_LIST {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Add new list";
            else
                return "Dodaj novu listu";
        }
    },
    DELETE_LIST {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Delete list";
            else
                return "Obriši listu";
        }
    },
    CHOOSE_LIST {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Choose one of Your lists";
            else
                return "Izaberite jednu od svojih listi";
        }
    },
    SET_DATE_AND_TIME {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Set date and time of start or end of the task. This is optional";
            else
                return "Postavite datum i vrijeme početka ili kraja zadatka. Ovo nije obavezno";
        }
    },
    NEXT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Next";
            else
                return "Dalje";
        }
    }
}
