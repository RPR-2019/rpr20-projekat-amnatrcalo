package ba.unsa.etf.rpr.project.controller;

import static org.junit.jupiter.api.Assertions.*;

import ba.unsa.etf.rpr.project.Main;
import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.StageName;
import ba.unsa.etf.rpr.project.maker.NotificationReminder;
import ba.unsa.etf.rpr.project.model.CustomList;
import ba.unsa.etf.rpr.project.model.Task;
import ba.unsa.etf.rpr.project.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
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

@ExtendWith(ApplicationExtension.class)
class DateAndTimeControllerTest {

    Stage theStage;
    AppDAO dao= AppDAO.getInstance();

    @Start
    public void start(Stage stage) throws Exception {
        dao.resetDatabase();
        Task task=new Task(1,"mujo","mujo's task", 1,1,1,1,1,1,1,1,1,1," ",false,-1," ",false,false,"tasks",false);
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dateAndTime.fxml"),ResourceBundle.getBundle("Translation"));
        DateAndTimeController dateAndTimeController = new DateAndTimeController(task);
        loader.setController(dateAndTimeController);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(StageName.DATEANDTIME.toString());
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
        robot.clickOn("#checkBoxAllDayTask");
        robot.clickOn("#btnSave");
        //all day task, but start date is not set
        DialogPane dialogPane= robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
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


}