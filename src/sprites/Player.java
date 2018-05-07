package sprites;

import java.util.LinkedList;
import java.util.List;
import javafx.event.EventHandler;
//import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import spacegalaga2D.SpaceGalaga2D;

public class Player extends Sprite implements EventHandler<KeyEvent> {
    private static final int BODY_WIDTH = 50;
    private static final int BODY_HEIGHT = 30;
    
    private static final int GUN_WIDTH = 20;
    private static final int GUN_HEIGHT = 20;
    private static final int GUN_STROKE_WIDTH = 10;
    
    private static final int JET_PIPE_WIDTH = 20;
    private static final int JET_PIPE_HEIGHT = 20;
    private static final int JET_INTENSITY = 30;
    
    private static final double VELOCITY = 10;
    
    public static int getWidth() {
        return BODY_WIDTH;
    }

    public static int getHeight() {
        return BODY_HEIGHT + GUN_HEIGHT + GUN_STROKE_WIDTH/2;
    }
    
    private static boolean rightArrowDown = false;
    private static boolean leftArrowDown = false;
    private static boolean upArrowDown = false;
    private static boolean downArrowDown = false;
    
    private double horizontalVelocity = 0;
    private double verticalVelocity = 0;

    public double getHorizontalVelocity() {
        return horizontalVelocity;
    }

    public double getVerticalVelocity() {
        return verticalVelocity;
    }
    
    private boolean playerCamera = false;
    public boolean isPlayerCamera() {
        return playerCamera;
    }
    
//    private Rectangle body;
    private Path body;
//    private Rectangle gun;    
    private Arc gun;
    private Rectangle leftJetPipe, rightJetPipe;
    private JetStream leftJet, rightJet;
    
