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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
     public void setPaint(Paint paint) { 
        Rectangle background = new Rectangle(width + 10.0, height + 10.0);
        background.setFill(paint);
        
        this.getChildren().setAll(background); 
    }
    
    public void setImage(Image image) { 
        setPaint(new ImagePattern(image));
    }
       
    public Background(int width, int height, Paint paint) {
        this.width = width;
        this.height = height;
        setPaint(paint);
    }

    public Background(int width, int height, Image image) {
        this(width, height, new ImagePattern(image));
    }
    
    public Background(int width, int height) {
        this(width, height, Color.BLACK);
    }
    
    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
