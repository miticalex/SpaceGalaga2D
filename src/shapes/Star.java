package shapes;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

/**
 *
 * @author AM
 */
public class Star extends Polyline{
    private static final int MIN_POINTS = 4;
    private static final int MAX_POINTS = 8;
    
    private static final double MIN_OUTER_R = 10;
    private static final double MAX_OUTER_R = 20;

    public static double getMAX_OUTER_R() {
        return MAX_OUTER_R;
    }
    
    private int numPoints;
    private double outerR;
    private double innerR;

    public int getNumPoints() {
        return numPoints;
    }

    public double getOuterR() {
        return outerR;
    }

    public double getInnerR() {
        return innerR;
    }
    
    public Star(){
        Random random = new Random();
        
        numPoints = MIN_POINTS + random.nextInt(MAX_POINTS - MIN_POINTS);
        outerR = MIN_OUTER_R + random.nextDouble() * (MAX_OUTER_R - MIN_OUTER_R);
        innerR = outerR * (0.16 + random.nextDouble() * 0.4);
        
        for (int i = 0; i < numPoints; i++) {
            double x0 = Math.cos(i*Math.PI*2/numPoints) * outerR;
            double y0 = Math.sin(i*Math.PI*2/numPoints) * outerR;
            this.getPoints().addAll(x0, y0);
            
            double x1 = Math.cos((i+0.5)*Math.PI*2/numPoints) * innerR;
            double y1 = Math.sin((i+0.5)*Math.PI*2/numPoints) * innerR;
            this.getPoints().addAll(x1, y1);
        }
        
        this.setStroke(Color.BLACK);
        this.setFill(Color.YELLOW);
        this.setRotate(90. * random.nextDouble());
    }
    
    public Star(double x, double y){
        this();
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
