package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    public Label loginFailedField;
    public TextField fldUsername;
    public PasswordField fldPassword;
    private User user;
    private ArrayList<User> users=new ArrayList<>();
    private AppDAO dao;

    public LoginController(User user, ArrayList<User>users, AppDAO dao) {
        this.user = user;
        this.users=users;
        this.dao=dao;
    }

    public User getUser() {
        return user;
    }



    public void hyperlinkAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();

        Stage registrationStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        RegisterController registerController = new RegisterController(null, users,dao);
        loader.setController(registerController);
        root = loader.load();

        registrationStage.setTitle("Sign Up");
        registrationStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
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

    public boolean checkLogin(String username, String password){
        boolean found=false;
        for(User u:users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                found=true;
            }
        }
        return found;
    }


    public void btnLoginAction(ActionEvent actionEvent) {
        boolean ok=true;

        if(fldUsername.getText().trim().isEmpty() || fldPassword.getText().trim().isEmpty())
        {
            loginFailedField.setText("Login failed! Invalid username or password!");
            loginFailedField.getStyleClass().add("loginFailed");
            fldUsername.clear();
            fldPassword.clear();
            ok=false;

        } else if(!checkLogin(fldUsername.getText(), fldPassword.getText())){
            loginFailedField.setText("Login failed! Invalid username or password!");
            loginFailedField.getStyleClass().add("loginFailed");
            fldUsername.clear();
            fldPassword.clear();
            ok=false;
        }
        else {
            loginFailedField.setText(" ");
            loginFailedField.getStyleClass().removeAll("loginFailed");

        }

        if(!ok) return;

        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();

    }
}
