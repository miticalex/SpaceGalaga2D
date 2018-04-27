package sprites;

import java.util.LinkedList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
    
    public static int getWIDTH() {
        return BODY_WIDTH;
    }

    public static int getHEIGHT() {
        return BODY_HEIGHT + GUN_HEIGHT;
    }
    
    private static enum Direction {LEFT, RIGHT, STILL}
    private static final double VELOCITY = 10;
    
    private Direction direction = Direction.STILL;
    private double velocity = 0;

    public Direction getDirection() {
        return direction;
    }

    public double getVelocity() {
        return velocity;
    }
     
    private boolean playerCamera = false;
    public boolean isPlayerCamera() {
        return playerCamera;
    }
    
//    private Rectangle body;
    private Path body;
//    private Rectangle gun;    
    private Arc gun;
    
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
        gun.setStrokeWidth(10);      
        
        this.getChildren().addAll(body, gun);
    }
    
    private void setVelocity() {
        switch (direction) {
            case LEFT:
                velocity = - VELOCITY;
                break;
            case RIGHT:
                velocity = VELOCITY;
                break;
            case STILL:
                velocity = 0;
                break;
            default:
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
        if (getTranslateX() + velocity < BODY_WIDTH / 2 + 5) {
            setTranslateX(BODY_WIDTH / 2 + 5);
        } else if (getTranslateX() + velocity > SpaceGalaga2D.getWINDOW_WIDTH() - BODY_WIDTH / 2 - 5) {
            setTranslateX(SpaceGalaga2D.getWINDOW_WIDTH() - BODY_WIDTH / 2 - 5);
        } else {
            setTranslateX(getTranslateX() + velocity);
        }
    }
    
    @Override
    public void handle(KeyEvent event) {
        if  (event.getEventType() == KeyEvent.KEY_PRESSED){
            switch (event.getCode()) {
                case SPACE:
                    fireShot();
                    break;
                case RIGHT:
                    direction = Direction.RIGHT;
                    setVelocity();
                    break;
                case LEFT:
                    direction = Direction.LEFT;
                    setVelocity();
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
        
        if ((event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) && event.getEventType() == KeyEvent.KEY_RELEASED) {
            direction = Direction.STILL;
            setVelocity();
        }
    }
}
