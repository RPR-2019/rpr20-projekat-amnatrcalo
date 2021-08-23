package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class TaskController {

    public ImageButton btnAddDateAndTime = new ImageButton(new Image("/img/date-and-time-icon.png"), 16, 16);
    public GridPane gridPane;
    public TextField fldTaskName;
    public TextArea areaNote;
    public Button btnCreate;
    public ChoiceBox<String> listMenu;

    private Task task;
    private User user;

    @FXML
    public void initialize(){

        gridPane.add(btnAddDateAndTime,0,2);
        Tooltip hoverBtnDateAndTime=new Tooltip("Set date and time of start or end of the task. This is optional.");
        hoverBtnDateAndTime.setStyle("-fx-background-color: yellow; -fx-text-fill: red;");
        hoverBtnDateAndTime.setShowDelay(Duration.millis(100));

        btnAddDateAndTime.setTooltip(hoverBtnDateAndTime);

        btnAddDateAndTime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage addDateAndTimeStage=new Stage();
                Parent root=null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dateAndTime.fxml"));
                DateAndTimeController dateAndTimeController = new DateAndTimeController(task);
                loader.setController(dateAndTimeController);
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                addDateAndTimeStage.setTitle("Add Date and Time");
                addDateAndTimeStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
                Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
                addDateAndTimeStage.getIcons().add(icon);
                addDateAndTimeStage.setResizable(false);
                addDateAndTimeStage.show();

                addDateAndTimeStage.setOnHiding( event -> {
                    task = dateAndTimeController.getTask();

                } );

            }


        });

    }

    public TaskController(User user){
        this.user=user;
    }

    public void actionCreate(ActionEvent actionEvent) {
        task.setTaskName(fldTaskName.getText());
        task.setUsername(user.getUsername());
        task.setNote(areaNote.getText());
        task.setListName(listMenu.getValue());
        //ovdje ga dodajem u bazu

    }
}
