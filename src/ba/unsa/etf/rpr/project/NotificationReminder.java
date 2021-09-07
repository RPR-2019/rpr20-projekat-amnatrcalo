package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.MyDayMessages;
import ba.unsa.etf.rpr.project.enums.NotificationMessages;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Locale;

public class NotificationReminder {

    public static void sendNotification(Task t){

        Image reminderImage=new Image("/img/reminder.png");

        Notifications notification=Notifications.create()
                .title(t.getTaskName().toUpperCase(Locale.ROOT))
                .text(NotificationMessages.REMINDERINFORMATION.toString() +t.getReminderDigit()+" "+t.getReminderPeriod())
                .graphic(new ImageView(reminderImage))
                .hideAfter(Duration.seconds(7))
                .position(Pos.BOTTOM_RIGHT);

                notification.show();




    }


}
