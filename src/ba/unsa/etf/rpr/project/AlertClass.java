package ba.unsa.etf.rpr.project;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Locale;
import java.util.Optional;

public class AlertClass {
    public void alertERROR(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }




}
