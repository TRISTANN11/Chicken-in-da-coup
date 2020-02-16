package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;

public abstract class SpriteBase {
    Image idleUp;
    Image idleDown;
    Image idleLeft;
    Image idleRight;
    Image alert;
    Image moveLeft1;
    Image moveLeft2;
    Image moveRight1;
    Image moveRight2;
    Image moveDown1;
    Image moveDown2;
    Image moveUp1;
    Image moveUp2;

    ImageView spriteAnimation;

    int frame;

    String color;

    Pane layer;

    double x;
    double y;
    double r;

    double dx;
    double dy;
    double dr;

    double playerSpottedX = -10000;
    double playerSpottedY = -10000;
    double detectTimer;
    double detectTime = 20;
    double detectSize = 50;
    double moveToSize = 14;

    double collideSize = 20;

    double health;
    double damage;
    double speed = 2;
    double patrolSpeed = 1;

    boolean removable = false;

    CheckMap mapLevel;

    double w;
    double h;

    boolean canMove = true;

    public SpriteBase(Pane layer, Image image, double x, double y, double dx, double dy, String mapName, String color) {

        try {
            mapLevel = new CheckMap(mapName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.layer = layer;
        this.color = color;

        idleDown = new Image(getClass().getResource("Images/chickens/" + color + "ChickenDown2.png").toExternalForm());
        idleLeft = new Image(getClass().getResource("Images/chickens/" + color + "ChickenLeft2.png").toExternalForm());
        idleRight = new Image(getClass().getResource("Images/chickens/" + color + "ChickenRight2.png").toExternalForm());
        idleUp = new Image(getClass().getResource("Images/chickens/" + color + "ChickenUp2.png").toExternalForm());
        alert = new Image(getClass().getResource("Images/chickens/clayChickenAlert.png").toExternalForm());

        moveLeft1 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenLeft1.png").toExternalForm());
        moveLeft2 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenLeft3.png").toExternalForm());
        moveRight1 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenRight1.png").toExternalForm());
        moveRight2 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenRight3.png").toExternalForm());
        moveDown1 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenDown1.png").toExternalForm());
        moveDown2 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenDown3.png").toExternalForm());
        moveUp1 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenUp1.png").toExternalForm());
        moveUp2 = new Image(getClass().getResource("Images/chickens/" + color + "ChickenUp3.png").toExternalForm());

        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;

        this.spriteAnimation = new ImageView(idleUp);
        this.spriteAnimation.relocate(x, y);
        this.spriteAnimation.setRotate(r);

        this.w = image.getWidth(); // imageView.getBoundsInParent().getWidth();
        this.h = image.getHeight(); // imageView.getBoundsInParent().getHeight();

        frame = 0;

        addToLayer();

    }


    public void addToLayer() {
        this.layer.getChildren().add(this.spriteAnimation);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.spriteAnimation);
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void move() {
        frame = ++frame % 60;

        if (!canMove)
            return;

        double[] movement;
        movement = mapLevel.movement(x, y, dx, dy);
        x = movement[0];
        y = movement[1];

        if (dx < 0) {
            switch (frame) {
                case 0:
                case 30:
                    spriteAnimation.setImage(idleLeft);
                    break;
                case 15:
                    spriteAnimation.setImage(moveLeft1);
                    break;
                case 45:
                    spriteAnimation.setImage(moveLeft2);
                    break;
                default:
                    break;
            }
        }
        if (dx > 0) {
            switch (frame) {
                case 0:
                case 30:
                    spriteAnimation.setImage(idleRight);
                    break;
                case 15:
                    spriteAnimation.setImage(moveRight1);
                    break;
                case 45:
                    spriteAnimation.setImage(moveRight2);
                    break;
                default:
                    break;
            }
        }
        if (dy > 0) {
            switch (frame) {
                case 0:
                case 30:
                    spriteAnimation.setImage(idleDown);
                    break;
                case 15:
                    spriteAnimation.setImage(moveDown1);
                    break;
                case 45:
                    spriteAnimation.setImage(moveDown2);
                    break;
                default:
                    break;
            }
        }
        if (dy < 0) {
            switch (frame) {
                case 0:
                case 30:
                    spriteAnimation.setImage(idleUp);
                    break;
                case 15:
                    spriteAnimation.setImage(moveUp1);
                    break;
                case 45:
                    spriteAnimation.setImage(moveUp2);
                    break;
                default:
                    break;
            }
        }
    }

    public ImageView getView() {
        return spriteAnimation;
    }

    public void updateUI() {
        spriteAnimation.relocate(x, y);
    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }

    // TODO: per-pixel-collision and map collisions
    public boolean collidesWith(SpriteBase otherSprite) {
        boolean toReturn = false;

        if (distTo(otherSprite.x, x) < collideSize && distTo(otherSprite.y,y) < collideSize){
            toReturn = true;
        }

        return (toReturn);
    }

    /**
     * Set flag that the sprite can be removed from the UI.
     */
    public void remove() {
        setRemovable(true);
    }

    /**
     * Set flag that the sprite can't move anymore.
     */
    public void stopMovement() {
        this.canMove = false;
    }

    public abstract void checkRemovability();

    public void AI(double Playerx, double Playery) {

        double toworkonx = Playerx;
        double toworkony = Playery;

        if (frame % 6 == 0) {
            if (Math.abs(Playerx - x) < Math.abs(Playery - y)) {
                if (Playerx > x && mapLevel.canMoveRight(x, y)) {
                    dy = 0;
                    dx = 5;
                } else if (Playerx < x && mapLevel.canMoveLeft(x, y)) {
                    dy = 0;
                    dx = -5;
                } else if (Playery > y && mapLevel.canMoveDown(x, y)) {
                    dy = 5;
                    dx = 0;
                } else {
                    dy = -5;
                    dx = 0;
                }

            } else {
                if (Playery > y && mapLevel.canMoveDown(x, y)) {
                    dy = 5;
                    dx = 0;
                } else if (Playery < y && mapLevel.canMoveUp(x, y)) {
                    dy = -5;
                    dx = 0;
                } else if (Playerx > x && mapLevel.canMoveRight(x, y)) {
                    dy = 0;
                    dx = 5;
                } else {
                    dy = 0;
                    dx = -5;
                }
            }


        }

      /*  boolean currentmovement = false;
        boolean canmovedown = mapLevel.canMoveDown(x, y);
        boolean canmoveup = mapLevel.canMoveUp(x, y);
        boolean canmoveleft = mapLevel.canMoveLeft(x, y);
        boolean canmoveright = mapLevel.canMoveRight(x,y);

        if(dy > 0){
            currentmovement = canmovedown;
        }
        if(dy < 0){
            currentmovement = canmoveup;
        }
        if(dx < 0){
            currentmovement = canmoveleft;
        }
        if(dx > 0){
            currentmovement = canmoveright;
        }

        if(toworkony < y && canmoveup && frame ==0){
            dx = 0;
            dy = -5;
        }

        else if(toworkony > y && canmovedown && frame ==0){
            dx = 0;
            dy = 5;
        }

        else if(toworkonx < x && canmoveright && frame == 0){
            dx = 5;
            dy = 0;
        }

        else if(toworkonx > x && canmoveleft && frame ==0){
            dx = -5;
            dy = 0;
        }

        if(!currentmovement){
            dy = 0;
            dx = 0;
            if(toworkony < y && canmoveup && !horizontalorVertical){
                dy = -5;
                horizontalorVertical = !horizontalorVertical;
            }
            else if(toworkony > y && canmovedown && !horizontalorVertical){
                dy = 5;
                horizontalorVertical = !horizontalorVertical;
            }
            else if (toworkonx < x && canmoveleft && horizontalorVertical){
                dx = -5;
                horizontalorVertical = !horizontalorVertical;
            }
            else if(toworkonx > x && canmoveright && horizontalorVertical){
                dx = 5;
                horizontalorVertical = !horizontalorVertical;
            }
            else{
                if(canmoveup){
                    dy = -5;
                }
                else if(canmovedown){
                    dy = 5;
                }
                else if (canmoveright){
                    dx = 5;
                }
                else if (canmoveleft){
                    dx = -5;
                }
            }

        }
       */
    }

    public void LOS(double Playerx, double Playery)
    {
        double px = Playerx;
        double py = Playery;
        //Debug info
//        if (frame % 227 == 0){
//            System.out.println("Player spotted: " + playerSpottedX + " " + playerSpottedY);
//            System.out.println("Spotted X distance: " + distTo(playerSpottedX, x));
//            System.out.println("Spotted Y distance: " + distTo(playerSpottedY, y));
//            System.out.println("Player xy:" + px + " " + py);
//            System.out.println("Enemy xy:" + x + " " + y);
//        }
        if (frame % 6 == 0) 
        {

            //Detect in LOS
            if (mapLevel.canSee(px,py,x,y) ||
                    //check if enemy can see any tiles adjacent to player
                    mapLevel.canSee(px + 25, py, x, y) ||
                    mapLevel.canSee(px-25, py, x, y) ||
                    mapLevel.canSee(px,py+25,x,y) ||
                    mapLevel.canSee(px,py-25,x,y) ||
                    //check if player can see any tiles adjacent to enemy
                    mapLevel.canSee(px,py, x+25,y) ||
                    mapLevel.canSee(px,py, x-25,y) ||
                    mapLevel.canSee(px,py, x, y+25) ||
                    mapLevel.canSee(px,py, x,y-25))
            {
                //spriteAnimation.setImage(alert);
                playerSpottedX = px;
                playerSpottedY = py;
                detectTimer = detectTime;
            }

            //Move to last detected position
            if (playerSpottedX != -10000 && detectTimer > 0)
            {
                
                //If at last detected location, don't move
                 if (distTo(playerSpottedX, x) < moveToSize && distTo(playerSpottedY, y)< moveToSize)
                 {
                        dx = 0;
                        dy = 0;
                 }

                 //If vertical LOS, move to X, then Y
                 else if (distTo(playerSpottedX, x) < detectSize  && distTo(playerSpottedX,x) < distTo(playerSpottedY,y) && distTo(playerSpottedY, y) > moveToSize)
                 {
                     //Move Right
                     if (playerSpottedX > x && distTo(playerSpottedY, y) > moveToSize && mapLevel.canMoveRight(x,y))
                     {
                         dx = speed;
                         dy = 0;
                     }
                     //Move Left
                     else if (playerSpottedX < x && distTo(playerSpottedX, x) > moveToSize && mapLevel.canMoveLeft(x,y))
                     {
                        dx = -speed;
                        dy = 0;
                     }
                     //Move Up
                     else if (playerSpottedY < y && distTo(playerSpottedY, y) > moveToSize && mapLevel.canMoveUp(x,y))
                     {
                        dx = 0;
                        dy = -speed;
                     }
                     //Move Down
                     else if (playerSpottedY > y && distTo(playerSpottedY, y) > moveToSize && mapLevel.canMoveDown(x,y))
                     {
                        dx = 0;
                        dy = speed;
                     }
                 }

                 //If horizontal LOS, move to Y, then X
                 else if (distTo(playerSpottedY, y) < detectSize && distTo(playerSpottedX,x) > distTo(playerSpottedY,y) && distTo(playerSpottedX, x) > moveToSize)
                 {
                     //Move Up
                     if (playerSpottedY < y && distTo(playerSpottedY, y) > moveToSize && mapLevel.canMoveUp(x,y))
                     {
                        dx = 0;
                        dy = -speed;
                     }
                     //Move Down
                      else if (playerSpottedY > y && distTo(playerSpottedY, y) > moveToSize && mapLevel.canMoveDown(x,y))
                     {
                        dx = 0;
                        dy = speed;
                     }
                     //Move Right
                     else if (playerSpottedX > x && distTo(playerSpottedX, x) > moveToSize && mapLevel.canMoveRight(x,y))
                     {
                        dx = speed;
                        dy = 0;
                     }
                     //Move Left
                     else if (playerSpottedX < x && distTo(playerSpottedX, x) > moveToSize && mapLevel.canMoveLeft(x,y))
                     {
                        dx = -speed;
                        dy = 0;
                     }
                 }
                 detectTimer--;
            }
            //Lost interest, patrol
            else if (detectTimer < 1){
                switch((int)(Math.random()*20)){
                    case 1:
                       if(mapLevel.canMoveUp(x,y)){
                           dx = 0;
                           dy = -patrolSpeed;
                       }
                    break;

                    case 2:
                        if(mapLevel.canMoveDown(x,y)){
                            dx = 0;
                            dy = patrolSpeed;
                        }
                    break;

                    case 3:
                        if(mapLevel.canMoveLeft(x,y)){
                            dx = -patrolSpeed;
                            dy = 0;
                        }

                    break;

                    case 4:
                        if(mapLevel.canMoveRight(x,y)){
                            dx = patrolSpeed;
                            dy = 0;
                        }
                    break;

                    default:
                    break;
                }

                //Check patrol collisions
                if (dx == patrolSpeed){
                    if (x > 795) {
                        dx = 0;
                    }
                }
                else if (dx == -patrolSpeed){
                    if (x < 5) {
                        dx = 0;
                    }
                }

                else if (dy == patrolSpeed){
                    if ( y > 795) {
                        dy = 0;
                    }
                }
                else if (dy == -patrolSpeed){
                    if ( y < 5) {
                        dy = 0;
                    }
                }
            }
            //No last seen position, don't move
            else
            {
                dy = 0;
                dx = 0;
            }
        }
    }

    private double distTo(double a, double b){
        double dist = Math.abs(a - b);
        return dist;
    }

    public void menuAI() {
        int dir;
        if (frame % 6 == 0) {
            dir = (int)Math.round(Math.random()* 25);

            switch(dir){
                case 1:
                    if(mapLevel.canMoveUp(x, y)){
                        dy = -patrolSpeed;
                        dx = 0;
                    }
                break;

                case 2:
                    if(mapLevel.canMoveDown(x, y)){
                        dy = patrolSpeed;
                        dx = 0;
                    }
                    break;

                case 3:
                    if(mapLevel.canMoveLeft(x, y)){
                        dy = 0;
                        dx = -patrolSpeed;
                    }
                    break;

                case 4:
                    if(mapLevel.canMoveRight(x, y)){
                        dy = 0;
                        dx = patrolSpeed;
                    }
                    break;

                case 5:
                    dx = 0;
                    dy = 0;
                break;
                default:
                    if (dx > 0){
                        dx = dx - .1;
                    }
                    if (dx < 0){
                        dx = dx + .1;
                    }

                    if (dy > 0){
                        dy = dy - .1;
                    }
                    if (dy < 0){
                        dy = dy + .1;
                    }
                break;
            }
        }

        //Check patrol collisions
        if (dx == patrolSpeed){
            if (x > 795) {
                dx = 0;
            }
        }
        else if (dx == -patrolSpeed){
            if (x < 5) {
                dx = 0;
            }
        }

        else if (dy == patrolSpeed){
            if ( y > 795) {
                dy = 0;
            }
        }
        else if (dy == -patrolSpeed){
            if ( y < 5) {
                dy = 0;
            }
        }
    }
}
