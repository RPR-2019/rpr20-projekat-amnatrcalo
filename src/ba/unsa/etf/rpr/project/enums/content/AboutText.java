package ba.unsa.etf.rpr.project.enums.content;

import java.util.Locale;

public enum AboutText {
    MADE{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Built on September 22, 2021";
            else
                return "Proizvedeno 22. septembra 2021.";
        }
    },
    COPYRIGHT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Copyright \u00a9 2021 Faculty of Electrical Engineering Sarajevo";
            else
                return "Sva prava pridržana \u00a9 2021 Elektrotehnički fakultet Sarajevo";
        }
    },
    CREATED_BY {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US")) {
                    return "Created by Amna Trčalo\n" + "https://github.com/RPR-2019/rpr20-projekat-amnatrcalo";

            } else {
                    return "Developer: Amna Trčalo\n"+ "https://github.com/RPR-2019/rpr20-projekat-amnatrcalo";
            }

        }

    }
}
