/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegalaga2D;

import cameras.Camera2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import shapes.Star;
import sprites.Background;
import sprites.Enemy;
import sprites.FallingCoin;
import sprites.Player;
import sprites.Shot;

/**
 *
 * @author AM
 */
public class SpaceGalaga2D extends Application {
    public static final double G = 100;

    public static double getG() {
        return G;
    }

    private static final String TITLE = "Space Galaga 2D - AM";
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    
    public static int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public static int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }
    private static final int TOP_OF_THE_SCREEN = 0;
    private static final double STARS_VELOCITY = 1;
    private static final int MIN_STARS = 3;
    
    private static final int POINTS_FOR_ENEMY = 100;
    private static final int POINTS_FOR_COIN = 50;
    private static final double COIN_MIN_INIT_VELOCITY_Y = 25.0;
    private static final double COIN_MAX_INIT_VELOCITY_Y = 100.0;
    private static final double COIN_MIN_ANGLE = -Math.PI/6; //-15 degrees
    private static final double COIN_MAX_ANGLE = Math.PI/6; //15 degrees
    
    private static final int ENEMIES_IN_A_ROW = 6;
    private static final int ENEMIES_IN_A_COLUMN = 3;
    private static final int SPACE_BTW_ENEMIES = 100;
    
    private static final int NUM_STARS = 12;
    
    private static final int LABELS_POSITION_Y = 20;
    
    private Random random =  new Random();
    
    private Group root;
    private Camera2D camera;
    private Background background;
    private List<Star> stars;
    
