package sprites;

import spacegalaga2D.SpaceGalaga2D;

/**
 *
 * @author AM
 */
public class FallingCoin extends Coin{
    private double horizontalVelocity;
    private double verticalVelocity;
    
    public FallingCoin(double horV, double verV){ //(oblique projectile - kosi hitac
        super();
        
        horizontalVelocity = horV;
        verticalVelocity = verV;
    }
    
    public FallingCoin(double horV){ //horizontal projectile - horizontalni hitac
        this(horV, 0.0);
    }
    
    public FallingCoin(){ //free fall - slobodan pad
        this(0.0, 0.0);
    }
    
    @Override
    public void update() {
        super.update();
        
        double dt = 1/60.0;
        
        verticalVelocity = verticalVelocity + SpaceGalaga2D.getG()*dt;
        
        this.setTranslateX(this.getTranslateX() + horizontalVelocity*dt);
        this.setTranslateY(this.getTranslateY() + verticalVelocity*dt);
    }
}
