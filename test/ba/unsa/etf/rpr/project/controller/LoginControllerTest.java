package ba.unsa.etf.rpr.project.controller;

import static org.junit.jupiter.api.Assertions.*;

import ba.unsa.etf.rpr.project.Main;
import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.StageName;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;
import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    AppDAO dao= AppDAO.getInstance();
    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"),bundle);
        LoginController loginController = new LoginController(null, dao.users(),dao);
        loader.setController(loginController);
        root = loader.load();
        stage.setTitle(StageName.LOGIN.toString());
        stage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/login-icon.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();

    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        dao.resetDatabase();
    }

    @Test
    public void loginTest(FxRobot robot){
        robot.clickOn("#btnLogin");
      DialogPane dialogPane= robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
       robot.clickOn(okButton);
    }

    @Test
    public void incorrectLogin(FxRobot robot){
        robot.clickOn("#fldUsername");
        robot.write("amna");
        robot.clickOn("#fldPassword");
        robot.write("amnaaaaa");
        robot.clickOn("#btnLogin");
        robot.lookup(".dialog-pane").tryQuery();
    }

    @Test
    public void register(FxRobot robot){
        robot.clickOn("#hyperLink");

        //is signup window loaded
        robot.lookup("#fldMail").tryQuery().isPresent();
    }

}