//    TODO: Consider setting a camera in the way below
//    private static enum View {SCENE, PLAYER}; 
//    private View view = View.SCENE;
    
    private List<Enemy> enemies;
    private Player player;
    private List<Shot> shots;
    private List<FallingCoin> coins;
    
    private double time = 0;
    private int points = 0;
    private boolean theEnd = false;
    
    private Text elapsed;
    private Text pointsField;
    
    private Text theEndLabel;
    private Text pointsEarnedLabel;
    
    //SET METHODS SECTION
    
    private void setBackground(Background background1){
        root.getChildren().remove(background);
        
        background = background1;
        root.getChildren().addAll(background);
    }

    private void setBackground(Paint paint){
        setBackground(new Background(WINDOW_WIDTH, WINDOW_HEIGHT, paint));
    }
    
    private void setBackground(Color color1, Color color2){
        setBackground(new Background(WINDOW_WIDTH, WINDOW_HEIGHT, color1, color2));
    }
        
    private void setBackground(Image image){
        setBackground(new Background(WINDOW_WIDTH, WINDOW_HEIGHT, image));
    }
    private void setBackground(){
        setBackground(Color.BLACK);
    }
    
    public void setStars(){
        stars = new LinkedList<>();
        
        for (int i = 0; i < NUM_STARS; i++) {
            Star star = new Star(-WINDOW_WIDTH/2 + 10 + random.nextInt(2*WINDOW_WIDTH - 20),
                                    10 + random.nextInt(WINDOW_HEIGHT - 20));            
            stars.add(star);
            camera.getChildren().add(star);
        }
    }
    
    private void setEnemies(){
        enemies = new LinkedList<>();
        
        for (int i = 0; i < ENEMIES_IN_A_COLUMN; i++) 
            for (int j = 0; j < ENEMIES_IN_A_ROW; j++) {
                Enemy enemy = new Enemy();
                enemy.setTranslateX((j+1) * WINDOW_WIDTH / (ENEMIES_IN_A_ROW + 1));
                enemy.setTranslateY((i+1) * SPACE_BTW_ENEMIES);
                if ((i%2==0 && j%2==0) || (i%2==1 && j%2==1))
                    enemy.addLeftWink();
                else 
                    enemy.addRightWink();
             
                camera.getChildren().add(enemy);
                enemies.add(enemy);
            }
    }
    
    private void setPlayer(){
        player = new Player();
        
        player.setTranslateX(WINDOW_WIDTH/2);
        player.setTranslateY(WINDOW_HEIGHT * 0.98);
        
        camera.getChildren().add(player);
    }
    
    private void setTime(){
        elapsed = new Text("Time: " + (int)time);
        elapsed.setFont(new Font(16));
        elapsed.setFill(Color.RED);
        elapsed.setStroke(Color.RED);
        elapsed.setTranslateX(WINDOW_WIDTH/2 - elapsed.getBoundsInParent().getWidth()/2);
        elapsed.setTranslateY(LABELS_POSITION_Y);
        
        root.getChildren().add(elapsed);
    }
    
    private void setPoints(){
        pointsField = new Text(WINDOW_WIDTH - 150, LABELS_POSITION_Y, "Points: " + (int)points);
        pointsField.setFont(new Font(16));
        pointsField.setFill(Color.RED);
        pointsField.setStroke(Color.RED);
        
        root.getChildren().add(pointsField);
    }
    
    private void setTheEnd(){
        theEndLabel = new Text("The End");
        pointsEarnedLabel = new Text("Points earned: " + (int)points);
        
        theEndLabel.setFont(new Font(64));
        theEndLabel.setTranslateX(WINDOW_WIDTH/2
                - theEndLabel.getBoundsInParent().getWidth()/2);
        theEndLabel.setTranslateY(WINDOW_HEIGHT/2
                - theEndLabel.getBoundsInParent().getHeight()/2);
        theEndLabel.setFill(Color.RED);
        theEndLabel.setStroke(Color.BLACK);
        
        pointsEarnedLabel.setFont(new Font(64));
        pointsEarnedLabel.setTranslateX(WINDOW_WIDTH/2
                - pointsEarnedLabel.getBoundsInParent().getWidth()/2);
        pointsEarnedLabel.setTranslateY(WINDOW_HEIGHT/2
                + pointsEarnedLabel.getBoundsInParent().getHeight());
        pointsEarnedLabel.setFill(Color.RED);
        pointsEarnedLabel.setStroke(Color.BLACK);
        
        root.getChildren().add(theEndLabel);
        root.getChildren().add(pointsEarnedLabel);
    }
    
    // UPDATE METHODS SECTION
    
    private void rotateStars(){
        if (stars.size()<NUM_STARS && random.nextDouble()<0.005){
            Star star = new Star(1.5*WINDOW_WIDTH,
                                10 + random.nextInt(WINDOW_HEIGHT - 20));
            stars.add(star);
            camera.getChildren().add(star);
        }
        
        for (Star star : stars) {
            if (star.getTranslateX() - STARS_VELOCITY < -WINDOW_WIDTH/2){
                stars.remove(star);
            } else {
                star.setTranslateX(star.getTranslateX() - STARS_VELOCITY);
            }
        }
    }
    
    private void updateStars(){
        rotateStars();
        
        //ENSURE THERE IS NO LESS THAN 3 STARS
        int starsOnCamera = 0;
        for (Star star : stars) {
            if  ((star.getTranslateX() >= -camera.getTranslateX()) &&
            (star.getTranslateX() <= -camera.getTranslateX() + WINDOW_WIDTH + 2*star.getOuterR()))
                starsOnCamera++;
        }
        
        for (int i = starsOnCamera; i < MIN_STARS; i++) {
            Star newStar = new Star();
            newStar.setTranslateX(-camera.getTranslateX() + WINDOW_WIDTH + 2*newStar.getOuterR());
            newStar.setTranslateY(10 + random.nextInt(WINDOW_HEIGHT - 20));

            stars.add(newStar);
        }
    }
    
    @Override
    public void start(Stage window) {
        root = new Group();
        setBackground(Color.BLACK, Color.DARKBLUE);
        camera = new Camera2D();
        
        setStars();
        setEnemies();
        setPlayer();
        coins = new LinkedList<>();
        
        root.getChildren().add(camera);
        
        setTime();
        setPoints();
        
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, false, SceneAntialiasing.BALANCED);
        scene.setOnKeyPressed(player);
        scene.setOnKeyReleased(player);
        
        window.setTitle(TITLE);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                update();
            }
        }.start();
    }

    public void update() {
        updateStars();
        
        camera.getChildren().clear();
        if (player.isPlayerCamera())
            camera.setTranslateX(WINDOW_WIDTH/2 - player.getTranslateX());
        else
            camera.setTranslateX(0);

        camera.getChildren().addAll(stars);
        camera.getChildren().add(player);
        camera.getChildren().addAll(enemies);
        player.update();
        
        if (theEnd == false) {
            for (Enemy enemy : enemies) {
                enemy.update();
                if (enemy.isDead()){
                    // creating coin
                    double coinInitVelocityY = -COIN_MIN_INIT_VELOCITY_Y 
                        - random.nextDouble()*
                            (COIN_MAX_INIT_VELOCITY_Y - COIN_MIN_INIT_VELOCITY_Y);
                    
                    double coinInitVelocityX = coinInitVelocityY * 
                        Math.tan(COIN_MIN_ANGLE + 
                            random.nextDouble()*(COIN_MAX_ANGLE-COIN_MIN_ANGLE));
                    
                    FallingCoin coin = new FallingCoin(
                            coinInitVelocityX, coinInitVelocityY);
                    coin.setTranslateX(enemy.getBoundsInParent().getMinX() + 
                            random.nextDouble()*(enemy.getBoundsInParent().getWidth() - coin.getWIDTH()));
                    coin.setTranslateY(enemy.getTranslateY());
                    coins.add(coin);
                    
                    enemies.remove(enemy);
                    continue;
                } else if (enemy.isHit())
                    continue;
                
                if (player.getBoundsInParent().intersects
                   (enemy.getBoundsInParent())) {
                    theEnd = true;
                    setTheEnd();
                    break;
                }
            }
            
            shots = player.getShots();
            
            //remove enemies
            for (Shot currentShot : shots) {
                if (currentShot.getTranslateY() < TOP_OF_THE_SCREEN) {
                    shots.remove(currentShot);
                }
                
                // Check if shot has hit the enemy - happens when they intersect
                for (Enemy currentEnemy : enemies) {
                    if (!currentEnemy.isHit() 
                            && currentShot.getBoundsInParent().intersects
                                (currentEnemy.getBoundsInParent())) {
                        shots.remove(currentShot);
                        currentEnemy.disappear();
                        points += POINTS_FOR_ENEMY;
                        break;
                    }
                }
            }
            
            coins.forEach(coin -> {
                if (coin.getBoundsInParent().getMinY() > WINDOW_HEIGHT){
                    coins.remove(coin);
                    return; //continue equivalent
                }
                if (coin.getBoundsInParent().intersects(
                    (player.getBoundsInParent()))){
                    points += POINTS_FOR_COIN;
                    coins.remove(coin);
                }
            });
            
            if (enemies.isEmpty() && coins.isEmpty()) {
                theEnd = true;
                setTheEnd();
            } else {
                camera.getChildren().addAll(shots);
                camera.getChildren().addAll(coins);
                shots.forEach(shot -> shot.update());
                coins.forEach(coin -> coin.update());
                player.setShots(shots);
            }
            
            root.getChildren().remove(elapsed);
            elapsed.setText("Time: " + (int)time);
            elapsed.setTranslateX(WINDOW_WIDTH/2 - elapsed.getBoundsInParent().getWidth()/2);
            root.getChildren().add(elapsed);
            
            root.getChildren().remove(pointsField);
            pointsField.setText("Points: " + (int)points);
            root.getChildren().add(pointsField);
            
            time += 1.0 / 60;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
