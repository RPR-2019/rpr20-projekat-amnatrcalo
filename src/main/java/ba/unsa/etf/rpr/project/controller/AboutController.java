package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.enums.content.AboutText;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AboutController {
    public Text text1=new Text(), text2=new Text();
    public TextFlow  textFlow=new TextFlow(text1,text2);
    public VBox vBox;
    public Hyperlink hyperlink;

    @FXML
    public void initialize(){
        text1.setText("TO DO APP 2021.1\n\n");
        text2.setText(AboutText.MADE.toString()+"\n \n"+ AboutText.CREATED_BY.toString() + "\n \n \n "+
                AboutText.COPYRIGHT.toString());
        vBox.getChildren().add(textFlow);
        stylize();
    }

    private void stylize(){
        text1.setStyle("-fx-font-weight: bold");
    }
}
