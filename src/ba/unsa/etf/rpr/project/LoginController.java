package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Label loginFailedField;
    public TextField fldUsername;
    public PasswordField fldPassword;

    public void hyperlinkAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        Stage registrationStage=new Stage();
        registrationStage.setTitle("Registration");
        registrationStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        registrationStage.setResizable(false);
        registrationStage.show();
    }


    public void btnLoginAction(ActionEvent actionEvent) {
        if(fldUsername.getText().trim().isEmpty() || fldPassword.getText().trim().isEmpty())
        {
            loginFailedField.setText("Login failed! Invalid username or password!");
            loginFailedField.getStyleClass().add("loginFailed");
        }


    }
}
