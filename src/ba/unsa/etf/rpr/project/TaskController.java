package ba.unsa.etf.rpr.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class TaskController {



    public ImageButton newButton = new ImageButton(new Image("/img/date-and-time-icon.png"), 16, 16);
    public GridPane gridPane;

    @FXML
    public void initialize(){
        /*final Integer initialValue = 0;
        SpinnerValueFactory<Integer> valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(01, 24, initialValue);

        spinner.setValueFactory(valueFactory);*/
        gridPane.add(newButton,0,2);
        Tooltip hoverBtnDateAndTime=new Tooltip("Set date and time of start or end of the task. This is optional.");
        hoverBtnDateAndTime.setStyle("-fx-background-color: yellow; -fx-text-fill: red;");
        hoverBtnDateAndTime.setShowDelay(Duration.millis(100));

        newButton.setTooltip(hoverBtnDateAndTime);

    }
}
