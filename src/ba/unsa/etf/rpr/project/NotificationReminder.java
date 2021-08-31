package ba.unsa.etf.rpr.project;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationReminder {

    public static void sendNotification(Task t){
        Image reminderImage=new Image("/img/reminder.png");

        Notifications notification=Notifications.create()
                .title(t.getTaskName())
                .text("Starts in "+t.getReminderDigit()+" "+t.getReminderPeriod())
                .graphic(new ImageView(reminderImage))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        notification.darkStyle();
        notification.show();
    }
}
