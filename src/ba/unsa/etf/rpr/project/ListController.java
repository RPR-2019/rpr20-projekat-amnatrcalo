package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class ListController {
    public TextField fldListName;
    private List list;
    private User user;

    public ListController(User user,List list){
        this.user=user;
        this.list=list;
    }

    public List getList() {
        return list;
    }

    public void actionSave(ActionEvent actionEvent) {
        if(!fldListName.getText().trim().isEmpty()){
            list=new List(user.getUsername(),fldListName.getText());
        }
        Stage stage= (Stage) fldListName.getScene().getWindow();
        stage.close();
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage= (Stage) fldListName.getScene().getWindow();
        stage.close();
    }



}

