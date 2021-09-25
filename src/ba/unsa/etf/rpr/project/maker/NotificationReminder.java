package ba.unsa.etf.rpr.project.maker;

import ba.unsa.etf.rpr.project.enums.content.NotificationMessages;
import ba.unsa.etf.rpr.project.model.Task;
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
                .text(NotificationMessages.REMINDER_INFORMATION.toString() +t.getReminderDigit()+" "+t.getReminderPeriod())
                .graphic(new ImageView(reminderImage))
                .hideAfter(Duration.seconds(7))
                .position(Pos.BOTTOM_RIGHT);

                notification.show();




    }


}
