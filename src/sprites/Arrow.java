package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

/**
 *
 * @author AM
 */
public class Arrow extends Sprite{
    private static final double ARROW_LENGTH = 15;
    private static final double ARROW_WIDTH = 3;
    
    private Line stem;
    private Polyline head;
    
    public Arrow(){
//        stem = new Line(0.0, 0.0, 
//                        0.0, ARROW_LENGTH);
        stem = new Line(0.0, 0.0, 
                        ARROW_LENGTH, 0.0);
        stem.setStroke(Color.SANDYBROWN);
        stem.setStrokeWidth(ARROW_WIDTH);
        
//        head = new Polyline(-ARROW_LENGTH/5, 0.8*ARROW_LENGTH, 
//                            0.0, ARROW_LENGTH, 
//                            ARROW_LENGTH/5, 0.8*ARROW_LENGTH);
        head = new Polyline(0.8*ARROW_LENGTH, -ARROW_LENGTH/5, 
                            ARROW_LENGTH, 0.0, 
                            0.8*ARROW_LENGTH, ARROW_LENGTH/5);
        head.setStroke(Color.RED);
        head.setStrokeWidth(ARROW_WIDTH);
        
        this.getChildren().addAll(stem, head);
    }
    
    public Arrow(double x, double y){
        this();
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
