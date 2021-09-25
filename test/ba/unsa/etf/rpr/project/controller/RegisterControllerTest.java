package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.StageName;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class RegisterControllerTest {
    AppDAO dao= AppDAO.getInstance();
    @Start
    public void start(Stage stage) throws Exception {
        dao.resetDatabase();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"),ResourceBundle.getBundle("Translation"));
        RegisterController registerController = new RegisterController(null, dao.users(),dao);
        loader.setController(registerController);
        root = loader.load();

        stage.setTitle(StageName.SIGNUP.toString());
        stage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/login-icon.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();
        stage.toFront();

    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        dao.resetDatabase();
    }

    @Test
    public void incorrectMail(FxRobot robot){
        robot.clickOn("#fldMail");
        robot.write("amna");
        robot.clickOn("#btnSignup");

        //myDay window doesn't show
        boolean error=false;
        if(robot.lookup("#fldUsername").tryQuery().isPresent()) error=true;
        assertTrue(error);
    }

    @Test
    public void unavailableUsername(FxRobot robot){
        robot.clickOn("#fldFirstName");
        robot.write("Mujo");
        robot.clickOn("#fldLastName");
        robot.write("Mujic");
        robot.clickOn("#fldUsername");
        robot.write("amna");
        robot.clickOn("#fldMail");
        robot.write("mujo@gmail.com");
        robot.clickOn("#fldPassword");
        robot.write("1234");
        robot.clickOn("#fldConfirmPassword");
        robot.write("1234");
        robot.clickOn("#btnSignup");

        boolean error=false;
        if(robot.lookup("#fldUsername").tryQuery().isPresent()) error=true;
        assertTrue(error);
    }

    @Test
    public void shortPassword(FxRobot robot){
        robot.clickOn("#fldFirstName");
        robot.write("Mujo");
        robot.clickOn("#fldLastName");
        robot.write("Mujic");
        robot.clickOn("#fldUsername");
        robot.write("mujo");
        robot.clickOn("#fldMail");
        robot.write("mujo@gmail.com");
        robot.clickOn("#fldPassword");
        robot.write("12");
        robot.clickOn("#fldConfirmPassword");
        robot.write("12");
        robot.clickOn("#btnSignup");

        boolean error=false;
        if(robot.lookup("#fldUsername").tryQuery().isPresent()) error=true;
        assertTrue(error);
    }

    @Test
    public void incorrectPassword(FxRobot robot){
        robot.clickOn("#fldFirstName");
        robot.write("Mujo");
        robot.clickOn("#fldLastName");
        robot.write("Mujic");
        robot.clickOn("#fldUsername");
        robot.write("mujo");
        robot.clickOn("#fldMail");
        robot.write("mujo@gmail.com");
        robot.clickOn("#fldPassword");
        robot.write("124567");
        robot.clickOn("#fldConfirmPassword");
        robot.write("1245");
        robot.clickOn("#btnSignup");

        boolean error=false;
        if(robot.lookup("#fldUsername").tryQuery().isPresent()) error=true;
        assertTrue(error);
    }






}