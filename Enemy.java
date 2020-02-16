package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends SpriteBase {

    public Enemy(Pane layer, Image image, double x, double y, double dx, double dy , String mapName,String color) {
        super(layer, image, x, y,  dx, dy,mapName,color);

    }

    @Override
    public void checkRemovability() {

        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }


    }
}