package ba.unsa.etf.rpr.project;

import javafx.scene.control.TextField;

public class ListController {
    public TextField fldListName;
    private String listName;

    public ListController(String listName){
        this.listName=listName;
    }



    public String getListName() {
        return fldListName.getText();
    }
}

