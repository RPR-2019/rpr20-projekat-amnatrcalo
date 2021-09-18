package ba.unsa.etf.rpr.project;


import ba.unsa.etf.rpr.project.enums.Help;
import ba.unsa.etf.rpr.project.enums.TooltipContent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;

public class HelpListController {
    public Text text1=new Text(), heading=new Text();
    public TextFlow textFlow=new TextFlow(heading,text1);
    public VBox vBox;


    @FXML
    public void initialize(){
        heading.setText(Help.LISTS_HEADING.toString()+"\n\n");
        heading.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        text1.setText(Help.LISTS.toString());
        vBox.getChildren().add(textFlow);



    }




}
