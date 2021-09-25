package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.StageName;
import ba.unsa.etf.rpr.project.model.CustomList;
import ba.unsa.etf.rpr.project.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
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
class TaskControllerTest {

    Stage theStage;
    AppDAO dao= AppDAO.getInstance();


    @Start
    public void start(Stage stage) throws Exception {
        dao.resetDatabase();
        User user=dao.getUser("amna");
        ObservableList<CustomList> listLists= FXCollections.observableArrayList(dao.lists(user));
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"),ResourceBundle.getBundle("Translation"));
        TaskController taskController=new TaskController(null,user,listLists ,false);
        loader.setController(taskController);
        root = loader.load();

        stage.setTitle(StageName.YOUR_TASK.toString());
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/todolist-icon.png"));
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
    public void emptyTaskName(FxRobot robot) throws InterruptedException {

       robot.clickOn("#fldTaskName");
       robot.write(" ");
       robot.clickOn("#btnCreate");
        DialogPane dialogPane= robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

    }

}