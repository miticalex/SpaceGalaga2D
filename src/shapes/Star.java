package shapes;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author AM
 */
public class Star extends Polygon{
    private static final int MIN_RAYS = 4;
    private static final int MAX_RAYS = 8;
    
    private static final double MIN_OUTER_R = 10;
    private static final double MAX_OUTER_R = 20;
    
    private static final Random random = new Random();

    public static double getMAX_OUTER_R() {
        return MAX_OUTER_R;
    }
    
    private int numRays;
    private double outerR;
    private double innerR;

    public int getNumRays() {
        return numRays;
    }

    public double getOuterR() {
        return outerR;
    }

    public double getInnerR() {
        return innerR;
    }
    
    private void drawStar(int numRays, double outerR, double innerR){
        for (int i = 0; i < numRays; i++) {
            double x0 = Math.cos(i*Math.PI*2/numRays) * outerR;
            double y0 = Math.sin(i*Math.PI*2/numRays) * outerR;
            this.getPoints().addAll(x0, y0);
            
            double x1 = Math.cos((i+0.5)*Math.PI*2/numRays) * innerR;
            double y1 = Math.sin((i+0.5)*Math.PI*2/numRays) * innerR;
            this.getPoints().addAll(x1, y1);
        }
        
        this.setStroke(Color.BLACK);
        this.setFill(Color.YELLOW);
    }
    
    public final void setTranslate(double x, double y){
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
    
    public Star(){
        numRays = MIN_RAYS + random.nextInt(MAX_RAYS - MIN_RAYS);
        outerR = MIN_OUTER_R + random.nextDouble() * (MAX_OUTER_R - MIN_OUTER_R);
        innerR = outerR * (0.16 + random.nextDouble() * 0.4);
        
        drawStar(numRays, outerR, innerR);
        this.setRotate(90. * random.nextDouble());
    }
    
    public Star(double x, double y){
        this();
        setTranslate(x, y);
    }
    
    public Star(int numRays1){
        numRays = numRays1;
        outerR = MIN_OUTER_R + random.nextDouble() * (MAX_OUTER_R - MIN_OUTER_R);
        innerR = outerR * (0.16 + random.nextDouble() * 0.4);
        
        drawStar(numRays, outerR, innerR);
    }
    
    public Star(int numRays1, double outerR1, double innerR1){
        numRays = numRays1;
        outerR = outerR1;
        innerR = innerR1;
        
        drawStar(numRays, outerR, innerR);
    }
    
    public Star(int numRays, double outerR, double innerR, double rotationAngle){
        this(numRays, outerR, innerR);
        this.setRotate(rotationAngle);
    }
    
    public Star(double x, double y, int numRays, double outerR, double innerR){
        this(numRays, outerR, innerR);
        this.setTranslate(x, y);
    }
    public Star(double x, double y, int numRays, double outerR, double innerR, double rotationAngle){
        this(x, y, numRays, outerR, innerR);
        this.setRotate(rotationAngle);
    }
}
