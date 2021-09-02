package ba.unsa.etf.rpr.project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterController {
    public TextField fldUsername;
    public Label errorUsername;
    public TextField fldFirstName;
    public Label errorFirstName;
    public TextField fldLastName;
    public Label errorLastName;
    public TextField fldMail;
    public Label errorMail;
    public PasswordField fldPassword;
    public Label errorPassword;
    public PasswordField fldConfirmPassword;
    public Label errorConfirmPassword;
    public Label lblWhatToEnter=new Label();
    public Label lblHaveAccount=new Label();
    private ArrayList<User> users=new ArrayList<>();
    private User user;
    private AppDAO dao;

    public RegisterController(User user,ArrayList<User>users, AppDAO dao) {
        this.user=user;
        this.users=users;
        this.dao=dao;
    }

    @FXML
    public void initialize(){
        lblWhatToEnter.setText(LoginMessages.WHATTOENTERREGISTRATION.toString());
        lblHaveAccount.setText(LoginMessages.ALREADYHAVEACCOUNT.toString());
    }

    public User getUser() {
        return user;
    }

    public void hyperlinkAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();

        Stage loginStage=new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),bundle);
        LoginController loginController = new LoginController(null, users,dao);
        loader.setController(loginController);
        root = loader.load();

        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
        loginStage.getIcons().add(icon);
        loginStage.setResizable(false);
        loginStage.show();
    }

    public boolean isUsernameFree(String username){
        boolean free=true;
        for(User user:users){
            if(user.getUsername().equals(username)) free=false;
        }
        return free;
    }


    public void btnSignupAction(ActionEvent actionEvent) throws IOException {
        boolean ok=true;

        if(fldFirstName.getText().trim().isEmpty()){
            errorFirstName.setText(LoginMessages.FIRSTNAMEEMPTY.toString());
            ok=false;
        } else{
            errorFirstName.setText(" ");
        }

        if(fldLastName.getText().trim().isEmpty()){
            errorLastName.setText(LoginMessages.LASTNAMEEMPTY.toString());
            ok=false;
        }else{
            errorLastName.setText(" ");
        }

        if(fldUsername.getText().trim().isEmpty()) {
            errorUsername.setText(LoginMessages.USERNAMEEMPTY.toString());
            ok=false;
        }else if(!fldUsername.getText().trim().isEmpty() && !isUsernameFree(fldUsername.getText())) {
            errorUsername.setText(LoginMessages.USERNAMEERROR.toString());
            ok=false;
        }
        else{
            errorUsername.setText(" ");
        }

        if(fldMail.getText().trim().isEmpty()) {
            errorMail.setText(LoginMessages.MAILEMPTY.toString());
            ok = false;
        }else{
            errorMail.setText(" ");
        }


        if(fldPassword.getText().trim().isEmpty()){
            errorPassword.setText(LoginMessages.PASSWORDEMPTY.toString());
            ok=false;
        } else if(!fldPassword.getText().isEmpty()&& fldPassword.getText().length()<4){
            ok=false;
            errorPassword.setText(LoginMessages.PASSWORDENTRY.toString());
        }else{
            errorPassword.setText(" ");
        }

       if(fldConfirmPassword.getText().trim().isEmpty() && !fldPassword.getText().isEmpty() ) {
           errorConfirmPassword.setText(LoginMessages.CONFIRMPASSWORDEMPTY.toString());
           ok=false;
        } else if(!fldPassword.getText().isEmpty() && !fldConfirmPassword.getText().isEmpty() && !fldPassword.getText().equals(fldConfirmPassword.getText())){
           errorConfirmPassword.setText(LoginMessages.PASSWORDMISMATCH.toString());
           ok=false;
       } else if (fldConfirmPassword.getText().trim().isEmpty() && fldPassword.getText().trim().isEmpty()){
           errorConfirmPassword.setText(LoginMessages.CONFIRMPASSWORDEMPTY.toString());
           ok=false;
       } else{
           errorConfirmPassword.setText(" ");
       }

       if(!ok) return;

       if (user == null) user = new User();
       user.setFirstName(fldFirstName.getText());
       user.setLastName(fldLastName.getText());
       user.setUsername(fldUsername.getText());
       user.setMail(fldMail.getText());
       user.setPassword(fldPassword.getText());

        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();

        dao.addList(user.getUsername(),"My day");
        dao.addList(user.getUsername(),"Planned");
        dao.addList(user.getUsername(),"Tasks");
        dao.addList(user.getUsername(),"Completed");


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), event -> {
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
            myDayStage.setOnCloseRequest(event2->{
                MyDayController.timelineInfinite.stop();
            });

            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(1000), event2 -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Let's get started!");
                alert.setHeaderText(null);
                alert.setContentText("If You need help, check Help menu at the top left area of the screen. Enjoy in planning Your day! ");

                alert.show();
            }));
            timeline2.play();
        }));
        timeline.play();
    }
}



