package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.StageName;
import ba.unsa.etf.rpr.project.enums.content.LoginMessages;
import ba.unsa.etf.rpr.project.enums.content.TooltipContent;
import ba.unsa.etf.rpr.project.maker.AlertClass;
import ba.unsa.etf.rpr.project.maker.NotificationReminder;
import ba.unsa.etf.rpr.project.maker.TooltipClass;
import ba.unsa.etf.rpr.project.model.User;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
    public Label lblHeading=new Label();
    public Button btnSignup;
    public Hyperlink hyperLink=new Hyperlink();
    private ArrayList<User> users=new ArrayList<>();
    private User user;
    private AppDAO dao;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");

    public RegisterController(User user,ArrayList<User>users, AppDAO dao) {
        this.user=user;
        this.users=users;
        this.dao=dao;
    }

    @FXML
    public void initialize(){
        lblHeading.setText(LoginMessages.SIGNUP.toString().toUpperCase(Locale.ROOT));
        lblWhatToEnter.setText(LoginMessages.WHAT_TO_ENTER_REGISTRATION.toString());
        lblHaveAccount.setText(LoginMessages.ALREADY_HAVE_ACCOUNT.toString());
        fldFirstName.setPromptText(LoginMessages.FIRST_NAME_PROMPT.toString());
        fldLastName.setPromptText(LoginMessages.LAST_NAME_PROMPT.toString());
        fldUsername.setPromptText(LoginMessages.USERNAME_PROMPT.toString());
        fldPassword.setPromptText(LoginMessages.PASSWORD_PROMPT.toString());
        fldConfirmPassword.setPromptText(LoginMessages.CONFIRM_PASSWORD_PROMPT.toString());
        btnSignup.setText(LoginMessages.SIGN_UP.toString());
        hyperLink.setText(LoginMessages.LOG_IN.toString());
        fldMail.setTooltip(TooltipClass.makeTooltip(TooltipContent.EMAIL.toString()));
    }

    public User getUser() {
        return user;
    }

    public void hyperlinkAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();

        Stage loginStage=new Stage();

        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),bundle);
        LoginController loginController = new LoginController(null, users,dao);
        loader.setController(loginController);
        root = loader.load();
        loginStage.setTitle(StageName.LOGIN.toString());
        loginStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/login-icon.png"));
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

    public static boolean emailValidation(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void signupAction(ActionEvent actionEvent) throws IOException {
        boolean ok=true;

        if(fldFirstName.getText().trim().isEmpty()){
            errorFirstName.setText(LoginMessages.FIRST_NAME_EMPTY.toString());
            ok=false;
        } else{
            errorFirstName.setText(" ");
        }

        if(fldLastName.getText().trim().isEmpty()){
            errorLastName.setText(LoginMessages.LAST_NAME_EMPTY.toString());
            ok=false;
        }else{
            errorLastName.setText(" ");
        }

        if(fldUsername.getText().trim().isEmpty()) {
            errorUsername.setText(LoginMessages.USERNAME_EMPTY.toString());
            ok=false;
        }else if(!fldUsername.getText().trim().isEmpty() && !isUsernameFree(fldUsername.getText())) {
            errorUsername.setText(LoginMessages.USERNAME_ERROR.toString());
            ok=false;
        }
        else{
            errorUsername.setText(" ");
        }

        if(fldMail.getText().trim().isEmpty()) {
            errorMail.setText(LoginMessages.MAIL_EMPTY.toString());
            ok = false;
        }else if(!emailValidation(fldMail.getText())){
           errorMail.setText(LoginMessages.INVALID_MAIL.toString());
           ok=false;
        } else{
            errorMail.setText(" ");
        }


        if(fldPassword.getText().trim().isEmpty()){
            errorPassword.setText(LoginMessages.PASSWORD_EMPTY.toString());
            ok=false;
        } else if(!fldPassword.getText().isEmpty()&& fldPassword.getText().length()<4){
            ok=false;
            errorPassword.setText(LoginMessages.PASSWORD_ENTRY.toString());
        }else{
            errorPassword.setText(" ");
        }

       if(fldConfirmPassword.getText().trim().isEmpty() && !fldPassword.getText().isEmpty() ) {
           errorConfirmPassword.setText(LoginMessages.CONFIRM_PASSWORD_EMPTY.toString());
           ok=false;
        } else if(!fldPassword.getText().isEmpty() && !fldConfirmPassword.getText().isEmpty() && !fldPassword.getText().equals(fldConfirmPassword.getText())){
           errorConfirmPassword.setText(LoginMessages.PASSWORD_MISMATCH.toString());
           ok=false;
       } else if (fldConfirmPassword.getText().trim().isEmpty() && fldPassword.getText().trim().isEmpty()){
           errorConfirmPassword.setText(LoginMessages.CONFIRM_PASSWORD_EMPTY.toString());
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

        dao.addList(user.getUsername(),"My Day");
        dao.addList(user.getUsername(), "Planned");
        dao.addList(user.getUsername(),"Tasks");
        dao.addList(user.getUsername(),"Completed");


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), event -> {
            Stage myDayStage=new Stage();
            Parent root=null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myDay.fxml"),bundle);
            MyDayController myDayController = new MyDayController(user,dao.lists(user));
            loader.setController(myDayController);
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            myDayStage.setTitle("ToDo App");
            myDayStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
            Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
            myDayStage.getIcons().add(icon);
            myDayStage.setResizable(true);
            myDayStage.setMinHeight(650);
            myDayStage.setMinWidth(760);
            String css = NotificationReminder.class.getResource("/css/notificationpopup.css").toExternalForm();
            myDayStage.getScene().getStylesheets().add(0,css);
            myDayStage.show();
            myDayStage.setOnCloseRequest(event2->{
                MyDayController.timelineInfinite.stop();
                MyDayController.timeline2.stop();
                MyDayController.timeline3.stop();
            });

            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(1000), event2 -> {
                AlertClass.alertINFORMATION(LoginMessages.REGISTER_MESSAGE_TITLE.toString(), LoginMessages.REGISTER_MESSAGE_HEADER.toString(),  LoginMessages.REGISTER_MESSAGE_CONTENT.toString(),"/img/salute.png" );
            }));
            timeline2.play();
        }));
        timeline.play();
    }
}



