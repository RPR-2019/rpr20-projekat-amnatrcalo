package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.StageName;
import ba.unsa.etf.rpr.project.model.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(ApplicationExtension.class)
class DateAndTimeControllerTest {

    Stage theStage;
    AppDAO dao= AppDAO.getInstance();
    /********************
     BEFORE THESE TESTS, SET STARTYEAR, STARTMONTH AND STARTDAY AS CURRENT DAY!!!
     *******************/
    private final Task task=new Task(1,"mujo","mujo's task", 2021,9,23,-1,-1,1,1,1,-1,-1," ",false,-1," ",false,false,"tasks",false);
    @Start
    public void start(Stage stage) throws Exception {
        dao.resetDatabase();

        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dateAndTime.fxml"),ResourceBundle.getBundle("Translation"));
        DateAndTimeController dateAndTimeController = new DateAndTimeController(task);
        loader.setController(dateAndTimeController);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(StageName.DATE_AND_TIME.toString());
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/date-and-time-icon.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage=stage;

    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        dao.resetDatabase();
    }


    @Test
    public void setAllday(FxRobot robot){
        robot.clickOn("#btnSave");
        DialogPane dialogPane= robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#checkBoxAllDayTask");
        assertFalse(robot.lookup(".dialog-pane").tryQuery().isPresent());
    }

    @Test
    public void reminderIsSet(FxRobot robot){
        robot.clickOn("#checkBoxReminder");
        robot.clickOn("#btnSave");
        //start of the task should be set
        DialogPane dialogPane= robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

    }

    @Test
    public void incorrectStart(FxRobot robot){
        robot.clickOn("#startMins");
        robot.press(KeyCode.UP).release(KeyCode.UP);
        robot.clickOn("#startHour");
        robot.press(KeyCode.UP).release(KeyCode.UP);
        robot.clickOn("#btnSave");
        //time is in the past
        DialogPane dialogPane= robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    public void incorrectEnd(FxRobot robot){
        robot.clickOn("#endMins");
        robot.press(KeyCode.UP).release(KeyCode.UP);
        robot.clickOn("#endHour");
        robot.press(KeyCode.UP).release(KeyCode.UP);
        robot.clickOn("#btnSave");
        //time is set, but date isn't
        DialogPane dialogPane= robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }



}