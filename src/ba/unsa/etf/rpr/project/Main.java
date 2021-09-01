package ba.unsa.etf.rpr.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.lang.annotation.Inherited;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"),bundle);
        HomeController ctrl = new HomeController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("To Do List App");
        primaryStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
        Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
