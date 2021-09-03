package ba.unsa.etf.rpr.project;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Locale;
import java.util.Optional;

public class AlertClass {
    public static void alertERROR(String header, String content, String image) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(" ");
        alert.setHeaderText(header);
        alert.setContentText(content);
        styleAlert(alert,image);
        alert.showAndWait();
    }

    private static void styleAlert(Alert alert, String image) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(AlertClass.class.getResource(image).toExternalForm()));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertClass.class.getResource("/css/alert.css").toExternalForm());
        dialogPane.getStyleClass().add("alert-pane");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    }

    public static boolean alertCONFIRMATION(String title, String header, String content){
        boolean ok=false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ok=true;
        }
        return ok;
    }

    public static void alertINFORMATION(String title, String header, String content, String image){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        styleAlert(alert,image);
        alert.show();
    }




}
