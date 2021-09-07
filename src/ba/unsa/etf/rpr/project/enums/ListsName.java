package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum ListsName {
    MYDAY{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "My Day";
            else
                return "Moj dan";
        }
    },
    COMPLETED{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Completed";
            else
                return "Dovršeno";
        }
    },
    TASKS{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Tasks";
            else
                return "Zadaci";
        }
    },
    PLANNED{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Planned";
            else
                return "Planirano";
        }
    }

}