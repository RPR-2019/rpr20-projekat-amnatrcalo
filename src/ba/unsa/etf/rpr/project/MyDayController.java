package ba.unsa.etf.rpr.project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class MyDayController {
    public Label greetingMessage;
    public Label clock;
    public Label randomQuote;
    public Label quoteAuthor;
    private User user;
    AppDAO dao;

    private final int currentHour=LocalDateTime.now().getHour();
    private final DateFormat format = DateFormat.getInstance();

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

       //set time
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            final Calendar cal = Calendar.getInstance();
            clock.setText(format.format(cal.getTime()));

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }




}




