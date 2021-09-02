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
    LOGINFAILEDHEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Login failed!";
            else
                return "Neuspješna prijava!";
        }
    },
    LOGINFAILEDCONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Invalid username or password.";
            else
                return "Netačno korisničko ime ili lozinka.";
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
    },
    FIRSTNAMEEMPTY{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The First name field is required.";
            else
                return "Unos imena je obavezno.";
        }
    },
    LASTNAMEEMPTY{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Last name field is required.";
            else
                return "Unos prezimena je obavezno.";
        }
    },
    USERNAMEEMPTY{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Username field is required.";
            else
                return "Unos korisničkog imena je obavezno.";
        }
    },
    USERNAMEERROR{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "User with this Username already exists.";
            else
                return "Ime je zauzeto.";
        }
    },
    MAILEMPTY{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Mail field is required.";
            else
                return "Unos Mail-a je obavezno.";
        }
    },
    PASSWORDEMPTY{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Password field is required.";
            else
                return "Unos lozinke je obavezno.";
        }
    },
    PASSWORDENTRY{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Password must contain at least 4 characters.";
            else
                return "Lozinka mora sadržavati najmanje 4 karaktera.";
        }
    },
    CONFIRMPASSWORDEMPTY{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Confirm password field is required.";
            else
                return "Potvrda lozinke je obavezna.";
        }
    },
    PASSWORDMISMATCH{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Passwords do not match.";
            else
                return "Unesene lozinke se ne podudaraju.";
        }
    }


}
