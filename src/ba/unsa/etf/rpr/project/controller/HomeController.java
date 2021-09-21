package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.content.LoginMessages;
import ba.unsa.etf.rpr.project.model.User;
import ba.unsa.etf.rpr.project.enums.StageName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ResourceBundle;

public class HomeController {
    private AppDAO dao;
    public  Button btnLogin;
    public Button btnSignup;

    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");

    public HomeController(){
        dao=AppDAO.getInstance();
    }

    @FXML
    public void initialize(){
        btnLogin.setText(LoginMessages.LOG_IN.toString());
        btnSignup.setText(LoginMessages.SIGN_UP.toString());
    }


    public void loginAction(ActionEvent actionEvent) throws IOException {


        Stage loginStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),bundle);
        LoginController loginController = new LoginController(null, dao.users(),dao);
        loader.setController(loginController);
        root = loader.load();

        loginStage.setTitle(StageName.LOGIN.toString());
        loginStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/login-icon.png"));
        loginStage.getIcons().add(icon);
        loginStage.setResizable(false);
        loginStage.show();
    }

    public void registrationAction(ActionEvent actionEvent) throws IOException {

        Stage registrationStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"),bundle);
        RegisterController registerController = new RegisterController(null, dao.users(),dao);
        loader.setController(registerController);
        root = loader.load();

        registrationStage.setTitle(StageName.SIGNUP.toString());
        registrationStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/login-icon.png"));
        registrationStage.getIcons().add(icon);
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
