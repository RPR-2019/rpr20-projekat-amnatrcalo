package ba.unsa.etf.rpr.project;

import java.util.Locale;

public enum LoginMessages {
    WHATTOENTERLOGIN {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Please enter your username and password";
            else
                return "Unesite svoje korisničko ime i lozinku";
        }
    },
    DONTHAVEACCOUNT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Don't have an account?";
            else
                return "Nemate račun?";
        }
    },
    LOGINFAILED{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Login failed! Invalid username or password.";
            else
                return "Neuspješna prijava! Netačno korisničko ime ili lozinka.";
        }
    },
    WHATTOENTERREGISTRATION{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Please fill in this form to create an account";
            else
                return "Popunite ovu formu za kreiranje računa";
        }
    },
    ALREADYHAVEACCOUNT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Already have an account?";
            else
                return "Već imate račun?";
        }
    }

}
