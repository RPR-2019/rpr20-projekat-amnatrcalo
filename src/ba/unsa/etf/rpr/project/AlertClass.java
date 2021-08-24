package ba.unsa.etf.rpr.project;

import javafx.scene.control.Alert;

import java.util.Locale;

public class AlertClass {
    public void alertERROR(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        //styleAlert(alert);
        alert.showAndWait();
    }
}
