package ba.unsa.etf.rpr.project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    public Label loginFailedMessage;
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
        });



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


    public void btnLoginAction(ActionEvent actionEvent) throws IOException {
        boolean ok=true;

        if(fldUsername.getText().trim().isEmpty() || fldPassword.getText().trim().isEmpty())
        {
            loginFailedMessage.setText("Login failed! Invalid username or password!");
            loginFailedMessage.getStyleClass().add("loginFailed");
            fldUsername.clear();
            fldPassword.clear();
            ok=false;

        } else if(!checkLogin(fldUsername.getText(), fldPassword.getText())){
            loginFailedMessage.setText("Login failed! Invalid username or password!");
            loginFailedMessage.getStyleClass().add("loginFailed");
            fldUsername.clear();
            fldPassword.clear();
            ok=false;
        }
        else {
            loginFailedMessage.setText(" ");
            loginFailedMessage.getStyleClass().removeAll("loginFailed");

        }

        if(!ok) return;

        if(user==null) user=dao.getUser(fldUsername.getText());


        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();

        //open myDay
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            Stage myDayStage=new Stage();
            Parent root=null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myDay.fxml"));
            MyDayController myDayController = new MyDayController(user,dao.lists(user));
            loader.setController(myDayController);

            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            myDayStage.setTitle("My Day");
            myDayStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
            Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
            myDayStage.getIcons().add(icon);
            myDayStage.setResizable(true);
            myDayStage.show();

        }));
        timeline.play();


    }
}
