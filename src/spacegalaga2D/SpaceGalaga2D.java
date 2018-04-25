/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegalaga2D;

import cameras.Camera2D;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sprites.Background;
import sprites.Enemy;

/**
 *
 * @author AM
 */
public class SpaceGalaga2D extends Application {
    private static final String TITLE = "Space Galaga 2D - AM";
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    
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
    private Group camera;
    private Background background;
    
    private List<Enemy> enemies;
    
    private void setBackground(Background background1){
        root.getChildren().remove(background);
        
        background = background1;
        root.getChildren().addAll(background);
    }

    private void setBackground(Paint paint){
        setBackground(new Background(WINDOW_WIDTH, WINDOW_HEIGHT, paint));
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
             
                camera.getChildren().add(enemy);
                enemies.add(enemy);
            }
    }
    
    @Override
    public void start(Stage window) {
        root = new Group();
        setBackground();
        camera = new Camera2D();
        
        setEnemies();
        
        root.getChildren().add(camera);
        
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        window.setTitle(TITLE);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
