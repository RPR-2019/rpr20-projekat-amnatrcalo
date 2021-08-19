package ba.unsa.etf.rpr.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        primaryStage.setTitle("Prijava");
        //use_computed_size da bi velicina prozora bila tacno velicine koja nam treba
        primaryStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        //login prozor nema potrebe da se povecava ili smanjuje
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
