package ba.unsa.etf.rpr.project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Random;

//kada se poziva postaviti mu minHeight i minwidth od oka
public class MyDayController {
    public Label greetingMessage;
    public Label date;
    public Label randomQuote;
    public Label quoteAuthor;
    public Label clock;
    private User user;
    AppDAO dao;

    private final int currentHour=LocalDateTime.now().getHour();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat simpleClockFormat=new SimpleDateFormat("HH:mm");

    //konstruktor: proslijediti mu user


    public MyDayController() {
        dao=AppDAO.getInstance();
    }

    @FXML
    public void initialize(){
        //set greeting message
       if(currentHour<=11) greetingMessage.setText("Good morning, ");
       else if(currentHour<19) greetingMessage.setText("Good afternoon, "); //+user.getUsername()
       else greetingMessage.setText("Good evening, ");

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


    }


    public void actionAddNewTask(ActionEvent actionEvent) throws IOException {
        Stage addNewTask=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/task.fxml"));


        addNewTask.setTitle("My Day");
        addNewTask.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
        addNewTask.getIcons().add(icon);
        addNewTask.setResizable(false);
        addNewTask.show();
    }
}




