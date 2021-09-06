package ba.unsa.etf.rpr.project;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class TooltipClass {

    public static Tooltip makeTooltip(String content){
        Tooltip tooltip=new Tooltip(content);
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setStyle("-fx-background-color: #fbebfc; -fx-text-fill: black; -fx-font-size: 12px;");
        return tooltip;
    }


}
