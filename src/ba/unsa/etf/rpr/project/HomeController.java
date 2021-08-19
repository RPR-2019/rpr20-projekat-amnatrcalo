package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    public void loginAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Stage loginStage=new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        loginStage.setResizable(false);
        loginStage.show();
    }

    public void registrationAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        Stage registrationStage=new Stage();
        registrationStage.setTitle("Sign Up");
        registrationStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        registrationStage.setResizable(false);
        registrationStage.show();

    }
}
