package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum StageName {
    SIGNUP{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Sign Up";
            else
                return "Registracija";
        }
    },
    LOGIN{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Login";
            else
                return "Prijava";
        }
    },
    YOURTASK{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Your Task";
            else
                return "Vaš zadatak";
        }
    },
    YOURLIST{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Your List";
            else
                return "Vaša lista";
        }
    }
}
