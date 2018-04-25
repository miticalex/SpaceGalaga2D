package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Sprite {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 20;
    
    private static final int GUN_WIDTH = 6;
    private static final int GUN_HEIGHT = 10;
    
    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
    
    private Rectangle body;
    private Rectangle gun;
    
    public Player() {
        body = new Rectangle(0, 0, WIDTH, HEIGHT);
        body.setTranslateX(- WIDTH/2);
        body.setFill(Color.SKYBLUE);
        
        gun = new Rectangle(0, 0, GUN_WIDTH, GUN_HEIGHT);
        
        gun.setTranslateX(- GUN_WIDTH/2);
        gun.setTranslateY(- GUN_HEIGHT);
        gun.setFill(Color.SKYBLUE);
        
        this.getChildren().addAll(body, gun);
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
