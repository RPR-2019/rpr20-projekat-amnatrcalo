package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.ListsName;
import ba.unsa.etf.rpr.project.enums.TooltipContent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

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
    final int MAX_CHARS = 100 ;

    public Task getTask() {
        return task;
    }


    @FXML
    public void initialize(){
        listMenu.setTooltip(TooltipClass.makeTooltip(TooltipContent.CHOOSELIST.toString()));
       areaNote.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS ? change : null));



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


        btnAddDateAndTime.setTooltip(TooltipClass.makeTooltip(TooltipContent.SETDATEANDTIME.toString()));

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
        ObservableList<CustomList> listsForMenu=listLists.stream().filter(l->
          !l.getListName().equals(ListsName.MYDAY.toString()) &&  !l.getListName().equals(ListsName.PLANNED.toString()) && !l.getListName().equals(ListsName.TASKS.toString()) && !l.getListName().equals(ListsName.COMPLETED.toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        //listMenu.setItems(listLists);
        listMenu.setItems(listsForMenu);
    }

    public TaskController(Task task, User user, ObservableList<CustomList> listLists, boolean edit){
        this.task=task;
        dao=AppDAO.getInstance();
        this.user=user;
        this.listLists=listLists;
        this.edit=edit;



    }

    public static boolean startDateAndTimeAreSet (Integer startYear){
        boolean areSet=false;
        if(startYear!=1) areSet=true;
        return areSet;
    }

    public void actionCreate(ActionEvent actionEvent) {
        boolean ok=true;

        if(fldTaskName.getText().trim().isEmpty()){
            ok=false;
            alertClass.alertERROR("Task name field is required",
                    "You didn't enter the name of Your task.","/img/login-icon.png");
        }

        if(!edit){
            for(Task t: dao.tasks()){
                if(t.getTaskName().equals(fldTaskName.getText()) && t.getUsername().equals(user.getUsername())){
                    ok=false;
                    alertClass.alertERROR("This name is not approved",
                            "Task with this name already exists","/img/login-icon.png");
                }
            }
       } else{
            for(Task t: dao.tasks()){
                if(t.getTaskName().equals(fldTaskName.getText()) && t.getId()!=task.getId() && t.getUsername().equals(user.getUsername())){
                    ok=false;
                    alertClass.alertERROR("This name is not approved",
                            "Task with this name already exists","/img/login-icon.png");
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
            task.setReminderDigit(-1);
            task.setReminderPeriod("--");
            task.setEndYear(1);
            task.setEndMonth(1);
            task.setEndDay(1);
            task.setEndHour(1);
            task.setEndMin(1);
            task.setAllDay(false);
        }
        task.setTaskName(fldTaskName.getText());
        task.setUsername(user.getUsername());

        if(areaNote.getText()!=null && !areaNote.getText().trim().isEmpty()) task.setNote(areaNote.getText());
        else task.setNote(" ");

        if(listMenu.getValue()==null) {
            if (startDateAndTimeAreSet(task.getStartYear())) {
                task.setListName(ListsName.PLANNED.toString());
            } else {
                task.setListName(ListsName.TASKS.toString());
            }
        }else{
            task.setListName(listMenu.getValue().getListName());
        }

        if(isOverlaping(task)){
            if(alertClass.alertCONFIRMATION( "This task overlaps with another task.", "Are You ok with this?", "/img/todolist-icon.png")){
                Stage stage = (Stage) areaNote.getScene().getWindow();
                stage.close();
            }

        }

        if(!isOverlaping(task)){
            Stage stage = (Stage) areaNote.getScene().getWindow();
            stage.close();
        }



    }

    public void actionCancel(ActionEvent actionEvent)  {

        Stage stage= (Stage) areaNote.getScene().getWindow();
        stage.close();
    }

    public boolean isOverlaping(Task task){
        boolean overlap=false;
        for(Task t: dao.allTasksForUser(user)){
            if(t.getStartDateAndTime().isEqual(task.getStartDateAndTime())) overlap=true;
            else if(t.getStartDateAndTime().isBefore(task.getStartDateAndTime()) && t.getEndDateAndTime().isAfter(task.getStartDateAndTime())) overlap=true;
        }
        return overlap;
    }

    public void actionAddFile(ActionEvent actionEvent){
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Izaberite datoteku: ");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tekstualne " +
                "datoteke", "*.txt"));

        File file= chooser.showOpenDialog(areaNote.getScene().getWindow());
        if(file==null) return;
        try {
            String text= new String(Files.readAllBytes(file.toPath()));
            areaNote.setText(text);
            if(text.length()>=100) {
                //ne moze puno znakova
            }
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ne mogu učitati datoteku");
            alert.setHeaderText("Došlo je do greške prilikom čitanja "+file.getName());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    }

