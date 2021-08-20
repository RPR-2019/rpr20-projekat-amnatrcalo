package ba.unsa.etf.rpr.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    public TextField fldUsername;
    public Label errorUsername;
    public TextField fldFirstName;
    public Label errorFirstName;
    public TextField fldLastName;
    public Label errorLastName;
    public PasswordField fldPassword;
    public Label errorPassword;
    public PasswordField fldConfirmPassword;
    public Label errorConfirmPassword;



    public void hyperlinkAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Stage loginStage=new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        loginStage.setResizable(false);
        loginStage.show();
    }



    public void btnSignupAction(ActionEvent actionEvent) {
        if(fldFirstName.getText().trim().isEmpty()){
            errorFirstName.setText("The First name field is required.");
        } else{
            errorFirstName.setText(" ");
        }

        if(fldLastName.getText().trim().isEmpty()){
            errorLastName.setText("The Last name field is required.");
        }else{
            errorLastName.setText(" ");
        }

        if(fldUsername.getText().trim().isEmpty()){
            errorUsername.setText("The Username field is required.");
        }else{
            errorUsername.setText(" ");
        }

        if(fldPassword.getText().trim().isEmpty()){
            errorPassword.setText("The Password field is required.");
        } else{
            errorPassword.setText(" ");
        }

       if(fldConfirmPassword.getText().trim().isEmpty() && !fldPassword.getText().isEmpty() ) {
           System.out.println("treba se pojavit");
           errorConfirmPassword.setText("The Confirm password field is required.");
        } else if(!fldPassword.getText().isEmpty() && !fldConfirmPassword.getText().isEmpty() && !fldPassword.getText().equals(fldConfirmPassword.getText())){
           System.out.println("mismatch");
           errorConfirmPassword.setText("Passwords do not match.");
       } else if (fldConfirmPassword.getText().trim().isEmpty() && fldPassword.getText().trim().isEmpty()){
           errorConfirmPassword.setText("The Confirm password field is required.");
       } else{
           errorConfirmPassword.setText(" ");
       }



    }
}
