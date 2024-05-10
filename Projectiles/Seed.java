package Projectiles;

import Projectiles.Obstacle;

import java.awt.image.BufferedImage;

public class Seed extends Obstacle
{
    public Seed(int xVal, int yVal, int width, int height, BufferedImage icon)
    {
        super(xVal,yVal,width,height,icon,"Projectiles.Seed");
    }
    public void moveLeft(int x)
    {
        super.setPos(getxVal()-x,getyVal());
    }
}
