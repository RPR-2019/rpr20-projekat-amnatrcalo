package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.AlertMessages;
import ba.unsa.etf.rpr.project.enums.ListsName;
import ba.unsa.etf.rpr.project.enums.MyDayMessages;
import ba.unsa.etf.rpr.project.enums.TooltipContent;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.CheckListView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;



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
    public ImageButton btnDeleteTask=new ImageButton(new Image("/img/delete-task.png"), 40, 40);
    public ImageButton btnEditTask=new ImageButton(new Image("/img/edit-list-and-pen.png"), 40, 40);
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
    private final User user;
    private final AppDAO dao;
    private ResourceBundle bundle = ResourceBundle.getBundle("Translation");



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
       if (t.getListName().equals("Completed")) ok=false;
       else if(!t.getReminderDateAndTime().isEqual((LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)))) ok=false;
       else if (!t.getUsername().equals(user.getUsername())) ok=false;
       return ok;
    }

    private void setTooltips(){
        btnEditTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.EDITTASK.toString()));
        btnDeleteTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.DELETETASK.toString()));
        btnRightArrow.setTooltip(TooltipClass.makeTooltip(TooltipContent.COLLAPSEDETAILS.toString()));
        btnAddNewTask.setTooltip(TooltipClass.makeTooltip(TooltipContent.ADDNEWTASK.toString()));
        btnNewList.setTooltip(TooltipClass.makeTooltip(TooltipContent.ADDNEWLIST.toString()));
        btnDeleteList.setTooltip(TooltipClass.makeTooltip(TooltipContent.DELETELIST.toString()));
    }



    @FXML
    public void initialize(){

        //set greeting message
        String capitalizeUsername=user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1);
        if(currentHour<=11) greetingMessage.setText(MyDayMessages.GOODMORNING.toString()+capitalizeUsername+"!");
        else if(currentHour<19) greetingMessage.setText(MyDayMessages.GOODAFTERNOON.toString()+capitalizeUsername+"!");
        else greetingMessage.setText(MyDayMessages.GOODEVENING.toString()+capitalizeUsername+"!");



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

            if(newItem.getListName().equals(ListsName.MYDAY.toString())){
                activeSession = FXCollections.observableArrayList(dao.getTasksForToday(user.getUsername()));
            } else{
                activeSession = FXCollections.observableArrayList(dao.getAllTasksByListName(user.getUsername(), newItem.getListName()));
            }
            tableViewTasks.setItems(activeSession);


        } );

        rightVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.getChildren().add(0,btnEditTask);
        rightVBox.getChildren().add(1,btnDeleteTask);
        rightVBox.getChildren().add(2,btnRightArrow);
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
                    AlertClass.alertERROR(AlertMessages.NOTSELECTED.toString(), " ", "/img/todolist-icon.png");
                    return;
                }
                String listName=task.getListName();
                if(AlertClass.alertCONFIRMATION( AlertMessages.DELETETASKCONFIRMATIONHEADER.toString() +" '"+task.getTaskName()+"'?",
                        AlertMessages.DELETETASKCONFIRMATIONCONTENT.toString(),"/img/todolist-icon.png")){
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
                    AlertClass.alertERROR(AlertMessages.NOTSELECTED.toString(), " ", "/img/todolist-icon.png");
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
                        if(!task.getListName().equals(ListsName.COMPLETED.toString())) {
                            task.setListName(ListsName.COMPLETED.toString());

                        } else{
                            if(TaskController.startDateAndTimeAreSet(task.getStartYear())) task.setListName(ListsName.PLANNED.toString());
                            else task.setListName(ListsName.TASKS.toString());
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"),bundle);
        TaskController taskController=new TaskController(null,user,listLists,false);
        loader.setController(taskController);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addNewTask.setTitle(" ");
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

    private boolean shouldDeleteList(String listName){
        return !listName.equals(ListsName.MYDAY.toString()) && !listName.equals(ListsName.COMPLETED.toString()) && !listName.equals(ListsName.TASKS.toString()) && !listName.equals(ListsName.PLANNED.toString());
    }



    public void actionDeleteList(ActionEvent actionEvent) {
        String selectedListName=listViewLists.getSelectionModel().getSelectedItem().getListName();
        if(selectedListName==null) return;

        if(!shouldDeleteList(selectedListName)){
            AlertClass.alertERROR(AlertMessages.DELETELISTERRORHEADER.toString(), AlertMessages.DELETELISTERRORCONTENT.toString(),"/img/todolist-icon.png");
            return;
        }

        if(AlertClass.alertCONFIRMATION(AlertMessages.DELETETASKCONFIRMATIONHEADER.toString()+selectedListName,AlertMessages.DELETELISTCONFIRMATIONCONTENT.toString(),"/img/todolist-icon.png")){
            dao.deleteList(user.getUsername(),selectedListName);
            listLists.remove(new CustomList(user.getUsername(), selectedListName));
        }

    }

    public void actionMoreDetails (ActionEvent event ){
        DateTimeFormatter formatDate=DateTimeFormatter.ofPattern(MyDayMessages.DATE.toString());
        DateTimeFormatter formatTime=DateTimeFormatter.ofPattern(MyDayMessages.CLOCK.toString());
        Task task = tableViewTasks.getSelectionModel().getSelectedItem();
        if(task==null){
            AlertClass.alertERROR(AlertMessages.NOTSELECTED.toString(), " ", "/img/todolist-icon.png");
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
                text2.setText("");
                text3.setText("");
            }
            if(task.getEndYear()!=1){
                text4.setText(MyDayMessages.TEXTFLOWENDDATE.toString()+task.getEndDateAndTime().format(formatDate)+"\n");
                text5.setText(MyDayMessages.TEXTFLOWENDTIME.toString()+task.getEndDateAndTime().format(formatTime)+"\n");
            } else{
                text4.setText("");
                text5.setText("");
            }

            if(task.isReminder()){
                text6.setText(MyDayMessages.TEXTFLOWREMINDERISSET.toString()+task.getReminderDigit()+" "+ task.getReminderPeriod()+MyDayMessages.TEXTFLOWREMINDERBEFORE.toString());
                if(task.isAlertEmail()) text7.setText(" ("+MyDayMessages.TEXTFLOWEMAILALERT.toString()+")\n");
                else text7.setText(" ("+MyDayMessages.TEXTFLOWNOTIFICATIONALERT.toString()+")\n");
            }else{
                text6.setText("");
                text7.setText("");
            }

            if(!task.getNote().trim().isEmpty()){
                text8.setText("\n"+task.getNote());
            }else{
                text8.setText("");
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




}






