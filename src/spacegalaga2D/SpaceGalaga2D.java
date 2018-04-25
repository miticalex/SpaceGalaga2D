/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacegalaga2D;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author AM
 */
public class SpaceGalaga2D extends Application {
    private static final String TITLE = "Space Galaga 2D - AM";
    
    private Group root;
    
    @Override
    public void start(Stage window) {
        root = new Group();
        
        Scene scene = new Scene(root, 300, 250);
        
        window.setTitle(TITLE);
        window.setScene(scene);
        window.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
