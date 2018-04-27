package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Enemy extends Sprite {
    private static final int BODY_WIDTH = 50;
    private static final int BODY_HEIGHT = 40;
    private static final int BODY_CENTRE_X = BODY_WIDTH/2;
    private static final int BODY_CENTRE_Y = BODY_HEIGHT/2;

    public static int getBODY_WIDTH() {
        return BODY_WIDTH;
    }

    public static int getBODY_HEIGHT() {
        return BODY_HEIGHT;
    }
    
    private static final int ARC_DIAMETER = 30;
    private static final int EAR_HEIGHT = 46;
    private static final int EAR_WIDTH = 30;
    
    private static final int MOUTH_WIDTH = 30;
    private static final int MOUTH_HEIGHT = 10;
    
    private Rectangle body;
    private Polygon lEar, rEar;
    private Arc mouth;
    private Ellipse lEye, rEye;
    private Circle lPupil, rPupil;
    
    private void addBody(){
        body = new Rectangle(0, 0, BODY_WIDTH, BODY_HEIGHT);
        body.setArcHeight(ARC_DIAMETER);
        body.setArcWidth(ARC_DIAMETER);
        body.setFill(Color.YELLOW);
        
        this.getChildren().add(body);
    }
    
    private void addEars(){
        lEar = new Polygon(-EAR_WIDTH,              BODY_CENTRE_Y - EAR_HEIGHT/2,
                            5,                      BODY_CENTRE_Y,
                           -EAR_WIDTH,              BODY_CENTRE_Y + EAR_HEIGHT/2);
        lEar.setFill(Color.SEASHELL);
        rEar = new Polygon(BODY_WIDTH + EAR_WIDTH,  BODY_CENTRE_Y - EAR_HEIGHT/2,  
                            BODY_WIDTH-5,           BODY_CENTRE_Y, 
                            BODY_WIDTH + EAR_WIDTH, BODY_CENTRE_Y + EAR_HEIGHT/2);
        rEar.setFill(Color.SEASHELL);
        
        this.getChildren().addAll(lEar, rEar);
    }
    
    private void addMouth(){
        mouth = new Arc(BODY_CENTRE_X, BODY_CENTRE_Y+5, MOUTH_WIDTH/2, MOUTH_HEIGHT, 180, 180);
        mouth.setType(ArcType.CHORD);
        mouth.setFill(Color.BLACK);
        
        this.getChildren().add(mouth);
    }
    
    private void addEyes(){
        lEye = new Ellipse(BODY_CENTRE_X - BODY_WIDTH/4.5, BODY_HEIGHT/3, 
                            BODY_WIDTH/7, BODY_HEIGHT/8);
        lEye.setFill(Color.WHITE);
        lEye.setStroke(Color.GREY);
        lEye.setStrokeWidth(0.5);
        lPupil = new Circle(BODY_CENTRE_X - BODY_WIDTH/4.5, BODY_HEIGHT/3, BODY_HEIGHT/20);
        lPupil.setFill(Color.BLACK);
        
        rEye = new Ellipse(BODY_CENTRE_X + BODY_WIDTH/4.5, BODY_HEIGHT/3,
                            BODY_WIDTH/7, BODY_HEIGHT/8);
        rEye.setFill(Color.WHITE);
        rEye.setStroke(Color.GREY);
        rEye.setStrokeWidth(0.5);
        rPupil = new Circle(BODY_CENTRE_X + BODY_WIDTH/4.5, BODY_HEIGHT/3, BODY_HEIGHT/20);
        rPupil.setFill(Color.BLACK);
        
        this.getChildren().addAll(lEye, lPupil, rEye, rPupil);
    }
    
    public Enemy() {
        addEars();
        addBody();
        addMouth();
        addEyes();
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
