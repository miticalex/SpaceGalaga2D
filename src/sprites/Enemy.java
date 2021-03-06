package sprites;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Enemy extends Sprite {
    private static final int BODY_WIDTH = 50;
    private static final int BODY_HEIGHT = 40;
    private static final int BODY_CENTRE_X = BODY_WIDTH/2;
    private static final int BODY_CENTRE_Y = BODY_HEIGHT/2;
    
    private static final int DISAPPEARANCE_DURATION = 2;

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
    
    private boolean hit = false; 
    private double timeToLive = 2.0;
    private boolean dead = false;

    public boolean isHit() {
        return hit;
    }

    public boolean isDead() {
        return dead;
    }

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
    
    public void addLeftWink(){
        ScaleTransition stE = new ScaleTransition(Duration.seconds(2), lEye);
        stE.setFromY(1);
        stE.setToY(0.5);
        ScaleTransition stP = new ScaleTransition(Duration.seconds(2), lPupil);
        stP.setFromY(1);
        stP.setToY(0.5);
        
        ParallelTransition st = new ParallelTransition(stE, stP);
        st.setAutoReverse(true);
        st.setCycleCount(Animation.INDEFINITE);
        st.play();
    }
    
    public void addRightWink(){
        ScaleTransition stE = new ScaleTransition(Duration.seconds(2), rEye);
        stE.setFromY(1);
        stE.setToY(0.5);
        ScaleTransition stP = new ScaleTransition(Duration.seconds(2), rPupil);
        stP.setFromY(1);
        stP.setToY(0.5);
        
        ParallelTransition st = new ParallelTransition(stE, stP);
        st.setAutoReverse(true);
        st.setCycleCount(Animation.INDEFINITE);
        st.play();
    }
    
    private void rotateWings(){
        // TODO: change the way how pivot is set....
        RotateTransition rotLE = new RotateTransition(Duration.seconds(2), lEar);
        RotateTransition rotRE = new RotateTransition(Duration.seconds(2), rEar);
        
        lEar.getTransforms().add(new Translate(-(EAR_WIDTH+5)/2, 0));
        lEar.setTranslateX((EAR_WIDTH+5)/2);
        
        rotLE.setFromAngle(-20);
        rotLE.setToAngle(20);
        rotLE.setAutoReverse(true);
        rotLE.setCycleCount(Animation.INDEFINITE);
        rotLE.play();
        
        rEar.getTransforms().add(new Translate((EAR_WIDTH+5)/2, 0));
        rEar.setTranslateX(-(EAR_WIDTH+5)/2);
        rotRE.setFromAngle(20);
        rotRE.setToAngle(-20);
        rotRE.setAutoReverse(true);
        rotRE.setCycleCount(Animation.INDEFINITE);
        rotRE.play();
    }
    
    public Enemy() {
        addEars();
        addBody();
        addMouth();
        addEyes();
        
        rotateWings();
    }
    
    public Arrow fireArrow(){
        return new Arrow(this.getTranslateX() + BODY_WIDTH/2, 
                this.getTranslateY() + BODY_HEIGHT);
    }
    
    public void disappear(){
        hit = true;
        
        RotateTransition rotate = new RotateTransition(
                Duration.seconds(DISAPPEARANCE_DURATION), this);
        rotate.setFromAngle(0);
        rotate.setToAngle(720); //rotate 2 circles
        rotate.play();
        
        FadeTransition fadeAway = new FadeTransition(
                Duration.seconds(DISAPPEARANCE_DURATION), this);
        fadeAway.setFromValue(1);
        fadeAway.setToValue(0);
        fadeAway.setInterpolator(Interpolator.LINEAR);
        fadeAway.play();
    }
    
    @Override
    public void update() {
        if (hit){
            timeToLive-= 1./60;
            if (timeToLive < 0)
                dead = true;
        }
    }
    
}
