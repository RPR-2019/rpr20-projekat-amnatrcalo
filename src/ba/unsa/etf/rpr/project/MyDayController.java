package ba.unsa.etf.rpr.project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.IndexedCheckModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


//kada se poziva postaviti mu minHeight i minwidth od oka
public class MyDayController {
    public Label greetingMessage;
    public Label date;
    public Label randomQuote;
    public Label quoteAuthor;
    public Label clock;
    public ListView<CustomList> listViewLists;
    //public TableColumn<Task,String>colTaskName;
    //public TableView<Task> tableViewTasks;
    public CheckListView<Task> tableViewTasks;
    public ObservableList<CustomList> listLists;
    public Button btnNewList;
    public Button btnDeleteList;

    static Timeline timelineInfinite=new Timeline();

    private ObservableList<Task> activeSession = FXCollections.observableArrayList();
    private User user;
    private AppDAO dao;
    private AlertClass alertClass=new AlertClass();

    private ArrayList<Task> checkedTasks=new ArrayList<>();


    private final int currentHour=LocalDateTime.now().getHour();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat simpleClockFormat=new SimpleDateFormat("HH:mm");



    public MyDayController(User user, ArrayList<CustomList>lists) {
        this.user=user;
        listLists = FXCollections.observableArrayList(lists);
        dao=AppDAO.getInstance();

    }

    public boolean shouldSendNotif(Task t){
        return !t.getListName().equals("Completed");
    }


    @FXML
    public void initialize(){
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

        /*tableViewTasks.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(Change<? extends Task> change) {
                change.next();
                if(change.wasAdded()) {
                    for(Task t: change.getAddedSubList()){
                        System.out.println("Item Checked : " + t.getTaskName());
                    }
                    String oldListName= change.getAddedSubList().get(0).getListName();
                    change.getAddedSubList().get(0).setListName("Completed");
                    dao.editTask(change.getAddedSubList().get(0));
                    System.out.println("Item Checked : " + change.getAddedSubList().get(0));
                } else if (change.wasRemoved()) {
                    checkedTasks.remove(change.getRemoved().get(0));
                 //   System.out.println("Item Unchecked : " + change.getRemoved().get(0));
                }

            }
        });*/

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

    public void actionEditTask(ActionEvent actionEvent) {
        Task task = tableViewTasks.getSelectionModel().getSelectedItem();
        if(task==null) return;

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



    public void actionDeleteTask(ActionEvent actionEvent) {
        Task task = tableViewTasks.getSelectionModel().getSelectedItem();
        if(task==null) return;
        String listName=task.getListName();
        if (task == null) return;
        if(alertClass.alertCONFIRMATION("Deleting task", "Are you sure you want to delete '"+task.getTaskName()+"'?",
                "This task will be deleted immediately. You can't undo this action.")){
            dao.deleteTask(task);
            activeSession.setAll(dao.getAllTasksByListName(user.getUsername(),listName));
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






