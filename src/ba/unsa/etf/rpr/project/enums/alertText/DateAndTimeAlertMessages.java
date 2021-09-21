package ba.unsa.etf.rpr.project.enums.alertText;

import java.util.Locale;

public enum DateAndTimeAlertMessages {
    INVALID_START_DATE {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The start date of the task can't be before current date";
            else
                return "Datum početka zadatka ne može biti prije trenutnog datuma";
        }
    },
    INVALID_START_TIME{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The start of the task can't be before current time";
            else
                return "Vrijeme početka zadatka ne može biti prije trenutnog vremena";
        }
    },
    INVALID_END_DATE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The end of the task can't be before its start";
            else
                return "Vrijeme kraja zadatka ne može biti prije njegovog početka";
        }
    },
    ALL_DAY_HEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The start of the task is not set";
            else
                return "Vrijeme početka zadatka nije postavljeno";
        }
    },
    ALL_DAY_CONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "The start of the task must be set because this is an all day activity.";
            else
                return "Vrijeme početka zadatka mora biti postavljeno jer je ovo cjelodnevna aktivnost.";
        }
    },
    START_DATE_NOT_SET_HEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Start date is not set";
            else
                return "Vrijeme početka nije postavljeno";
        }
    },
    START_DATE_NOT_SET_CONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "End date is set, but start date is not.";
            else
                return "Vrijeme kraja zadatka je postavljeno, a početka nije.";
        }
    },
    START_DATE_ERROR{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Start date is not set proprerly";
            else
                return "Datum početka nije postavljen pravilno";
        }
    },
    END_DATE_ERROR{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "End date is not set proprerly";
            else
                return "Datum kraj nije postavljen pravilno";
        }
    },
    START_TIME_ERROR{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Start time is not set proprerly.";
            else
                return "Vrijeme početka nije postavljen pravilno";
        }
    },
    END_TIME_ERROR{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "End time is not set proprerly";
            else
                return "Vrijeme kraja nije postavljen pravilno";
        }
    },
    TIME_ERROR{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Start time or end time is not set proprerly";
            else
                return "Vrijeme početka ili vrijeme kraja zadatka nije postavljeno pravilno";
        }
    },
    REMINDER_HEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Reminder can't be set";
            else
                return "Podsjetnik ne može biti postavljen";
        }
    },
    REMINDER_CONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "You didn't specify start time of Your task.";
            else
                return "Niste naveli vrijeme početka zadatka.";
        }
    }
}
