package ba.unsa.etf.rpr.project.enums;

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
    EDITTASK{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Edit task";
            else
                return "Uredi zadatak";
        }
    },
    DELETETASK{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Delete task";
            else
                return "Obriši zadatak";
        }
    },
    COLLAPSEDETAILS{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Collaps details";
            else
                return "Sakrij detalje";
        }
    },
    ADDNEWTASK{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Add new task";
            else
                return "Dodaj novi zadatak";
        }
    },
    ADDNEWLIST{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Add new list";
            else
                return "Dodaj novu listu";
        }
    },
    DELETELIST{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Delete list";
            else
                return "Obriši listu";
        }
    },
    CHOOSELIST{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Choose one of Your lists";
            else
                return "Izaberite jednu od svojih listi";
        }
    },
    SETDATEANDTIME{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Set date and time of start or end of the task. This is optional";
            else
                return "Postavite datum i vrijeme početka ili kraja zadatka. Ovo nije obavezno";
        }
    }
}
