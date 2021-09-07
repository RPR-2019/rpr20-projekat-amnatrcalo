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

}
