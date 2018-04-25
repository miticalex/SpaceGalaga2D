/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegalaga2D;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sprites.Background;

/**
 *
 * @author AM
 */
public class SpaceGalaga2D extends Application {
    private static final String TITLE = "Space Galaga 2D - AM";
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final boolean Z_BUFFER =false;
    
    public static int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public static int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }
    
    private Group root;
    private Background background;
    
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
    
    @Override
    public void start(Stage window) {
        root = new Group();
        
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Z_BUFFER);
        setBackground();
        
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
