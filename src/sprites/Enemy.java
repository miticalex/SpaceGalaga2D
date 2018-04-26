package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Sprite {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 40;

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
    
    Rectangle body;
    
    public Enemy() {
        body = new Rectangle(0, 0, 50, 40);
        
        body.setFill(Color.YELLOW);
        this.getChildren().addAll(body);
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
