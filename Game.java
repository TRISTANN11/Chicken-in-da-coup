package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game extends Application {

    Random rnd = new Random();

    AnimationTimer gameLoop;

    Pane playfieldLayer;
    Pane scoreLayer;

    String mapName;

    Image playerImage;
    Image enemyImage;

    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

    Text collisionText = new Text();
    Text scoreText = new Text();
    int score;
    boolean collision = false;

    Scene scene;

    public Game() throws URISyntaxException {
    }

    public void startGame(Stage primaryStage) {


        mapName = "Map1.csv";
        Image background = new Image("sample/Map.jpg");
        ImageView backgroundView = new ImageView();
        backgroundView.setImage(background);

        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();

        score = 0;
        scoreText.setText("Score: " + score);
        scoreText.setFont( Font.font( null, FontWeight.BOLD, 32));
        scoreText.setStroke(Color.BLACK);
        scoreText.relocate(0, 10);

        root.getChildren().add(backgroundView);
        root.getChildren().add(playfieldLayer);
        root.getChildren().add(scoreLayer);
        root.getChildren().add(scoreText);

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);



        primaryStage.setScene(scene);
        primaryStage.show();

        loadGame();

        createScoreLayer();
        createPlayer();
        spawnEnemies(75, 75,"clay");
        spawnEnemies(75, 700,"clay");
        spawnEnemies(725, 75,"clay");
        spawnEnemies(725, 700,"clay");
        spawnEnemies(500, 450,"clay");
        spawnEnemies(250, 450,"clay");
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                players.forEach(sprite -> sprite.processInput());

                // add random enemies


                // movement
                players.forEach(sprite -> sprite.move());
                enemies.forEach(sprite-> sprite.LOS(players.get(0).getX(), players.get(0).getY()));
                enemies.forEach(sprite -> sprite.move());

                // check collisions
                checkCollisions();

                // update sprites in scene
                players.forEach(sprite -> sprite.updateUI());
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites( enemies);

                // update score, health, etc
                score++;
                scoreText.setText("Score: " + score / 25);
                updateScore();
            }

        };
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event){
                if (event.getCode() == KeyCode.ESCAPE) {
                    gameLoop.stop();
                    enemies.removeAll(enemies);
                    players.removeAll(players);
                    start(primaryStage);
                }
            }
        });
        gameLoop.start();

    }

    @Override
    public void start(Stage primaryStage){

        mapName = "MenuMap.csv";

        //Getting Scene Size
        double sceneWidth = (int)Math.round(Settings.SCENE_WIDTH);
        double sceneHeight = (int)Math.round(Settings.SCENE_HEIGHT);

        //The center of the background art is not the true center of the screen, so
        //a small adjustment is made to make sure everything is in the center of
        //the background art.
        double xAdjustment = sceneWidth/65;
        double xCenter = Math.round(sceneWidth/2);
        double yCenter = Math.round(sceneHeight/2);

        //Creating images
        Image background = new Image("sample/Images/menu/blankmenu.jpg");
        Image title = new Image("sample/Images/menu/title.png");
        Image start = new Image("sample/Images/menu/start.png");
        Image instructions = new Image("sample/Images/menu/instructions.png");

        //Setting image views
        ImageView backgroundView = new ImageView(background);
        ImageView titleView = new ImageView(title);
        ImageView startView = new ImageView(start);
        ImageView instructionsView = new ImageView(instructions);


        //Setting image positions
        Point.Double titlePos = new Point.Double(xCenter, (sceneHeight*0.28));
        Point.Double startPos = new Point.Double(xCenter, (sceneHeight*0.52));
        Point.Double instructionsPos = new Point.Double(xCenter, (sceneHeight*0.65));

        titleView.setX(titlePos.x);
        titleView.setY(titlePos.y);
        startView.setX(startPos.x);
        startView.setY(startPos.y);
        instructionsView.setX(instructionsPos.x);
        instructionsView.setY(instructionsPos.y);

        ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
        imageViews.add(backgroundView);
        imageViews.add(titleView);
        imageViews.add(startView);
        imageViews.add(instructionsView);

        Group root = new Group();

        //scale images, fix positions, and add to group
        for(int i=0;i<imageViews.size();i++){
            ImageTools.scaleImage(imageViews.get(i), sceneWidth, sceneHeight);
            if (imageViews.get(i) != backgroundView){
                ImageTools.centerImage(imageViews.get(i));
            }
            root.getChildren().add(imageViews.get(i));
        }


        //Selected Menu Option Animation
        ScaleTransition selectedTransition = new ScaleTransition();
        selectedTransition.setDuration(Duration.millis(800));
        selectedTransition.setByX(0.1);
        selectedTransition.setByY(0.1);
        selectedTransition.setCycleCount(Integer.MAX_VALUE);
        selectedTransition.setAutoReverse(true);
        selectedTransition.setNode(startView);
        selectedTransition.play();
        int[] select = {1};


        //Chicken animations
        playfieldLayer = new Pane();
        root.getChildren().add(playfieldLayer);
        loadGame();
        spawnEnemies(50, 50,"clay");
        spawnEnemies(700, 700,"clay");
        spawnEnemies(750, 50,"white");
        spawnEnemies(50, 750,"white");
        spawnEnemies(400, 750,"white");
        spawnEnemies(400, 50,"clay");
        spawnEnemies(750, 400,"white");

        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                enemies.forEach(sprite-> sprite.menuAI());
                enemies.forEach(sprite -> sprite.move());

                //update sprites
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites( enemies);
            }

        };

        //Event handlers for navigating menu
        startView.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedTransition.playFrom(Duration.millis(0));
                        selectedTransition.stop();
                        selectedTransition.setNode(startView);
                        selectedTransition.play();
                        select[0] = 1;
                    }
                }
        );
        startView.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        gameLoop.stop();
                        enemies.removeAll(enemies);
                        startGame(primaryStage);
                    }
                }
        );
        instructionsView.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedTransition.playFrom(Duration.millis(0));
                        selectedTransition.stop();
                        selectedTransition.setNode(instructionsView);
                        selectedTransition.play();
                        select[0] = 2;
                    }
                }
        );

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event){
                switch(event.getCode()){

                    case UP:
                        selectedTransition.playFrom(Duration.millis(0));
                        selectedTransition.stop();
                        selectedTransition.setNode(startView);
                        selectedTransition.play();
                        select[0] = 1;
                        break;

                    case DOWN:
                        selectedTransition.playFrom(Duration.millis(0));
                        selectedTransition.stop();
                        selectedTransition.setNode(instructionsView);
                        selectedTransition.play();
                        select[0] = 2;
                        break;

                    case ENTER:
                        if (select[0] == 1){
                            gameLoop.stop();
                            enemies.removeAll(enemies);
                            startGame(primaryStage);
                        }
                }
            }
        });

        gameLoop.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadGame() {
        playerImage = new Image( getClass().getResource("Images/chickens/whiteChickenDown1.png").toExternalForm());
        enemyImage = new Image( getClass().getResource("enemy.png").toExternalForm());
    }

    private void createScoreLayer() {


        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        scoreLayer.getChildren().add( collisionText);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        collisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
        collisionText.relocate(x - 175, y - 10);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);

    }
    private void createPlayer() {

        // player input
        Input input = new Input( scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over

        Image image = playerImage;

        // center horizontally, position at 70% vertically
        double x = 400;
        double y = 350;

        // create player
        Player player = new Player(playfieldLayer, image, x, y,  0, 0 , Settings.PLAYER_SPEED, input,mapName,"white");

        // register player
        players.add( player);

    }

    private void spawnEnemies( int x, int y,String color) {

        // image
        Image image = enemyImage;

        // random speed


        Enemy enemy = new Enemy( playfieldLayer, image, x, y, 0,  0 ,mapName, color);

        // manage sprite
        enemies.add(enemy);

    }

    private void removeSprites(  List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        while( iter.hasNext()) {
            SpriteBase sprite = iter.next();

            if( sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();

                // remove from list
                iter.remove();
            }
        }
    }

    private void checkCollisions() {

        collision = false;

        for( Player player: players) {
            for( Enemy enemy: enemies) {
                if( player.collidesWith(enemy)) {
                    collision = true;
                }
            }
        }
    }

    private void updateScore() {
        if( collision) {
            collisionText.setText("\tGame Over\n(esc for main menu)");
            gameLoop.stop();
        } else {
            collisionText.setText("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}