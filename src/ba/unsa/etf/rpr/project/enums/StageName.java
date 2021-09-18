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
    },
    DATEANDTIME{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Date and Time";
            else
                return "Datum i vrijeme";
        }
    },
    ABOUT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "About";
            else
                return "O nama";
        }
    },
    HELP{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Help";
            else
                return "Pomoć";
        }
    },
    MY_PROFILE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "My profile";
            else
                return "Moj profil";
        }
    }
}
