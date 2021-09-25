package ba.unsa.etf.rpr.project.enums.alertText;

import ba.unsa.etf.rpr.project.enums.ListsName;

import java.util.Locale;

public enum MyDayAlertMessages {
    NOT_SELECTED {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "No tasks are selected";
            else
                return "Nije odabran ni jedan zadatak";
        }
    },
    DELETE_TASK_CONFIRMATION_HEADER {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Are you sure you want to delete ";
            else
                return "Jeste li sigurni da želite obrisati ";
        }
    },
    DELETE_TASK_CONFIRMATION_CONTENT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This task will be deleted immediately. You can't undo this action.";
            else
                return "Odabrani zadatak će se obrisati odmah. Brisanje nije moguće poništiti.";
        }
    },
    DELETE_LIST_ERROR_HEADER {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This list can't be deleted";
            else
                return "Odabrana lista se ne može izbrisati";
        }
    },
    DELETE_LIST_ERROR_CONTENT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Lists: " + ListsName.MY_DAY.toString() + ", " + ListsName.TASKS.toString() + ", " + ListsName.PLANNED.toString() + " and " + ListsName.COMPLETED.toString() + "" +
                        " can't be deleted.";
            else
                return "Liste: " + ListsName.MY_DAY.toString() + ", " + ListsName.TASKS.toString() + ", " + ListsName.PLANNED.toString() + " i " + ListsName.COMPLETED.toString() + "" +
                        " ne mogu biti izbrisane.";
        }

    },
    DELETE_LIST_CONFIRMATION_CONTENT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "By deleting this list, all tasks from it will be deleted, too.";
            else
                return "Brisanjem ove liste, svi zadaci iz nje se također brišu.";
        }
    }

}
