package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.AboutText;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AboutController {
    public Text text1=new Text(), text2=new Text();
    public TextFlow  textFlow=new TextFlow(text1,text2);
    public VBox vBox;

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
