package Projectiles;

import Projectiles.Obstacle;

import java.awt.image.BufferedImage;

public class Shovel extends Obstacle
{
    public Shovel(int xVal, int width, int height, BufferedImage icon)
    {
        super(xVal,(int)(Math.random()*780+100),width,height,icon,"Projectiles.Shovel");
    }
    public void moveLeft(int x)
    {
        super.setPos(getxVal()-x,getyVal());
    }
}
