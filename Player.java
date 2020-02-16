package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Player extends SpriteBase {

    double playerMinX;
    double playerMaxX;
    double playerMinY;
    double playerMaxY;

    Input input;

    double speed;

    public Player(Pane layer, Image image, double x, double y, double dx, double dy, double speed, Input input,String mapName, String color) {

        super(layer, image, x, y,  dx, dy, mapName,color);

        this.speed = speed;
        this.input = input;

        init();
    }


    private void init() {

        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen
        playerMinX = 0;
        playerMaxX = Settings.SCENE_WIDTH - idleUp.getWidth();
        playerMinY = 75;
        playerMaxY = Settings.SCENE_HEIGHT -idleUp.getHeight();

    }

    public void processInput() {

        // ------------------------------------
        // movement
        // ------------------------------------

        // vertical direction
        if (input.isMoveUp())
        {
            spriteAnimation.setImage(idleUp);
            frame = 0;
            dx = 0;
            dy = -speed;
        } else if (input.isMoveDown())
        {
            spriteAnimation.setImage(idleDown);
            frame = 0;
            dx = 0;
            dy = speed;
        }

        // horizontal direction
        if (input.isMoveLeft()) {
            spriteAnimation.setImage(idleLeft);
            frame = 0;
            dy = 0;
            dx = -speed;
        } else if (input.isMoveRight()) {
            spriteAnimation.setImage(idleRight);
            frame = 0;
            dy = 0;
            dx = speed;
        }


        //no direction
        if (!input.isMoveUp() && !input.isMoveDown() && !input.isMoveLeft() && !input.isMoveRight()){
            dy = 0;
            dx = 0;
        }
    }

    @Override
    public void move() {

        super.move();

        checkBounds();


    }

    private void checkBounds()
    {
        // vertical
        if( Double.compare( y, playerMinY) < 0) {
            y = playerMinY;
        } else if( Double.compare(y, playerMaxY) > 0) {
            y = playerMaxY;
        }

        // horizontal
        if( Double.compare( x, playerMinX) < 0) {
            x = playerMinX;
        } else if( Double.compare(x, playerMaxX) > 0) {
            x = playerMaxX;
        }

    }


    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }

}