    private List<Shot> shots = new LinkedList<>();

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> shots) {
        this.shots = shots;
    }
    
    public Player() {
//        body = new Rectangle(0, 0, WIDTH, HEIGHT);
//        body.setTranslateX(- WIDTH/2);
        body = new Path();
        MoveTo moveTo = new MoveTo(-BODY_WIDTH/2, 0);
        ArcTo arcTo1 = new ArcTo(BODY_WIDTH*0.55, BODY_WIDTH*0.45, 0, BODY_WIDTH/2, 0, false, true);
        ArcTo arcTo2 = new ArcTo(BODY_WIDTH*0.55, BODY_WIDTH*0.4, 0, -BODY_WIDTH/2, 0, true, false);
        ClosePath closePath = new ClosePath();
        body.getElements().addAll(moveTo, arcTo1, arcTo2, closePath);
        body.setFill(Color.SKYBLUE);
        body.setStroke(null);
        
//        gun = new Rectangle(0, 0, GUN_WIDTH, GUN_HEIGHT);     
//        gun.setTranslateX(- GUN_WIDTH/2);
//        gun.setTranslateY(- GUN_HEIGHT);
        gun = new Arc(0, - BODY_HEIGHT, GUN_WIDTH/2, GUN_HEIGHT, 0, 180);
        gun.setFill(Color.BLACK);
        gun.setStroke(Color.SKYBLUE);
        gun.setStrokeWidth(GUN_STROKE_WIDTH);
        
        LinearGradient pipesColor = new LinearGradient(0, 0, 0.5, 0, true, 
                CycleMethod.REFLECT, 
                new Stop[] {
                    new Stop(0.0, Color.DARKRED),
                    new Stop(0.8, Color.GOLD)// LIGHTSALMON
                });
        
        leftJetPipe = new Rectangle(-BODY_WIDTH*0.55-JET_PIPE_WIDTH, -JET_PIPE_HEIGHT, 
                                    JET_PIPE_WIDTH, JET_PIPE_HEIGHT);
        leftJetPipe.setStroke(null);
        leftJetPipe.setFill(pipesColor);
        rightJetPipe = new Rectangle(BODY_WIDTH*0.55, -JET_PIPE_HEIGHT, 
                                    JET_PIPE_WIDTH, JET_PIPE_HEIGHT);
        rightJetPipe.setStroke(null);
        rightJetPipe.setFill(pipesColor);
        
        leftJet = new JetStream(JET_PIPE_WIDTH, JET_INTENSITY);
        leftJet.setTranslateX(-BODY_WIDTH*0.55-20);
        rightJet = new JetStream(JET_PIPE_WIDTH, JET_INTENSITY);
        rightJet.setTranslateX(BODY_WIDTH*0.55);
        
        this.getChildren().addAll(body, gun, leftJetPipe, rightJetPipe, leftJet, rightJet);
    }
    
    private void setVelocity() {
        if ((rightArrowDown == false && leftArrowDown == false)
           || (rightArrowDown == true && leftArrowDown == true)) {
            horizontalVelocity = 0;
        } else if (rightArrowDown == true){
            horizontalVelocity = VELOCITY;
        } else if (leftArrowDown == true){
            horizontalVelocity = - VELOCITY;
        } else {
            throw new AssertionError();
        }
        
        if ((upArrowDown == false && downArrowDown == false)
           || (upArrowDown == true && downArrowDown == true)) {
            verticalVelocity = 0;
        } else if (upArrowDown == true){
            verticalVelocity = - VELOCITY;
        } else if (downArrowDown == true){
            verticalVelocity = VELOCITY;
        } else {
            throw new AssertionError();
        }
    }
    
    private void fireShot() {
        Shot shot = new Shot();
        shot.setTranslateX(getTranslateX());
        shot.setTranslateY(getTranslateY() - BODY_HEIGHT - GUN_HEIGHT - 5);
        shots.add(shot);
    }
    
    @Override
    public void update() {
        if (getTranslateX() + horizontalVelocity < BODY_WIDTH / 2 + 5) {
            setTranslateX(BODY_WIDTH / 2 + 5);
        } else if (getTranslateX() + horizontalVelocity > SpaceGalaga2D.getWINDOW_WIDTH() - BODY_WIDTH / 2 - 5) {
            setTranslateX(SpaceGalaga2D.getWINDOW_WIDTH() - BODY_WIDTH / 2 - 5);
        } else {
            setTranslateX(getTranslateX() + horizontalVelocity);
        }
        
        if (getTranslateY() + verticalVelocity < getHeight() + 5) {
            setTranslateY(getHeight() + 5);
        } else if (getTranslateY() + verticalVelocity > SpaceGalaga2D.getWINDOW_HEIGHT() - 5) {
            setTranslateY(SpaceGalaga2D.getWINDOW_HEIGHT() - 5);
        } else {
            setTranslateY(getTranslateY() + verticalVelocity);
        }
        
        leftJet.update();
        rightJet.update();
    }
    
    @Override
    public void handle(KeyEvent event) {
        if  (event.getEventType() == KeyEvent.KEY_PRESSED){
            switch (event.getCode()) {
                case SPACE:
                    fireShot();
                    break;
                case RIGHT:
                    rightArrowDown = true;
                    break;
                case LEFT:
                    leftArrowDown = true;
                    break;
                case UP:
                    upArrowDown = true;
                    break;
                case DOWN:
                    downArrowDown = true;
                    break;
                case DIGIT1:
                    playerCamera = false;
                    break; 
                case DIGIT2:
                    playerCamera = true;
                    break;
                default:
                    throw new AssertionError();
            }
        }
        
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            switch (event.getCode()) {
                case RIGHT:
                    rightArrowDown = false;
                    break;
                case LEFT:
                    leftArrowDown = false;
                    break;
                case UP:
                    upArrowDown = false;
                    break;
                case DOWN:
                    downArrowDown = false;
                    break;
                default:
                    throw new AssertionError();
            }
        }
        
        setVelocity();
    }
}
