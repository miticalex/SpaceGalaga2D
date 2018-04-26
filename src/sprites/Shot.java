package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Shot extends Sprite {

    private static final double VELOCITY = -5;
    private static final int RADIUS = 3;
    
    private Circle body;
    
    public Shot() {
        body = new Circle(0, 0, RADIUS);
        body.setFill(Color.RED);
        getChildren().addAll(body);
    }
    
    @Override
    public void update() {
        setTranslateY(getTranslateY() + VELOCITY);
    }
    
}
