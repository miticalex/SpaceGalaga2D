package sprites;

import java.util.LinkedList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Sprite implements EventHandler<KeyEvent> {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 20;
    
    private static final int GUN_WIDTH = 6;
    private static final int GUN_HEIGHT = 10;
    
    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }
    
    private Rectangle body;
    private Rectangle gun;
    
    private List<Shot> shots = new LinkedList<>();

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> shots) {
        this.shots = shots;
    }
    
    public Player() {
        body = new Rectangle(0, 0, WIDTH, HEIGHT);
        body.setTranslateX(- WIDTH/2);
        body.setFill(Color.SKYBLUE);
        
        gun = new Rectangle(0, 0, GUN_WIDTH, GUN_HEIGHT);
        
        gun.setTranslateX(- GUN_WIDTH/2);
        gun.setTranslateY(- GUN_HEIGHT);
        gun.setFill(Color.SKYBLUE);
        
        this.getChildren().addAll(body, gun);
    }
    
    private void fireShot() {
        Shot shot = new Shot();
        shot.setTranslateX(getTranslateX());
        shot.setTranslateY(getTranslateY() - GUN_HEIGHT);
        shots.add(shot);
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void handle(KeyEvent event) {
        if ((event.getCode() == KeyCode.SPACE) && (event.getEventType() == KeyEvent.KEY_PRESSED)) {
            fireShot();
        }
    }
}
