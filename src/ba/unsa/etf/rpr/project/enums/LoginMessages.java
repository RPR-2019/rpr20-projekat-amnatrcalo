package ba.unsa.etf.rpr.project.enums;

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
    INVALIDMAIL{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This email address is not valid.";
            else
                return "Unesena email adresa nije validna.";
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
    },
    REGISTERMESSAGETITLE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Let's get started!";
            else
                return "Počnimo!";
        }
    },
    REGISTERMESSAGEHEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "It looks like you are starting To Do APP for the first time";
            else
                return "Izgleda da koristite To Do App prvi put";
        }
    },
    REGISTERMESSAGECONTENT{
        @Override
        public String toString() {
            String s1="", s2="";
            if (Locale.getDefault().getCountry().equals("US")){
                return "If You need help, check Help menu at the top left area of the screen.\nEnjoy in planning Your day!";
            }

            else{
              return "Ako trebate pomoć, provjerite Help menu u gornjem lijevom ćošku ekrana.\nUživajte u planiranju Vašeg dana!";

            }

        }
    }


}
