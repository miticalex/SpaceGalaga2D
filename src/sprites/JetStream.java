package sprites;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author AM
 */
public class JetStream extends Sprite {
    Random random = new Random();
    private static Color[] colorScale;
    
    private static void createColorscale(){
        colorScale = new Color[101];
        
        double redRed = Color.RED.getRed(), 
            blueRed = Color.AQUA.getRed(),
            smokeRed = Color.LIGHTGOLDENRODYELLOW.getRed(),
            redGreen = Color.RED.getGreen(), 
            blueGreen = Color.AQUA.getGreen(), 
            smokeGreen = Color.LIGHTGOLDENRODYELLOW.getGreen(),
            redBlue = Color.RED.getBlue(), 
            blueBlue = Color.AQUA.getBlue(),
            smokeBlue = Color.LIGHTGOLDENRODYELLOW.getBlue();
        
        for (int i = 0; i < 20; i++) {
            colorScale[i] = Color.color(
                redRed,
                redGreen,
                redBlue,
                i*0.8/20.0);
        }
        for (int i = 0; i < 20; i++) {
            colorScale[20 + i] = Color.color(
                redRed*(20.0 - i)/20.0 + blueRed*i/20.0, 
                redGreen*(20.0 - i)/20.0 + blueGreen*i/20.0,
                redBlue*(20.0 - i)/20.0 + blueBlue*i/20.0, 
                0.8);
        }
        for (int i = 0; i < 40; i++) {
            colorScale[40 + i] = Color.color(
                blueRed*(40.0 - i)/40.0 + smokeRed*(i)/40.0, 
                blueGreen*(40.0 - i)/40.0 + smokeGreen*(i)/40.0,
                blueBlue*(40.0 - i)/40.0 + smokeBlue*(i)/40.0, 
                0.8);
        }
        for (int i = 0; i <= 20; i++) {
            colorScale[80+i] = Color.color(smokeRed, smokeGreen, smokeBlue, 0.8);
        }
    }
    
    private double width;
    private double intensity;
    
    //TODO - CONSIDER - Possible to implement through vertical 10 rectangles and linear gradient with 30 stops
    private final int[][] colorCodes = new int[10][30];
    private final Rectangle[][] molecules = new Rectangle[10][30];
    
    private void createJetStream(){
        for (int i = 0; i < 5; i++) {
            colorCodes[i][0] = 20*i + random.nextInt(20);
            colorCodes[9-i][0] = 20*i + random.nextInt(20);
        }
        for (int j = 1; j < 30; j++) {
            colorCodes[0][j] = 0;
            colorCodes[9][j] = 0;
        }
        for (int j = 1; j < 30; j++) {
            for (int i = 1; i < 9; i++) {
                colorCodes[i][j] = (colorCodes[i-1][j-1] + colorCodes[i][j-1] + colorCodes[i+1][j-1])/3 - 1;
                if (colorCodes[i][j] < 0) 
                    colorCodes[i][j] = 0;
            }
        }
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 30; j++) {
                molecules[i][j].setFill(colorScale[colorCodes[i][j]]);
                molecules[i][j].setStroke(null);
            }
        }
    }
        
    public JetStream(double width, double intensity) {
        createColorscale();
        
        this.width = width;
        this.intensity = intensity;
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 30; j++) {
                molecules[i][j] = new Rectangle(i*width/10, j*intensity/30, width/10, intensity/30);
                this.getChildren().add(molecules[i][j]);
            }
        }
        
        createJetStream();
    }

    @Override
    public void update() {
        createJetStream();
    }
    
}
