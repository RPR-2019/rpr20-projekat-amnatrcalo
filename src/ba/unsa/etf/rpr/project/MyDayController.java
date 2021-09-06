package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.MyDayMessages;
import ba.unsa.etf.rpr.project.enums.TooltipContent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.IndexedCheckModel;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


//kada se poziva postaviti mu minHeight i minwidth od oka
public class MyDayController {
    public Label greetingMessage;
    public Label date;
    public Label randomQuote;
    public Label quoteAuthor;
    public Label clock;
    public Button btnAddNewTask;
    public Button btnNewList;
    public Button btnDeleteList;
    public ListView<CustomList> listViewLists;
    public CheckListView<Task> tableViewTasks;
    public ObservableList<CustomList> listLists;
    public VBox rightVBox;
    public ImageButton btnDeleteTask=new ImageButton(new Image("/img/delete-task.png"), 36, 36);
    public ImageButton btnEditTask=new ImageButton(new Image("/img/edit-list-and-pen.png"), 36, 36);
    public ImageButton btnRightArrow=new ImageButton(new Image("/img/right_arrow.png"),15,15);
    public Text text1 = new Text();
    public Text text2 = new Text();
    public Text text3 = new Text();
    public Text text4 = new Text();
    public Text text5 = new Text();
    public Text text6 = new Text();
    public Text text7 = new Text();
    public Text text8 = new Text();
    public TextFlow textFlow = new TextFlow(text1, text2, text3, text4, text5,text6,text7,text8);


    private ObservableList<Task> activeSession = FXCollections.observableArrayList();
    private User user;
    private AppDAO dao;
    private AlertClass alertClass=new AlertClass();



    static Timeline timelineInfinite=new Timeline();
    private final int currentHour=LocalDateTime.now().getHour();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat simpleClockFormat=new SimpleDateFormat("HH:mm");



    public MyDayController(User user, ArrayList<CustomList>lists) {
        this.user=user;
        listLists = FXCollections.observableArrayList(lists);
        dao=AppDAO.getInstance();

    }

    private boolean shouldSendNotif(Task t){
        return !t.getListName().equals("Completed");
    }



