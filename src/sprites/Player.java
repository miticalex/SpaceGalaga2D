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
    private static final int GUN_STROKE_WIDTH = 10;
    
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
    
    private static enum VerticalDirection {UP, DOWN, STILL}
    private static enum HorizontalDirection {LEFT, RIGHT, STILL}
    
    private HorizontalDirection horizontalDirection = HorizontalDirection.STILL;
    private VerticalDirection verticalDirection = VerticalDirection.STILL;
    private double velocity = 0;
    private double verticalVelocity = 0;

    public VerticalDirection getVerticalDirection() {
        return verticalDirection;
    }
    
    public HorizontalDirection getHorizontalDirection() {
        return horizontalDirection;
    }

    public double getVelocity() {
        return velocity;
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
        
        this.getChildren().addAll(body, gun);
    }
    
    private void setVelocity() {
        switch (horizontalDirection) {
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
    
        switch (verticalDirection) {
            case UP:
                verticalVelocity = - VELOCITY;
                break;
            case DOWN:
                verticalVelocity = VELOCITY;
                break;
            case STILL:
                verticalVelocity = 0;
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
        
        if (getTranslateY() + verticalVelocity < getHeight() + 5) {
            setTranslateY(getHeight() + 5);
        } else if (getTranslateY() + verticalVelocity > SpaceGalaga2D.getWINDOW_HEIGHT() - 5) {
            setTranslateY(SpaceGalaga2D.getWINDOW_HEIGHT() - 5);
        } else {
            setTranslateY(getTranslateY() + verticalVelocity);
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
                    rightArrowDown = true;
                    if (leftArrowDown){
                        horizontalDirection = HorizontalDirection.STILL;
                    } else {
                        horizontalDirection = HorizontalDirection.RIGHT;
                    }
                    setVelocity();
                    break;
                case LEFT:
                    leftArrowDown = true;
                    if (rightArrowDown){
                        horizontalDirection = HorizontalDirection.STILL;
                    } else {
                        horizontalDirection = HorizontalDirection.LEFT;
                    }
                    setVelocity();
                    break;
                case UP:
                    upArrowDown = true;
                    if (downArrowDown){
                        verticalDirection = VerticalDirection.STILL;
                    } else {
                        verticalDirection = VerticalDirection.UP;
                    }
                    setVelocity();
                    break;
                case DOWN:
                    downArrowDown = true;
                    if (upArrowDown){
                        verticalDirection = verticalDirection.STILL;
                    } else {
                        verticalDirection = VerticalDirection.DOWN;
                    }
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
        
        if (event.getEventType() == KeyEvent.KEY_RELEASED){
            switch (event.getCode()) {
                case RIGHT:
                    rightArrowDown = false;
                    if (leftArrowDown){
                        horizontalDirection = HorizontalDirection.LEFT;
                    } else {
                        horizontalDirection = HorizontalDirection.STILL;
                    }
                    setVelocity();
                    break;
                case LEFT:
                    leftArrowDown = false;
                    if (rightArrowDown){
                        horizontalDirection = HorizontalDirection.RIGHT;
                    } else {
                        horizontalDirection = HorizontalDirection.STILL;
                    }
                    setVelocity();
                    break;
                case UP:
                    upArrowDown = false;
                    if (downArrowDown){
                        verticalDirection = VerticalDirection.DOWN;
                    } else {
                        verticalDirection = VerticalDirection.STILL;
                    }
                    setVelocity();
                    break;
                case DOWN:
                    downArrowDown = false;
                    if (upArrowDown){
                        verticalDirection = verticalDirection.UP;
                    } else {
                        verticalDirection = VerticalDirection.STILL;
                    }
                    setVelocity();
                    break;
                default:
                    throw new AssertionError();
            }
        }
//        if ((event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) 
//                && event.getEventType() == KeyEvent.KEY_RELEASED) {
//            horizontalDirection = HorizontalDirection.STILL;
//            setVelocity();
//        }
//        if ((event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) 
//                && event.getEventType() == KeyEvent.KEY_RELEASED) {
//            
//            verticalDirection = VerticalDirection.STILL;
//            setVelocity();
//        }
    }
}
