package ba.unsa.etf.rpr.project;

public class List {

    private String username;
    private String listName;

    public List(String username, String listName) {

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
}
