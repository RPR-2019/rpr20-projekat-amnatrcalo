package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum AlertMessages {
    NOTSELECTED {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "No tasks are selected";
            else
                return "Nije odabran ni jedan zadatak";
        }
    },
    DELETETASKCONFIRMATIONHEADER {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Are you sure you want to delete ";
            else
                return "Jeste li sigurni da želite obrisati ";
        }
    },
    DELETETASKCONFIRMATIONCONTENT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This task will be deleted immediately. You can't undo this action.";
            else
                return "Odabrani zadatak će se obrisati odmah. Brisanje nije moguće poništiti.";
        }
    },
    DELETELISTERRORHEADER {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This list can't be deleted";
            else
                return "Odabrana lista se ne može izbrisati";
        }
    },
    DELETELISTERRORCONTENT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Lists: " + ListsName.MYDAY.toString() + ", " + ListsName.TASKS.toString() + ", " + ListsName.PLANNED.toString() + " and " + ListsName.COMPLETED.toString() + "" +
                        " can't be deleted.";
            else
                return "Liste: " + ListsName.MYDAY.toString() + ", " + ListsName.TASKS.toString() + ", " + ListsName.PLANNED.toString() + " i " + ListsName.COMPLETED.toString() + "" +
                        " ne mogu biti izbrisane.";
        }

    },
    DELETELISTCONFIRMATIONCONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "By deleting this list, all tasks from it will be deleted, too.";
            else
                return "Brisanjem ove liste, svi zadaci iz nje se također brišu.";
        }
    },
    TASKNAMEREQUIREDHEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Task name field is required";
            else
                return "Unos naziva zadatka je obavezno";
        }
    },
    TASKNAMEREQUIREDCONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "You didn't enter the name of Your task.";
            else
                return "Niste ukucali naziv zadatka.";
        }
    },
    NAMENOTAPPROVEDHEADER{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This name is not approved";
            else
                return "Ovo ime nije dozvoljeno";
        }
    },
    NAMENOTAPPROVEDCONTENT{
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Task with this name already exists.";
            else
                return "Zadatak sa ovim imenom već postoji.";
        }
    },
    TASKS_OVERLAPPIN_GHEADER {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "This task overlaps with another task";
            else
                return "Vrijeme zadatka se poklapa sa drugim zadatkom";
        }
    },
    TASKS_OVERLAPPING_CONTENT {
        @Override
        public String toString() {
            if (Locale.getDefault().getCountry().equals("US"))
                return "Are You ok with this?";
            else
                return "Je li Vam to u redu?";
        }
    }
}
