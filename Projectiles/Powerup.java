package Projectiles;

import Projectiles.Obstacle;

import java.awt.image.BufferedImage;

public class Powerup extends Obstacle
{
    private int power;
    public Powerup(int xVal, int yVal, int width, int height, BufferedImage icon, int power)
    {
        super(xVal,yVal,width,height,icon,"Projectiles.Powerup");
        this.power = power;
    }
    public void moveLeft(int x)
    {
        super.setPos(getxVal()-x,getyVal());
    }
    public int getPower()
    {
        return power;
    }
}
