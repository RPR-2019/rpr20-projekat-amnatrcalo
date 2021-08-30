package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public ChoiceBox<CustomList> listMenu;

    private Task task;
    private User user;
    private AlertClass alertClass=new AlertClass();
    private ObservableList<CustomList> listLists= FXCollections.observableArrayList();

    private AppDAO dao;
    private boolean edit=false;


    public Task getTask() {
        return task;
    }


    @FXML
    public void initialize(){

        if(task!=null){
            fldTaskName.setText(task.getTaskName());
            areaNote.setText(task.getNote());
            for(CustomList l:listLists){
                if(l.getListName().equals(task.getListName())){
                    listMenu.getSelectionModel().select(l);
                }
            }
        }

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

        listMenu.setItems(listLists);

    }

    public TaskController(Task task, User user, ObservableList<CustomList> listLists, boolean edit){
        this.task=task;
        dao=AppDAO.getInstance();
        this.user=user;
        this.listLists=listLists;
        this.edit=edit;
    }

    public boolean startDateAndTimeAreSet (Integer startYear){
        boolean areSet=false;
        if(startYear!=1) areSet=true;
        return areSet;
    }

    public void actionCreate(ActionEvent actionEvent) {
        boolean ok=true;

        if(fldTaskName.getText().trim().isEmpty()){
            ok=false;
            alertClass.alertERROR("Task name field is required",
                    "You didn't enter the name of Your task.");
        }

        if(!edit){
            for(Task t: dao.tasks()){
                if(t.getTaskName().equals(fldTaskName.getText()) && t.getUsername().equals(user.getUsername())){
                    ok=false;
                    alertClass.alertERROR("This name is not approved",
                            "Task with this name already exists");
                }
            }
       } else{
            for(Task t: dao.tasks()){
                if(t.getTaskName().equals(fldTaskName.getText()) && t.getId()!=task.getId() && t.getUsername().equals(user.getUsername())){
                    ok=false;
                    alertClass.alertERROR("This name is not approved",
                            "Task with this name already exists");
                }
            }
        }

        if(!ok) return;


        if(task==null) {
            //ako nisu podesena vremena i datumi
            task=new Task();
            task.setStartYear(1);
            task.setStartMonth(1);
            task.setStartDay(1);
            task.setStartHour(1);
            task.setStartMin(1);
            task.setReminder(false);
            task.setReminderDigit(1);
            task.setReminderPeriod("--");
            task.setEndYear(1);
            task.setEndMonth(1);
            task.setEndDay(1);
            task.setEndHour(1);
            task.setEndMin(1);
        }
        task.setTaskName(fldTaskName.getText());
        task.setUsername(user.getUsername());

        if(areaNote.getText()!=null && !areaNote.getText().trim().isEmpty()) task.setNote(areaNote.getText());
        else task.setNote(" ");

        if(listMenu.getValue()==null) {
            if (startDateAndTimeAreSet(task.getStartYear())) {
                task.setListName("Planned");
            } else {
                task.setListName("Tasks");
            }
        }else{
            task.setListName(listMenu.getValue().getListName());
        }

        Stage stage = (Stage) areaNote.getScene().getWindow();
        stage.close();
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage= (Stage) areaNote.getScene().getWindow();
        stage.close();
    }



    }

