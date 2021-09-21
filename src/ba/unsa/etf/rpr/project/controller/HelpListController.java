package ba.unsa.etf.rpr.project.controller;


import ba.unsa.etf.rpr.project.enums.Help;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
