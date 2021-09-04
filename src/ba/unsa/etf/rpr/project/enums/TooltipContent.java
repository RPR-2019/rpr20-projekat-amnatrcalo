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
    }
}
