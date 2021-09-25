package ba.unsa.etf.rpr.project.model;

import ba.unsa.etf.rpr.project.enums.ListsName;

public class CustomList {

    private String username;
    private String listName;

    public CustomList(String username, String listName) {

        this.username = username;
        this.listName = listName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getListName() {
     /*  return switch (listName) {
            case "My Day" -> ListsName.MYDAY.toString();
            case "Tasks" -> ListsName.TASKS.toString();
            case "Planned" -> ListsName.PLANNED.toString();
            case "Completed" -> ListsName.COMPLETED.toString();
            default -> listName;
        };*/
       return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    @Override
    public String toString(){
        return switch (listName) {
            case "My Day" -> ListsName.MY_DAY.toString();
            case "Tasks" -> ListsName.TASKS.toString();
            case "Planned" -> ListsName.PLANNED.toString();
            case "Completed" -> ListsName.COMPLETED.toString();
            default -> listName;
        };
       // return listName;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof CustomList)) {
            return false;
        }

        CustomList l = (CustomList) o;

        return this.getListName().equals(((CustomList) o).getListName())
                && this.getUsername().equals(((CustomList) o).getUsername());
    }

}
