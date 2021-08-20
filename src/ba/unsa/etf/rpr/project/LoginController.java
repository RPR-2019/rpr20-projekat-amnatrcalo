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
import java.util.ArrayList;

public class LoginController {
    public Label loginFailedField;
    public TextField fldUsername;
    public PasswordField fldPassword;
    private User user;
    private ArrayList<User> users=new ArrayList<>();

    public LoginController(User user, ArrayList<User>users) {
        this.user = user;
        this.users=users;
    }

    public User getUser() {
        return user;
    }



    public void hyperlinkAction(ActionEvent actionEvent) throws IOException {
        Stage registrationStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        RegisterController registerController = new RegisterController(null, users);
        loader.setController(registerController);
        root = loader.load();

        registrationStage.setTitle("Sign Up");
        registrationStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        registrationStage.setResizable(false);
        registrationStage.show();

        /*registrationStage.setOnHiding( event -> {
            User user = registerController.getUser();
            if (user != null) {
                dao.addUser(user);
            }
        } );*/
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
        //boolean ok=true;
        if(fldUsername.getText().trim().isEmpty() || fldPassword.getText().trim().isEmpty())
        {
            loginFailedField.setText("Login failed! Invalid username or password!");
            loginFailedField.getStyleClass().add("loginFailed");
            fldUsername.clear();
            fldPassword.clear();
           // ok=false;
        } else if(!checkLogin(fldUsername.getText(), fldPassword.getText())){
            loginFailedField.setText("Login failed! Invalid username or password!");
            loginFailedField.getStyleClass().add("loginFailed");
            fldUsername.clear();
            fldPassword.clear();
          //  ok=false;
        }
        else {
            loginFailedField.setText(" ");
            loginFailedField.getStyleClass().removeAll("loginFailed");

        }

        /*if(!ok) return;

        user.setUsername(fldUsername.getText());
        user.setPassword(fldPassword.getText());*/




    }
}
