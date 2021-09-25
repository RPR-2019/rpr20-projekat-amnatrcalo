package ba.unsa.etf.rpr.project.enums.content;

import java.util.Locale;

public enum LoginMessages {
    WHAT_TO_ENTER_LOGIN {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Please enter your username and password";
            else
                return "Unesite svoje korisničko ime i lozinku";
        }
    },
    DONT_HAVE_ACCOUNT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Don't have an account?";
            else
                return "Nemate račun?";
        }
    },
    LOGIN_FAILED_HEADER {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Login failed!";
            else
                return "Neuspješna prijava!";
        }
    },
    LOGIN_FAILED_CONTENT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Invalid username or password.";
            else
                return "Netačno korisničko ime ili lozinka.";
        }

    },
    WHAT_TO_ENTER_REGISTRATION {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Please fill in this form";
            else
                return "Popunite ovu formu za kreiranje računa";
        }
    },
    ALREADY_HAVE_ACCOUNT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Already have an account?";
            else
                return "Već imate račun?";
        }
    },
    FIRST_NAME_EMPTY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The First name field is required.";
            else
                return "Unos imena je obavezno.";
        }
    },
    LAST_NAME_EMPTY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Last name field is required.";
            else
                return "Unos prezimena je obavezno.";
        }
    },
    USERNAME_EMPTY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Username field is required.";
            else
                return "Unos korisničkog imena je obavezno.";
        }
    },
    USERNAME_ERROR {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "User with this Username already exists.";
            else
                return "Ime je zauzeto.";
        }
    },
    MAIL_EMPTY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Mail field is required.";
            else
                return "Unos Mail-a je obavezno.";
        }
    },
    INVALID_MAIL {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This email address is not valid.";
            else
                return "Unesena email adresa nije validna.";
        }
    },
    PASSWORD_EMPTY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Password field is required.";
            else
                return "Unos lozinke je obavezno.";
        }
    },
    PASSWORD_ENTRY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Password must contain at least 4 characters.";
            else
                return "Lozinka mora sadržavati najmanje 4 karaktera.";
        }
    },
    CONFIRM_PASSWORD_EMPTY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The Confirm password field is required.";
            else
                return "Potvrda lozinke je obavezna.";
        }
    },
    PASSWORD_MISMATCH {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Passwords do not match.";
            else
                return "Unesene lozinke se ne podudaraju.";
        }
    },
    REGISTER_MESSAGE_TITLE {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Let's get started!";
            else
                return "Počnimo!";
        }
    },
    REGISTER_MESSAGE_HEADER {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "It looks like you are starting To Do APP for the first time";
            else
                return "Izgleda da koristite To Do App prvi put";
        }
    },
    REGISTER_MESSAGE_CONTENT {
        @Override
        public String toString() {
            String s1="", s2="";
            if (Locale.getDefault().getCountry().equals("US")){
                return "If You need help, check Help menu at the top left area of the screen.\nEnjoy in planning Your day!";
            }

            else{
              return "Ako trebate pomoć, provjerite menu Pomoć u gornjem lijevom uglu ekrana.\nUživajte u planiranju Vašeg dana!";

            }

        }
    },
    USERNAME_PROMPT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Username";
            else
                return "Korisničko ime";
        }
    },
    PASSWORD_PROMPT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Password";
            else
                return "Lozinka";
        }
    },
    FIRST_NAME_PROMPT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "First Name";
            else
                return "Ime";
        }
    },
    LAST_NAME_PROMPT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Last Name";
            else
                return "Prezime";
        }
    },
    CONFIRM_PASSWORD_PROMPT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Confirm Password";
            else
                return "Potvrdi lozinku";
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
    LOG_IN{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Log in";
            else
                return "Prijavite se";
        }
    },
    SIGN_UP{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Sign up";
            else
                return "Registrujte se";
        }
    },
    SIGNUP{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Sign up";
            else
                return "Registracija";
        }
    }


}
