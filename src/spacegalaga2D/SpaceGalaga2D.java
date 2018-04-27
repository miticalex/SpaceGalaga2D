/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegalaga2D;

import cameras.Camera2D;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sprites.Background;
import sprites.Enemy;
import sprites.Player;
import sprites.Shot;

/**
 *
 * @author AM
 */
public class SpaceGalaga2D extends Application {
    private static final String TITLE = "Space Galaga 2D - AM";
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    
    private static final int TOP_OF_THE_SCREEN = 0;
    
    public static int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public static int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }
    
    private static final int ENEMIES_IN_A_ROW = 6;
    private static final int ENEMIES_IN_A_COLUMN = 3;
    private static final int SPACE_BTW_ENEMIES = 100;
    
    private Group root;
    private Camera2D camera;
    private Background background;
//    TODO: Consider setting a camera in the way below
//    private static enum View {SCENE, PLAYER}; 
//    private View view = View.SCENE;
    
    private List<Enemy> enemies;
    private Player player;
    private List<Shot> shots;
    
    private double time = 0;
    private boolean theEnd = false;
    private Text elapsed;
    
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
    
    @Override
    public void start(Stage window) {
        root = new Group();
        setBackground(Color.BLACK, Color.DARKBLUE);
        camera = new Camera2D();
        
        setEnemies();
        setPlayer();
        
        root.getChildren().add(camera);
        
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
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
        if (theEnd == false) {
            shots = player.getShots();
            
            for (int i = 0; i < shots.size(); i++) {
                Shot currentShot = shots.get(i);
            
                if (currentShot.getTranslateY() < TOP_OF_THE_SCREEN) {
                    shots.remove(currentShot);
                }
                
                // Check if shot has hit the enemy - happens when they intersect
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy currentEnemy = enemies.get(j);
                    if (currentShot.getBoundsInParent().intersects
                       (currentEnemy.getBoundsInParent())) {
                        shots.remove(currentShot);
                        enemies.remove(currentEnemy);
                        break;
                    }
                }
            }
            
            camera.getChildren().clear();
            
            camera.getChildren().add(player);
            player.update();
            if (player.isPlayerCamera())
                camera.setTranslateX(WINDOW_WIDTH/2 - player.getTranslateX());
            else
                camera.setTranslateX(0);
            
            if (enemies.isEmpty()) {
                theEnd = true;
            } else {
                camera.getChildren().addAll(shots);
                shots.forEach(shot -> shot.update());
                player.setShots(shots);
            
                camera.getChildren().addAll(enemies);
            }
            
            //TODO: enhance positioning
            root.getChildren().remove(elapsed);
            elapsed = new Text(WINDOW_WIDTH/2 - 30, 20,  "Time: " + (int)time);
            elapsed.setFont(new Font(16));
            elapsed.setFill(Color.RED);
            elapsed.setStroke(Color.RED);
            root.getChildren().add(elapsed);
            
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
