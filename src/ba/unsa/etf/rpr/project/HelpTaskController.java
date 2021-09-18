package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.Help;
import ba.unsa.etf.rpr.project.enums.StageName;
import ba.unsa.etf.rpr.project.enums.TooltipContent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class HelpTaskController {
    public Text text1=new Text(), heading=new Text();
    public TextFlow textFlow=new TextFlow(heading,text1);
    public VBox vBox;
    public ImageButton arrow=new ImageButton(new Image("/img/right_arrow.png"),15,15);

    private final ResourceBundle bundle = ResourceBundle.getBundle("Translation");

    @FXML
    public void initialize(){
        heading.setText(Help.TASK_HEADING.toString()+"\n\n");
        heading.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        text1.setText(Help.TASK.toString());
        vBox.getChildren().add(textFlow);
        vBox.getChildren().add(arrow);
        arrow.setTooltip(TooltipClass.makeTooltip(TooltipContent.NEXT.toString()));

        arrow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage helpListStage=new Stage();
                Parent root=null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/helpList.fxml"),bundle);
                HelpListController helpController=new HelpListController();
                loader.setController(helpController);
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                helpListStage.setTitle(StageName.HELP.toString());
                helpListStage.setScene(new Scene(root, Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE));
                Image icon=new Image(getClass().getResourceAsStream("/img/plan-your-day-icon.png"));
                helpListStage.getIcons().add(icon);
                helpListStage.setResizable(false);
                helpListStage.show();
            }
        });

    }
}
