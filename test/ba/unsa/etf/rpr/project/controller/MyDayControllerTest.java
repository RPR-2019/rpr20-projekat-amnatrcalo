package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.maker.NotificationReminder;
import ba.unsa.etf.rpr.project.model.Task;
import ba.unsa.etf.rpr.project.model.User;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class MyDayControllerTest {

    AppDAO dao= AppDAO.getInstance();
    @Start
    public void start(Stage stage) throws Exception {
        dao.resetDatabase();
        User user=dao.getUser("amna");
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/myDay.fxml"),ResourceBundle.getBundle("Translation"));
        MyDayController myDayController = new MyDayController(user,dao.lists(user));
        loader.setController(myDayController);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stage.setTitle("ToDo App");
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, 620));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
        stage.getIcons().add(icon);
        stage.setResizable(true);
        stage.setMinHeight(760);
        stage.setMinWidth(760);
        String css = NotificationReminder.class.getResource("/css/notificationpopup.css").toExternalForm();
        stage.getScene().getStylesheets().add(0,css);
        stage.show();
        stage.toFront();
    }

    @BeforeEach @AfterEach
    public void resetDatabase() throws SQLException {
        dao.resetDatabase();
    }

    @Test
    public void deleteDefaultList(FxRobot robot){
        robot.clickOn("#listViewLists");
        robot.clickOn("Planirano");
        robot.clickOn("#btnDeleteList");
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
    }

    @Test
    public void deleteUsersList(FxRobot robot){
        robot.clickOn("#btnNewList");
        robot.clickOn("#fldListName");
        robot.write("New list");
        robot.clickOn("#btnSave");

        robot.clickOn("#listViewLists");
        robot.scroll(4,VerticalDirection.DOWN);
        robot.clickOn("New list");
        robot.clickOn("#btnDeleteList");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.lookup("#listViewLists");
        boolean deleted=true;
        if(robot.lookup("New list").tryQuery().isPresent()) deleted=false;
        assertTrue(deleted);
    }

    @Test
    public void taskIsCreated(FxRobot robot){
        robot.clickOn("#btnAddNewTask");
        robot.clickOn("#fldTaskName");
        robot.write("Ha ha");
        robot.clickOn("#btnCreate");

        assertEquals(1,dao.tasks().size());
    }

    @Test
    public void taskWithTheSameName(FxRobot robot){
        robot.clickOn("#btnAddNewTask");
        robot.clickOn("#fldTaskName");
        robot.write("same name");
        robot.clickOn("#btnCreate");

        robot.clickOn("#btnAddNewTask");
        robot.clickOn("#fldTaskName");
        robot.write("same name");
        robot.clickOn("#btnCreate");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        robot.clickOn("#fldTaskName");
        robot.write("!!");
        robot.clickOn("#btnCreate");

        assertEquals(2,dao.tasks().size());

    }

    @Test
    public void deleteTask(FxRobot robot){
        robot.clickOn("#btnAddNewTask");
        robot.clickOn("#fldTaskName");
        robot.write("Delete this task");
        robot.clickOn("#btnCreate");

        assertEquals(1,dao.tasks().size());

        robot.press(KeyCode.RIGHT).release(KeyCode.RIGHT).press(KeyCode.ENTER).release(KeyCode.ENTER)
                .press(KeyCode.RIGHT).release(KeyCode.RIGHT)
                .press(KeyCode.DOWN).release(KeyCode.DOWN)
                .press(KeyCode.DOWN).release(KeyCode.DOWN)
                .press(KeyCode.DOWN).release(KeyCode.DOWN)
                .press(KeyCode.ENTER).release(KeyCode.ENTER);

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        assertEquals(0,dao.tasks().size());

    }

    @Test
    public void editTask(FxRobot robot){
        robot.clickOn("#btnAddNewTask");
        robot.clickOn("#fldTaskName");
        robot.write("Hello");
        robot.clickOn("#btnCreate");


        robot.press(KeyCode.RIGHT).release(KeyCode.RIGHT).press(KeyCode.ENTER).release(KeyCode.ENTER)
                .press(KeyCode.RIGHT).release(KeyCode.RIGHT)
                .press(KeyCode.DOWN).release(KeyCode.DOWN)
                .press(KeyCode.DOWN).release(KeyCode.DOWN)
                .press(KeyCode.ENTER).release(KeyCode.ENTER);

        robot.clickOn("#fldTaskName");
        robot.write("ooo");
        robot.clickOn("#btnCreate");
        boolean edit=false;
        for(Task t: dao.tasks()){
            if(t.getTaskName().equals("Helloooo")) edit=true;
            break;
        }

        assertTrue(edit);

    }

    @Test
    public void enterCreate(FxRobot robot){
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        assertTrue(robot.lookup("#fldTaskName").tryQuery().isPresent());
    }




}