    @FXML
    public void initialize(){

        //set delete task Btn and edit task Btn
        rightVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.getChildren().add(0,btnEditTask);
        rightVBox.getChildren().add(1,btnDeleteTask);
        rightVBox.getChildren().add(3,btnRightArrow);
        rightVBox.getChildren().add(textFlow);

        btnEditTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.EDITTASK.toString()));
        btnDeleteTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.DELETETASK.toString()));
        btnRightArrow.setTooltip(TooltipClass.makeTooltip(TooltipContent.COLLAPSEDETAILS.toString()));
        btnAddNewTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.ADDNEWTASK.toString()));
        btnNewList.setTooltip(TooltipClass.makeTooltip(TooltipContent.ADDNEWLIST.toString()));
        btnDeleteList.setTooltip(TooltipClass.makeTooltip(TooltipContent.DELETELIST.toString()));

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
                    AlertClass.alertERROR(MyDayMessages.NOTSELECTED.toString(), " ", "/img/todolist-icon.png");
                    return;
                }
                String listName=task.getListName();
                if(AlertClass.alertCONFIRMATION( MyDayMessages.DELETECONFIRMATIONHEADER.toString() +" '"+task.getTaskName()+"'?",
                        MyDayMessages.DELETECONFIRMATIONCONTENT.toString(),"/img/todolist-icon.png")){
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
                    AlertClass.alertERROR(MyDayMessages.NOTSELECTED.toString(), " ", "/img/todolist-icon.png");
                    return;
                }

                Stage editTask=new Stage();
                Parent root=null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"));
                TaskController taskController=new TaskController(task,user,listLists,true);
                loader.setController(taskController);
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                editTask.setTitle("My Day");
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
        Timeline timelineInfinite = new Timeline(new KeyFrame(Duration.millis(1000), e-> {
            for(Task t: dao.getAllTasksAlertNotification(user)){
                if(shouldSendNotif(t) && t.getReminderDateAndTime().isEqual((LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)))){
                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(2000), event2 -> {
                        NotificationReminder.sendNotification(t);
                    }));
                    timeline2.play();
                }
            }

            for(Task t: dao.getAllTasksEmailNotification(user)){
                if(shouldSendNotif(t) && t.getReminderDateAndTime().isEqual((LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)))){
                    Timeline timeline3 = new Timeline(new KeyFrame(Duration.millis(2000), event3 -> {
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


        //set greeting message
       if(currentHour<=11) greetingMessage.setText(MyDayMessages.GOODMORNING.toString()+user.getUsername()+"!");
       else if(currentHour<19) greetingMessage.setText(MyDayMessages.GOODAFTERNOON.toString()+user.getUsername()+"!");
       else greetingMessage.setText(MyDayMessages.GOODEVENING.toString()+user.getUsername()+"!");



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
        //colTaskName.setCellValueFactory(new PropertyValueFactory("taskName"));

        listViewLists.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) ->{
            CustomList oldList=(CustomList) oldItem;
            CustomList newList=(CustomList) newItem;

            if(newItem.getListName().equals("My day")){
                activeSession = FXCollections.observableArrayList(dao.getTasksForToday(user.getUsername()));
            } else{
                activeSession = FXCollections.observableArrayList(dao.getAllTasksByListName(user.getUsername(), newItem.getListName()));
            }
                tableViewTasks.setItems(activeSession);
                //colTaskName.setCellValueFactory(new PropertyValueFactory("taskName"));

        } );



        tableViewTasks.setCellFactory(CheckBoxListCell.forListView(new Callback<Task, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Task task) {
                BooleanProperty observable=new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) ->{
                    if(isNowSelected){
                        if(!task.getListName().equals("Completed")) {
                            task.setListName("Completed");

                        } else{
                            if(TaskController.startDateAndTimeAreSet(task.getStartYear())) task.setListName("Planned");
                            else task.setListName("Tasks");
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


    }


    public void actionAddNewTask(ActionEvent actionEvent) throws IOException {

        Stage addNewTask=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"));
        TaskController taskController=new TaskController(null,user,listLists,false);
        loader.setController(taskController);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addNewTask.setTitle("My Day");
        addNewTask.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/list.fxml"));
        ListController listController=new ListController(user,null);
        loader.setController(listController);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addNewList.setTitle("My List");
        addNewList.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
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



    public void actionDeleteList(ActionEvent actionEvent) {
        String selectedListName=listViewLists.getSelectionModel().getSelectedItem().getListName();
        if(selectedListName==null) return;

        if(selectedListName.equals("Tasks") || selectedListName.equals("My day")||selectedListName.equals("Planned") ||selectedListName.equals("Completed")){
            alertClass.alertERROR("This list can't be deleted", "Lists: 'My day', 'Tasks', 'Planned'" +
                    " and 'Completed' can't be deleted.","/img/login-icon.png");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete list");
        alert.setHeaderText("By deleting this list, all tasks from it will be deleted, too.");
        alert.setContentText("Are you sure?");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeOne) {
                dao.deleteTasksFromList(user.getUsername(),selectedListName);
                dao.deleteList(user.getUsername(),selectedListName);
                listLists.remove(new CustomList(user.getUsername(), selectedListName));
            }



    }

    public void actionMoreDetails (ActionEvent event ){
        DateTimeFormatter formatDate=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatTime=DateTimeFormatter.ofPattern("HH:mm");
        Task task = tableViewTasks.getSelectionModel().getSelectedItem();
        if(task==null){
            AlertClass.alertERROR(MyDayMessages.NOTSELECTED.toString(), " ", "/img/todolist-icon.png");
        } else{
            rightVBox.setPrefWidth(300);
            text1.setText(task.getTaskName() + " ("+task.getListName()+")\n\n");

            if(task.getStartYear()!=1){
                text2.setText(MyDayMessages.TEXTFLOWSTARTDATE.toString()+task.getStartDateAndTime().format(formatDate)+"\n");
                if(task.isAllDay()){
                    text3.setText(MyDayMessages.TEXTFLOWALLDAYTASK.toString()+"\n");
                } else{
                    text3.setText(MyDayMessages.TEXTFLOWSTARTTIME.toString()+task.getStartDateAndTime().format(formatTime)+"\n");
                }
            } else{
                text2.setText("\n");
                text3.setText("\n");
            }
            if(task.getEndYear()!=1){
                text4.setText(MyDayMessages.TEXTFLOWENDDATE.toString()+task.getEndDateAndTime().format(formatDate)+"\n");
                text5.setText(MyDayMessages.TEXTFLOWENDTIME.toString()+task.getEndDateAndTime().format(formatTime)+"\n");
            } else{
                text4.setText("\n");
                text5.setText("\n");
            }

            if(task.isReminder()){
                text6.setText(MyDayMessages.TEXTFLOWREMINDERISSET.toString()+task.getReminderDigit()+" "+ task.getReminderPeriod()+MyDayMessages.TEXTFLOWREMINDERBEFORE.toString());
                if(task.isAlertEmail()) text7.setText(" ("+MyDayMessages.TEXTFLOWEMAILALERT.toString()+")\n");
                else text7.setText(" ("+MyDayMessages.TEXTFLOWNOTIFICATIONALERT.toString()+")\n");
            }else{
                text6.setText("\n");
                text7.setText("\n");
            }

            if(!task.getNote().trim().isEmpty()){
                text8.setText("\n"+task.getNote());
            }else{
                text8.setText(" ");
            }

            styleTextFlow(false);

        }
    }

    private void styleTextFlow(boolean collapse){
        if(collapse){
            rightVBox.setPrefWidth(200);
            text1.setText(" ");
            text2.setText(" ");
            text3.setText(" ");
            text4.setText(" ");
            text5.setText(" ");
            text6.setText(" ");
            text7.setText(" ");
            text8.setText(" ");
        } else{
            textFlow.setLineSpacing(1.5);
            textFlow.setStyle(" -fx-padding: 20px; -fx-margin: 10px;");
            text1.setStyle("-fx-font-size: 16; -fx-fill: darkred; -fx-font-weight:bold;");
            text2.setStyle("-fx-font-size: 14; -fx-fill: goldenrod;");
            text3.setStyle("-fx-font-size: 14; -fx-fill: goldenrod;");
            text4.setStyle("-fx-font-size: 14; -fx-fill: goldenrod;");
            text5.setStyle("-fx-font-size: 14; -fx-fill: goldenrod;");
            text6.setStyle("-fx-font-size: 14; -fx-fill: goldenrod;");
            text7.setStyle("-fx-font-size: 14; -fx-fill: goldenrod;");
            text8.setStyle("-fx-font-size: 14; -fx-fill: goldenrod;");
        }

    }





    public boolean sameStart(Task task){
        boolean same=false;
        for(Task t: dao.tasks()){
            if(t.getStartDateAndTime().isEqual(task.getStartDateAndTime())) same=true;
        }
        return same;
    }
}






