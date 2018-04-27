package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Shot extends Sprite {
    private static final double VELOCITY = -5;
    private static final double ROT_VELOCITY = 5;
    //private static final int RADIUS = 3;
    
    //private Circle body;
    private Polygon body;
    
    public Shot() {
        //body = new Circle(0, 0, RADIUS);
        body = new Polygon(0,8, -5,3, -5,-3, 0,-8, 5,-3, 5,3);
        body.setFill(Color.RED);
        getChildren().addAll(body);
    }
    
    @Override
    public void update() {
        setTranslateY(getTranslateY() + VELOCITY);
        setRotate(getRotate() + ROT_VELOCITY);
    }
    
}
