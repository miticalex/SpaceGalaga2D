package sprites;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
//import shapes.Star;

/**
 *
 * @author AM
 */
public class Coin extends Sprite{
    private static final int WIDTH = 45;
    private static final int HEIGHT = 50;
    private static final int RIM_WIDTH = 2;

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
    
    private Ellipse rim;
    private Ellipse inside;
//    private Star star;
    
    public Coin(){
        rim = new Ellipse(WIDTH/2, HEIGHT/2);
        rim.setFill(null);
        rim.setStrokeWidth(RIM_WIDTH);
        rim.setStroke(Color.DARKGOLDENROD);
        
        inside = new Ellipse(WIDTH/2 - RIM_WIDTH, HEIGHT/2 - RIM_WIDTH);
        inside.setStroke(Color.GOLD);
        inside.setStrokeWidth(RIM_WIDTH);
        Stop[] hues = new Stop[]{
            new Stop(0.0, Color.YELLOW),
            new Stop(1.0, Color.DARKGOLDENROD)
        };
        RadialGradient insideFill = new RadialGradient
                (220, 0.3, 0.5, 0.5, 0.6, true, CycleMethod.NO_CYCLE, hues);
        inside.setFill(insideFill);
        
        //TODO: Consider adding a star imprint on the face of the coin
//        star = new Star(5, 6, 6);
//        star.setFill(insideFill);
//        star.setStroke(Color.GOLDENROD);
        
        this.getChildren().addAll(rim, inside);
    }

    @Override
    public void update() {
        this.setRotate(this.getRotate() + 4.0);
        
        Stop[] hues = new Stop[]{
            new Stop(0.0, Color.YELLOW),
            new Stop(1.0, Color.DARKGOLDENROD)
        };
        RadialGradient insideFill = new RadialGradient
                (200 - this.getRotate(), 0.5, 0.5, 0.5, 0.6, true, CycleMethod.NO_CYCLE, hues);
        inside.setFill(insideFill);
//        star.setFill(insideFill);
    }
    
}
