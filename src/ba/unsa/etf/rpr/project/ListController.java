package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ListController {
    public TextField fldListName;
    private CustomList list;
    private User user;

    public ListController(User user, CustomList list){
        this.user=user;
        this.list=list;
    }

    public CustomList getList() {
        return list;
    }

    public void actionSave(ActionEvent actionEvent) {
        if(!fldListName.getText().trim().isEmpty()){
            list=new CustomList(user.getUsername(),fldListName.getText());
        }
        Stage stage= (Stage) fldListName.getScene().getWindow();
        stage.close();
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage= (Stage) fldListName.getScene().getWindow();
        stage.close();
    }



}

