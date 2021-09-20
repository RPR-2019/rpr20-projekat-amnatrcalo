package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.LoginMessages;
import ba.unsa.etf.rpr.project.enums.StageName;
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

public class LoginController {
    public Label lblWhatToEnter =new Label();
    public Label lblDontHaveAccount =new Label();
    public Label lblHeading=new Label();
    public TextField fldUsername;
    public PasswordField fldPassword;
    public Button btnLogin;
    public Hyperlink hyperLink=new Hyperlink();
    private User user;
    private ArrayList<User> users=new ArrayList<>();
    private AppDAO dao;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");

    public LoginController(User user, ArrayList<User>users,AppDAO dao) {
        this.user = user;
        this.users=users;
        this.dao=dao;
    }

    public User getUser() {
        return user;
    }

    @FXML
    public void initialize(){
        lblHeading.setText(LoginMessages.LOGIN.toString().toUpperCase(Locale.ROOT));
        lblWhatToEnter.setText(LoginMessages.WHATTOENTERLOGIN.toString());
        lblDontHaveAccount.setText(LoginMessages.DONTHAVEACCOUNT.toString());
        fldUsername.setPromptText(LoginMessages.USERNAME_PROMPT.toString());
        fldPassword.setPromptText(LoginMessages.PASSWORD_PROMPT.toString());
        hyperLink.setText(LoginMessages.SIGN_UP.toString());
        btnLogin.setText(LoginMessages.LOG_IN.toString());
    }



    public void hyperlinkAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();

        Stage registrationStage=new Stage();

        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"),bundle);
        RegisterController registerController = new RegisterController(null, users,dao);
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
            AlertClass.alertERROR(LoginMessages.LOGINFAILEDHEADER.toString(),LoginMessages.LOGINFAILEDCONTENT.toString(),"/img/road-sign-icon.png");
            fldUsername.clear();
            fldPassword.clear();
            ok=false;

        } else if(!checkLogin(fldUsername.getText(), fldPassword.getText())){
            AlertClass.alertERROR(LoginMessages.LOGINFAILEDHEADER.toString(),LoginMessages.LOGINFAILEDCONTENT.toString(),"/img/road-sign-icon.png");
            fldUsername.clear();
            fldPassword.clear();
            ok=false;
        }


        if(!ok) return;

        if(user==null) user=dao.getUser(fldUsername.getText());


        Stage stage = (Stage) fldUsername.getScene().getWindow();
        stage.close();

        //open myDay
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
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

        }));
        timeline.play();


    }
}
