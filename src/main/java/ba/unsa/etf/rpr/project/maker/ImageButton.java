package ba.unsa.etf.rpr.project.maker;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {
    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 2, 2, 2, 2; transition-duration: 0.4s;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 3 1 1 3; ";
    private final String STYLE_ENTERED="-fx-cursor:hand; -fx-border:none; -fx-background-color:transparent;";
    public ImageButton(Image originalImage, double h, double w) {

        ImageView image = new ImageView(originalImage);
        image.setFitHeight(h);
        image.setFitHeight(w);
        image.setPreserveRatio(true);
        setGraphic(image);
        setStyle(STYLE_NORMAL);

        setOnMousePressed(event -> setStyle(STYLE_PRESSED));
        setOnMouseReleased(event -> setStyle(STYLE_NORMAL));
        setOnMouseEntered(event->setStyle(STYLE_ENTERED));
        setOnMouseExited(event->setStyle(STYLE_NORMAL));
    }
}
