package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.*;
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


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TaskController {

    public ImageButton btnAddDateAndTime = new ImageButton(new Image("/img/clock-icon.jpg"), 36, 36);
    public GridPane gridPane;
    public TextField fldTaskName;
    public TextArea areaNote;
    public ChoiceBox<CustomList> listMenu;

    private Task task;
    private final User user;

    private ObservableList<CustomList> listLists= FXCollections.observableArrayList();

    private final AppDAO dao;
    private final boolean edit;
    final int MAX_CHARS = 100 ;
    private final ResourceBundle bundle = ResourceBundle.getBundle("Translation");

    public Task getTask() {
        return task;
    }


    @FXML
    public void initialize(){

        listMenu.setTooltip(TooltipClass.makeTooltip(TooltipContent.CHOOSELIST.toString()));

        //set max number of chars in textArea
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dateAndTime.fxml"),bundle);
                DateAndTimeController dateAndTimeController = new DateAndTimeController(task);
                loader.setController(dateAndTimeController);
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                addDateAndTimeStage.setTitle(StageName.DATEANDTIME.toString());
                addDateAndTimeStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
                Image icon=new Image(getClass().getResourceAsStream("/img/date-and-time-icon.png"));
                addDateAndTimeStage.getIcons().add(icon);
                addDateAndTimeStage.setResizable(false);
                addDateAndTimeStage.show();

                addDateAndTimeStage.setOnHiding( event -> {
                    task = dateAndTimeController.getTask();

                } );

            }


        });

        //user can't choose list name from default names-only user's lists will be shown
        ObservableList<CustomList> listsForMenu=listLists.stream().filter(l->
          !l.getListName().equals(ListsName.MYDAY.toString()) &&  !l.getListName().equals(ListsName.PLANNED.toString()) && !l.getListName().equals(ListsName.TASKS.toString()) && !l.getListName().equals(ListsName.COMPLETED.toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

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
            AlertClass.alertERROR(TaskAlertMessages.TASKNAMEREQUIREDHEADER.toString(),
                    TaskAlertMessages.TASKNAMEREQUIREDCONTENT.toString(),"/img/road-sign-icon.png");
        }

        if(!edit){
            for(Task t: dao.tasks()){
                if(t.getTaskName().equals(fldTaskName.getText()) && t.getUsername().equals(user.getUsername())){
                    ok=false;
                   AlertClass.alertERROR(TaskAlertMessages.NAMENOTAPPROVEDHEADER.toString(),
                            TaskAlertMessages.NAMENOTAPPROVEDCONTENT.toString(),"/img/road-sign-icon.png");
                }
            }
       } else{
            //don't show alert if edit name is the same as original name
            for(Task t: dao.tasks()){
                if(t.getTaskName().equals(fldTaskName.getText()) && t.getId()!=task.getId() && t.getUsername().equals(user.getUsername())){
                    ok=false;
                    AlertClass.alertERROR(TaskAlertMessages.NAMENOTAPPROVEDHEADER.toString(),
                            TaskAlertMessages.NAMENOTAPPROVEDCONTENT.toString(),"/img/road-sign-icon.png");
                }
            }
        }

        if(!ok) return;


        if(task==null) {
            //if date and time is not set
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

        if(listMenu.getValue()==null || isListNameDefault(listMenu.getValue().getListName())) {
            if (startDateAndTimeAreSet(task.getStartYear())) {
                task.setListName(ListsName.PLANNED.toString());
            } else {
                task.setListName(ListsName.TASKS.toString());
            }
        }else{
            task.setListName(listMenu.getValue().getListName());
        }

        if(isOverlaping(task)){
            if(AlertClass.alertCONFIRMATION( TaskAlertMessages.TASKS_OVERLAPPIN_GHEADER.toString(), TaskAlertMessages.TASKS_OVERLAPPING_CONTENT.toString(), "/img/thinking-face-icon.png")){
                Stage stage = (Stage) areaNote.getScene().getWindow();
                stage.close();
            }

        }

        if(!isOverlaping(task)){
            Stage stage = (Stage) areaNote.getScene().getWindow();
            stage.close();
        }



    }

    private boolean isListNameDefault(String listName){
        boolean defaultName=false;
        for(String s: ListsName.defaultListsName()){
            if (listName.equals(s)) {
                defaultName = true;
                break;
            }
        }
        return defaultName;
    }

    private boolean isOverlaping(Task task){
        boolean overlap=false;
        for(Task t: dao.allTasksForUser(user)){
            if(startDateAndTimeAreSet(task.getStartYear()) && t.getId()!=task.getId()){
                if(t.getStartDateAndTime().isEqual(task.getStartDateAndTime())) overlap=true;
                else if(t.getStartDateAndTime().isBefore(task.getStartDateAndTime()) && t.getEndDateAndTime().isAfter(task.getStartDateAndTime())) overlap=true;
            }
        }
        return overlap;
    }

    public void actionCancel(ActionEvent actionEvent)  {

        Stage stage= (Stage) areaNote.getScene().getWindow();
        stage.close();
    }


    public void actionAddFile(ActionEvent actionEvent){
        FileChooser chooser=new FileChooser();
        chooser.setTitle(TaskMessages.CHOOSE_FILE.toString());
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(TaskMessages.DATA_TYPE.toString(), "*.txt"));

        File file= chooser.showOpenDialog(areaNote.getScene().getWindow());
        if(file==null) return;
        try {
            String text= new String(Files.readAllBytes(file.toPath()));
            areaNote.setText(text);
            if(text.length()>100) {
               AlertClass.alertERROR(TaskAlertMessages.TEXTLENGTHERRORHEADER.toString(),TaskAlertMessages.TEXTLENGTHERRORCONTENT.toString(),"/img/road-sign-icon.png");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            AlertClass.alertERROR(" ",e.getMessage(),"/img/road-sign-icon.png");

        }

    }


    }

