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
    }
}
