package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    private AppDAO dao;

    public HomeController(){
        dao=AppDAO.getInstance();
    }
    public void loginAction(ActionEvent actionEvent) throws IOException {
        Stage loginStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        LoginController loginController = new LoginController(null, dao.users());
        loader.setController(loginController);
        root = loader.load();

        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        loginStage.setResizable(false);
        loginStage.show();

        /*loginStage.setOnHiding( event -> {
            User user = loginController.getUser();
            if (user != null) {
                User foundUser=dao.getUser(user.getUsername());
                if(foundUser==null || !foundUser.getPassword().equals(user.getPassword())){
                    System.out.println("Ne valja");
                    loginStage.show();
                    loginController.setOk(false);
                }
            }
        } );*/
    }

    public void registrationAction(ActionEvent actionEvent) throws IOException {
        Stage registrationStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        RegisterController registerController = new RegisterController(null, dao.users());
        loader.setController(registerController);
        root = loader.load();

        registrationStage.setTitle("Sign Up");
        registrationStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        registrationStage.setResizable(false);
        registrationStage.show();

        registrationStage.setOnHiding( event -> {
            User user = registerController.getUser();
            if (user != null) {
                dao.addUser(user);
            }
        } );

    }
}
