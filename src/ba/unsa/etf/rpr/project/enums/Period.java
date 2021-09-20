package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum Period {
    HOURS{
        @Override
        public String toString(){
            if (Locale.getDefault().getCountry().equals("US"))
                return "hours";
            else
                return "sati";
        }
    },
    MINS{
        @Override
        public String toString(){
            if (Locale.getDefault().getCountry().equals("US"))
                return "minutes";
            else
                return "minuta";
        }
    },
    DAYS{
        @Override
        public String toString(){
            if (Locale.getDefault().getCountry().equals("US"))
                return "days";
            else
                return "dana";
        }
    };




}
