package ba.unsa.etf.rpr.project.enums;

import java.util.ArrayList;
import java.util.Locale;

public enum ListsName {
    MY_DAY {
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
    };

    public static ArrayList<String> defaultListsName(){
       ArrayList<String> res=new ArrayList<>();
        res.add("My Day");
        res.add("Tasks");
        res.add("Completed");
        res.add("Planned");
       return res;
    }

}
