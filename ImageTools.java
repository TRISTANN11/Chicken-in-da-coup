package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTools {

    public static void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {

            double xPos = imageView.getX();
            double yPos = imageView.getY();

            double imgCenterXPos = imageView.getFitWidth()/2;
            double imgCenterYPos = imageView.getFitHeight()/2;

            double newXPos = xPos - imgCenterXPos;
            double newYPos = yPos - imgCenterYPos;

            imageView.setX(newXPos);
            imageView.setY(newYPos);
        }
    }

    public static void scaleImage(ImageView imageView, Double sceneWidth, Double sceneHeight){
        Image img = imageView.getImage();
        if (img != null) {

            double fitWidth = (img.getWidth() * sceneWidth)/800;
            double fitHeight = (img.getHeight() * sceneHeight)/800;

            imageView.setFitWidth(fitWidth);
            imageView.setFitHeight(fitHeight);
        }
    }
}