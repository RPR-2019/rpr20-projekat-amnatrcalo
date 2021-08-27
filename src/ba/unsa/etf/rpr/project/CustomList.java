package ba.unsa.etf.rpr.project;

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
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    @Override
    public String toString(){
        return listName;
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
