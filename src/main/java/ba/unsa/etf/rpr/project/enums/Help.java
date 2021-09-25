package ba.unsa.etf.rpr.project.enums;

import java.util.Locale;

public enum Help {
    LISTS{
        @Override
        public String toString() {
            StringBuilder eng=new StringBuilder();
            eng.append("There are 4 default lists that can't be deleted: 'My Day', 'Planned', 'Tasks' and 'Completed'.")
                    .append("\n").append("You can create or delete Your own lists.").append("\n\n")
                    .append("By creating the task, If You don't specify Your list name and date and time of the task are set," +
                            " the task will be stored in 'Planned' list.")
                    .append("\n\n")
                    .append("By creating the task, If You don't specify Your list name and date and time of the task are not set," +
                            " the task will be stored in 'Tasks' list.")
                    .append("\n\n")
                    .append("If You specify Your list name, task will be stored in that list.")
                    .append("\n\n")
                    .append("If You mark task as completed (checkbox next to its name), the task will be stored in 'Completed' list." +
                            " You won't get notifications from these tasks.")
                    .append("\n")
                    .append("You can undo this action by clicking the same checkbox. The task will be stored in " +
                            " 'Planned' or 'Tasks' list.")
                    .append("\n\n")
                    .append("All tasks that start today will be shown in My Day list.");

            StringBuilder bos=new StringBuilder();
            bos.append("Postoje 4 liste koje ne mogu biti obrisane: 'Moj dan', 'Planirano', 'Zadaci' i 'Dovršeno'.")
                    .append("\n").append("Možete kreirati ili brisati Vaše vlastite liste.").append("\n\n")
                    .append("Prilikom kreiranja zadatka ako ne specificirate ime liste, a datum i vrijeme zadatka je postavljeno," +
                            " zadatak će biti spremljen u listu 'Planirano'.")
                    .append("\n\n")
                    .append("Prilikom kreiranja zadatka ako ne specificirate ime liste, a datum i vrijeme zadatka nije postavljeno," +
                            " zadatak će biti spremljen u listu 'Zadaci'.")
                    .append("\n\n")
                    .append("Ako specificirate svoje ime liste, zadatak će biti spremljen u tu listu.")
                    .append("\n\n")
                    .append("Ako označite zadatak kao dovršen (checkbox pored njegovog imena), zadatak će biti spremljen u listu 'Dovršeno'," +
                            " i taj zadatak Vam neće slati notifikacije.")
                    .append("\n")
                    .append("Ovu akciju možete poništiti ponovnim označavanjem istog checkbox-a. Zadatak će biti" +
                            " spremljen u listu 'Planirano' ili 'Zadaci'.")
                    .append("\n\n")
                    .append("Svi zadaci koji počinju danas će se pojaviti u listi Moj dan.");

            if (Locale.getDefault().getCountry().equals("US"))
                return eng.toString();
            else
                return bos.toString();
        }
    },
    LISTS_HEADING{
        @Override
        public String toString(){
            if (Locale.getDefault().getCountry().equals("US"))
                return "Lists";
            else
                return "Liste";
        }
    },
    TASK_HEADING{
        @Override
        public String toString(){
            if (Locale.getDefault().getCountry().equals("US"))
                return "Creating a task";
            else
                return "Kreiranje zadatka";
        }
    },
    TASK{
        @Override
        public String toString(){
            StringBuilder eng=new StringBuilder();
            eng.append("To add a new task, You should press '+' button in the left corner of the screen" +
                    " or press ENTER.")
                    .append("\n\n")
                    .append("The name of the task is needed, while other information are optional:" +
                            " You can add date and time, note or reminder.")
                    .append("\n\n")
                    .append("If Your task is an all-day-activity, You should enter the start date." +
                            " Otherwise, date and time of the start  of the task are required, while date and time of the end " +
                            " of the task are optional.")
                    .append("\n")
                    .append("You can also receive reminder via e-mail or as notification alert.");

            StringBuilder bos=new StringBuilder();
            bos.append("Za dodavanje novog zadatka pritisnite '+' dugme u lijevom uglu ekrana" +
                    " ili pritiskom na dugme ENTER.")
                    .append("\n\n")
                    .append("Specificiranje imena zadatka je neophodno, dok su ostale informacije opcionalne:" +
                            " možete dodati datum i vrijeme, bilješku ili podsjetnik.")
                    .append("\n\n")
                    .append("Ako je zadatak cjelodnevna aktivnost, trebate ukucati datum početka zadatka." +
                            " U suprotnom, datum i vrijeme početka zadatka su obavezni, dok su datum i vrijeme kraja " +
                            " opcionalni.")
                    .append("\n")
                    .append("Također, možete primiti podsjetnik e-mailom ili notifikacijom.");


            if (Locale.getDefault().getCountry().equals("US"))
                return eng.toString();
            else
                return bos.toString();
        }
    }
}
