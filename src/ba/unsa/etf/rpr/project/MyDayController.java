package ba.unsa.etf.rpr.project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

//kada se poziva postaviti mu minHeight i minwidth od oka
public class MyDayController {
    public Label greetingMessage;
    public Label date;
    public Label randomQuote;
    public Label quoteAuthor;
    public Label clock;
    public ChoiceBox<List> choiceList;
    public TableColumn<Task,String>colTaskName;
    public TableView<Task> tableViewTasks;

    public ObservableList<List> listLists;
    private ObservableList<Task> activeSession = FXCollections.observableArrayList();
    private User user;
    AppDAO dao;

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

        choiceList.setItems(listLists);

        /*colTaskName.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        activeSession.add(new Exercise("hrrykane"));
        exerciseTable.setItems(activeSession);*/

    }


    public void actionAddNewTask(ActionEvent actionEvent) throws IOException {

        Stage addNewTask=new Stage();
        Parent root=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"));
        TaskController taskController=new TaskController(user,listLists);
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






    }
}




