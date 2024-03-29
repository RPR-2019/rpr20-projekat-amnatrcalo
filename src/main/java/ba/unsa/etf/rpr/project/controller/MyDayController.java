package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.StageName;
import ba.unsa.etf.rpr.project.enums.alertText.MyDayAlertMessages;
import ba.unsa.etf.rpr.project.enums.content.MyDayMessages;
import ba.unsa.etf.rpr.project.enums.content.TooltipContent;
import ba.unsa.etf.rpr.project.maker.*;
import ba.unsa.etf.rpr.project.model.CustomList;
import ba.unsa.etf.rpr.project.model.Task;
import ba.unsa.etf.rpr.project.model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.CheckListView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyDayController {
    public Label greetingMessage;
    public Label date;
    public Label randomQuote;
    public Label quoteAuthor;
    public Label clock;
    public Button btnAddNewTask;
    public Button btnNewList;
    public Button btnDeleteList;
    public CheckMenuItem checkMenuItem;
    public ListView<CustomList> listViewLists;
    public CheckListView<Task> tableViewTasks;
    public ObservableList<CustomList> listLists;
    public VBox rightVBox;
    public ImageButton btnDeleteTask=new ImageButton(new Image("/img/delete-task.png"), 40, 40);
    public ImageButton btnEditTask=new ImageButton(new Image("/img/edit-list-and-pen.png"), 40, 40);
    public ImageButton btnRightArrow=new ImageButton(new Image("/img/right_arrow.png"),15,15);
    public Text text1 = new Text();

    public TextFlow textFlow = new TextFlow(text1);


    private ObservableList<Task> activeSession = FXCollections.observableArrayList();
    private final User user;
    private final AppDAO dao;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");
    private boolean sendNotification=true;


    static Timeline timelineInfinite=new Timeline();
    static Timeline timeline2=new Timeline();
    static Timeline timeline3=new Timeline();
    private final int currentHour=LocalDateTime.now().getHour();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MyDayMessages.DATE.toString());
    SimpleDateFormat simpleClockFormat=new SimpleDateFormat(MyDayMessages.CLOCK.toString());



    public MyDayController(User user, ArrayList<CustomList>lists) {
        this.user=user;
        listLists = FXCollections.observableArrayList(lists);
        dao=AppDAO.getInstance();
    }



    private boolean shouldSendNotif(Task t){
        boolean ok=true;
        if(t.getListName().equals("Completed")) ok=false;
       else if(!t.getReminderDateAndTime().isEqual((LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)))) ok=false;
       else if (!t.getUsername().equals(user.getUsername())) ok=false;
       else if (!sendNotification) ok=false;
       return ok;
    }

    private void setTooltips(){
        btnEditTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.EDIT_TASK.toString()));
        btnDeleteTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.DELETE_TASK.toString()));
        btnRightArrow.setTooltip(TooltipClass.makeTooltip(TooltipContent.COLLAPSE_DETAILS.toString()));
        btnAddNewTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.ADD_NEW_TASK.toString()));
        btnNewList.setTooltip(TooltipClass.makeTooltip(TooltipContent.ADD_NEW_LIST.toString()));
        btnDeleteList.setTooltip(TooltipClass.makeTooltip(TooltipContent.DELETE_LIST.toString()));
    }



    @FXML
    public void initialize(){

        //receive notification
        checkMenuItem.setSelected(true);

        //set greeting message
        String capitalizeUsername=user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1);
        if(currentHour<=11) greetingMessage.setText(MyDayMessages.GOOD_MORNING.toString()+capitalizeUsername+"!");
        else if(currentHour<19) greetingMessage.setText(MyDayMessages.GOOD_AFTERNOON.toString()+capitalizeUsername+"!");
        else greetingMessage.setText(MyDayMessages.GOOD_EVENING.toString()+capitalizeUsername+"!");



        //set qoute
        Random rand=new Random();
        int upperbound=dao.quotes().size();
        int randIndex=rand.nextInt(upperbound);
        randomQuote.setText(dao.quotes().get(randIndex).getContent());
        quoteAuthor.setText(dao.quotes().get(randIndex).getAuthor());

        //set date and time
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            final Calendar cal = Calendar.getInstance();
            date.setText(simpleDateFormat.format(cal.getTime()));
            clock.setText(simpleClockFormat.format(cal.getTime()));

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        listViewLists.setItems(listLists);

        listViewLists.getSelectionModel().select(0);
        activeSession = FXCollections.observableArrayList(dao.getTasksForToday(user.getUsername()));
        tableViewTasks.setItems(activeSession);

        listViewLists.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) ->{
            if(newItem!=null) {
                if(newItem.getListName().equals("My Day")){
                    activeSession = FXCollections.observableArrayList(dao.getTasksForToday(user.getUsername()));
                } else {
                    activeSession = FXCollections.observableArrayList(dao.getAllTasksByListName(user.getUsername(), newItem.getListName()));
                }
            }
            tableViewTasks.setItems(activeSession);



        } );

        rightVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.getChildren().add(btnRightArrow);
        rightVBox.getChildren().add(btnEditTask);
        rightVBox.getChildren().add(btnDeleteTask);
        rightVBox.getChildren().add(textFlow);

        setTooltips();


        btnRightArrow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                styleTextFlow(true);
            }
        });


        btnDeleteTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Task task = tableViewTasks.getSelectionModel().getSelectedItem();
                if(task==null) {
                    AlertClass.alertERROR(MyDayAlertMessages.NOT_SELECTED.toString(), " ", "/img/todolist-icon.png");
                    return;
                }
                String listName=task.getListName();
                if(AlertClass.alertCONFIRMATION( MyDayAlertMessages.DELETE_TASK_CONFIRMATION_HEADER.toString() +" '"+task.getTaskName()+"'?",
                        MyDayAlertMessages.DELETE_TASK_CONFIRMATION_CONTENT.toString(),"/img/thinking-face-icon.png")){
                    dao.deleteTask(task);
                    activeSession.setAll(dao.getAllTasksByListName(user.getUsername(),listName));
                }
            }
        });

        btnEditTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Task task = tableViewTasks.getSelectionModel().getSelectedItem();
                if(task==null){
                    AlertClass.alertERROR(MyDayAlertMessages.NOT_SELECTED.toString(), " ", "/img/road-sign-icon.png");
                    return;
                }

                Stage editTask=new Stage();
                Parent root=null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"),bundle);
                TaskController taskController=new TaskController(task,user,listLists,true);
                loader.setController(taskController);
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                editTask.setTitle(" ");
                editTask.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
                Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
                editTask.getIcons().add(icon);
                editTask.setResizable(false);
                editTask.show();

                editTask.setOnHiding( event -> {
                    Task newTask = taskController.getTask();
                    if (newTask != null) {
                        dao.editTask(newTask);
                        listViewLists.getSelectionModel().select(new CustomList(user.getUsername(), newTask.getListName()));
                        activeSession.setAll(dao.getAllTasksByListName(user.getUsername(), newTask.getListName()));
                    }
                } );
            }
        });

        //send notification
         timelineInfinite = new Timeline(new KeyFrame(Duration.millis(1000), e-> {
            for(Task t: dao.getAllTasksAlertNotification(user)){
                if(shouldSendNotif(t)){
                     timeline2 = new Timeline(new KeyFrame(Duration.millis(2000), event2 -> {
                        NotificationReminder.sendNotification(t);
                    }));
                    timeline2.play();
                }
            }

            for(Task t: dao.getAllTasksEmailNotification(user)){
                if(shouldSendNotif(t)){
                    timeline3 = new Timeline(new KeyFrame(Duration.millis(2000), event3 -> {
                        try {
                            MailClass.sendMail(t,user);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }));
                    timeline3.play();

                }
            }
        }));
        timelineInfinite.setCycleCount(Timeline.INDEFINITE);
        timelineInfinite.play();



        //when checkbox is selected, mark task as completed
        tableViewTasks.setCellFactory(CheckBoxListCell.forListView(new Callback<Task, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(Task task) {
                BooleanProperty observable=new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) ->{
                    if(isNowSelected){
                        if(!task.getListName().equals("Completed")){
                            task.setListName("Completed");

                        } else{
                            if(TaskController.startDateAndTimeAreSet(task.getStartYear()))
                                task.setListName("Planned");
                            else
                                task.setListName("Tasks");
                        }
                        dao.editTask(task);
                        listViewLists.getSelectionModel().select(new CustomList(user.getUsername(), task.getListName()));
                        activeSession.setAll(dao.getAllTasksByListName(user.getUsername(), task.getListName()));
                    }
                }


                );
                return observable ;
            }
        }));

        //when ENTER show task details

        tableViewTasks.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    if(tableViewTasks.getSelectionModel().getSelectedItem()==null) return;
                    text1.setText(tableViewTasks.getSelectionModel().getSelectedItem().getAllDetails());
                    styleTextFlow(false);
                }
            }
        });

    }


    public void actionAddNewTask(ActionEvent actionEvent) throws IOException {

        Stage addNewTask=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"),bundle);
        TaskController taskController=new TaskController(null,user,listLists,false);
        loader.setController(taskController);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addNewTask.setTitle(StageName.YOUR_TASK.toString());
        addNewTask.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/todolist-icon.png"));
        addNewTask.getIcons().add(icon);
        addNewTask.setResizable(false);
        addNewTask.show();

        addNewTask.setOnHiding( event -> {
            Task newTask= taskController.getTask();
            if (newTask != null && newTask.getUsername()!=null) {
                dao.addTask(newTask);
                listViewLists.getSelectionModel().select(new CustomList(user.getUsername(), newTask.getListName()));
                activeSession.setAll(dao.getAllTasksByListName(user.getUsername(), newTask.getListName()));
            }

        } );

    }


    public void actionNewList(ActionEvent actionEvent) {
        Stage addNewList=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/list.fxml"),bundle);
        ListController listController=new ListController(user,null);
        loader.setController(listController);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addNewList.setTitle(" ");
        addNewList.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/todolist-icon.png"));
        addNewList.getIcons().add(icon);
        addNewList.setResizable(false);
        addNewList.show();

        addNewList.setOnHiding( event -> {
            CustomList newList= listController.getList();
            if (newList != null) {
                dao.addList(user.getUsername(),newList.getListName());
                listLists.add(newList);
            }

        } );


    }

    private boolean shouldDeleteList(String listName){
        return !listName.equals("My Day") && !listName.equals("Completed") && !listName.equals("Tasks") && !listName.equals("Planned");

    }



    public void actionDeleteList(ActionEvent actionEvent) {
        String selectedListName=listViewLists.getSelectionModel().getSelectedItem().getListName();
        if(selectedListName==null) return;

        if(!shouldDeleteList(selectedListName)){
            AlertClass.alertERROR(MyDayAlertMessages.DELETE_LIST_ERROR_HEADER.toString(), MyDayAlertMessages.DELETE_LIST_ERROR_CONTENT.toString(),"/img/road-sign-icon.png");
            return;
        }

        if(AlertClass.alertCONFIRMATION(MyDayAlertMessages.DELETE_TASK_CONFIRMATION_HEADER.toString()+"'"+selectedListName+"'?", MyDayAlertMessages.DELETE_LIST_CONFIRMATION_CONTENT.toString(),"/img/thinking-face-icon.png")){
            dao.deleteList(user.getUsername(),selectedListName);
            listLists.remove(new CustomList(user.getUsername(), selectedListName));
        }

    }

    public void actionMoreDetails (ActionEvent event ){
            if(tableViewTasks.getSelectionModel().getSelectedItem()==null) return;
            text1.setText(tableViewTasks.getSelectionModel().getSelectedItem().getAllDetails());
            styleTextFlow(false);
    }

    private void styleTextFlow(boolean collapse){
        if(collapse){
            rightVBox.setPrefWidth(200);
           text1.setText(" ");

        } else{
            rightVBox.setPrefWidth(300);
            textFlow.setLineSpacing(1.5);
            textFlow.setStyle(" -fx-padding: 20px; -fx-margin: 10px;");
            text1.setStyle("-fx-font-size: 16; -fx-fill: darkred; -fx-font-weight:bold;");

        }

    }

    public void actionClose (ActionEvent actionEvent){
        Stage stage=(Stage) btnNewList.getScene().getWindow();
        stage.close();
    }

    private String exportTasks(){
        StringBuilder sb=new StringBuilder();
        for(Task t: dao.allTasksForUser(user)){
            sb.append(t.getAllDetails()).append("\n\n");
        }
        return sb.toString();
    }

    public void actionSave(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(btnAddNewTask.getScene().getWindow());

        if (file != null) {
            saveTextToFile(exportTasks(), file);
        }
    }


    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(MyDayController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actionAbout(ActionEvent actionEvent) throws IOException {
        Stage aboutStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"),bundle);
        AboutController aboutController=new AboutController();
        loader.setController(aboutController);
        root = loader.load();

        aboutStage.setTitle(StageName.ABOUT.toString());
        aboutStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
        aboutStage.getIcons().add(icon);
        aboutStage.setResizable(false);
        aboutStage.show();
    }

    public void actionHelp(ActionEvent actionEvent) throws IOException {
        Stage helpStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/helpTask.fxml"),bundle);
        HelpTaskController helpController=new HelpTaskController();
        loader.setController(helpController);
        root = loader.load();

        helpStage.setTitle(StageName.HELP.toString());
        helpStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
        helpStage.getIcons().add(icon);
        helpStage.setResizable(false);
        helpStage.show();
    }

    public void actionEdit(ActionEvent actionEvent) throws IOException {
        Stage editStage=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editProfile.fxml"),bundle);
        EditProfileController editProfileController=new EditProfileController(user, dao.users(), dao);
        loader.setController(editProfileController);
        root = loader.load();

        editStage.setTitle(StageName.MY_PROFILE.toString());
        editStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/login-icon.png"));
        editStage.getIcons().add(icon);
        editStage.setResizable(false);
        editStage.show();
    }

    public void actionEnglish(ActionEvent actionEvent)  {
        Locale.setDefault(new Locale("en","US"));

        Stage stage=(Stage) btnAddNewTask.getScene().getWindow();
        stage.close();
    }

    public void actionBosnian(ActionEvent actionEvent)  {
        Locale.setDefault(new Locale("bs","BA"));

        Stage stage=(Stage) btnAddNewTask.getScene().getWindow();
        stage.close();
    }

    public void actionCheckMenu(ActionEvent actionEvent){
        sendNotification=!sendNotification;
    }



}






