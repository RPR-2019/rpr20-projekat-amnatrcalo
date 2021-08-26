package ba.unsa.etf.rpr.project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.controlsfx.control.Notifications;

import javax.management.Notification;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.Random;


//kada se poziva postaviti mu minHeight i minwidth od oka
public class MyDayController {
    public Label greetingMessage;
    public Label date;
    public Label randomQuote;
    public Label quoteAuthor;
    public Label clock;
    public ListView<List> listViewLists;
    public TableColumn<Task,String>colTaskName;
    public TableView<Task> tableViewTasks;
    public ObservableList<List> listLists;
    public Button btnNewList;
    public Button btnDeleteList;


    private ObservableList<Task> activeSession = FXCollections.observableArrayList();
    private User user;
    private AppDAO dao;
    private AlertClass alertClass=new AlertClass();

    private final int currentHour=LocalDateTime.now().getHour();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat simpleClockFormat=new SimpleDateFormat("HH:mm");



    public MyDayController(User user, ArrayList<List>lists) {
        this.user=user;
        listLists = FXCollections.observableArrayList(lists);
        dao=AppDAO.getInstance();

    }

    @FXML
    public void initialize(){

        //set greeting message
       if(currentHour<=11) greetingMessage.setText("Good morning @"+user.getUsername()+"!");
       else if(currentHour<19) greetingMessage.setText("Good afternoon @"+user.getUsername()+"!");
       else greetingMessage.setText("Good evening @"+user.getUsername()+"!");

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
        colTaskName.setCellValueFactory(new PropertyValueFactory("taskName"));

        listViewLists.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) ->{
            List oldList=(List) oldItem;
            List newList=(List) newItem;

            if(newItem.getListName().equals("My day")){
                activeSession = FXCollections.observableArrayList(dao.getTasksForToday(user.getUsername()));
            } else{
                activeSession = FXCollections.observableArrayList(dao.getAllTasksByListName(user.getUsername(), newItem.getListName()));
            }


                tableViewTasks.setItems(activeSession);
                colTaskName.setCellValueFactory(new PropertyValueFactory("taskName"));

        } );



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
            if (newTask != null) {
                dao.addTask(newTask);
                listViewLists.getSelectionModel().select(new List(user.getUsername(), newTask.getListName()));
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
            List newList= listController.getList();
            if (newList != null) {
                dao.addList(user.getUsername(),newList.getListName());
                listLists.add(newList);
            }

        } );


    }



    public void actionDeleteList(ActionEvent actionEvent) {
        String selectedListName=listViewLists.getSelectionModel().getSelectedItem().getListName();
        if(selectedListName.equals("Tasks") || selectedListName.equals("My day")||selectedListName.equals("Planned")){
            alertClass.alertERROR("This list can't be deleted", "Lists: 'My day', 'Tasks' and 'Planned'" +
                    "can't be deleted.");
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
                listLists.remove(new List(user.getUsername(), selectedListName));
            }



    }

    public void actionEditTask(ActionEvent actionEvent) {
        Task task = tableViewTasks.getSelectionModel().getSelectedItem();
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

            }
        } );
    }



    public void actionDeleteTask(ActionEvent actionEvent) {
        Task task = tableViewTasks.getSelectionModel().getSelectedItem();
        String listName=task.getListName();
        if (task == null) return;
        if(alertClass.alertCONFIRMATION("Deleting task", "Are you sure you want to delete '"+task.getTaskName()+"'?",
                "This task will be deleted immediately. You can't undo this action.")){
            dao.deleteTask(task);
            activeSession.setAll(dao.getAllTasksByListName(user.getUsername(),listName));
        }


            Image reminderImage=new Image("/img/reminder.png");

            Notifications notification=Notifications.create()
                    .title("Download complete")
                    .text("Saved to home")
                    .graphic(new ImageView(reminderImage))
                    .hideAfter(Duration.seconds(10))
                    .position(Pos.BOTTOM_RIGHT);
            notification.darkStyle();
            notification.show();


    }
}




