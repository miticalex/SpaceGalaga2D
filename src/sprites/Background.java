package sprites;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author AM
 */
public class Background extends Sprite {
    private static final int BACKGROUND_DEPTH = 100;
    
    private int width;
    private int height;
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    private void addToGroup(Rectangle background){
        background.setTranslateZ(BACKGROUND_DEPTH);
        
        this.getChildren().setAll(background); 
    }
    
    public Background(int width1, int height1, Paint paint) {
        width = width1;
        height = height1;
        Rectangle background = new Rectangle(width, height, paint);
        
        addToGroup(background); 
    }

    public Background(int width, int height, Image image) {
        Rectangle background = new Rectangle(width, height, new ImagePattern(image));
        
        addToGroup(background); 
    }
    
    public Background(int width, int height) {
        this(width, height, Color.BLACK);
    }
    
    public void setImage(Image image) { 
        Rectangle background = new Rectangle(width, height);
        background.setFill(new ImagePattern(image));
        
        addToGroup(background); 
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
