package Projectiles;

import Projectiles.Obstacle;

import java.awt.image.BufferedImage;

public class Shovel extends Obstacle
{
    public Shovel(int xVal, int width, int height, BufferedImage icon)
    {
        super(xVal,(int)(Math.random()*780+100),width,height,icon,"Projectiles.Shovel");
    }
    public Shovel(int xVal, int width, int height, BufferedImage icon, int damage)
    {
        super(xVal,(int)(Math.random()*780+100),width,height,icon,"Projectiles.Shovel",damage);
    }
    public void moveLeft(int x)
    {
        if(getIsParried())
        {
            super.setPos(getxVal()+x,getyVal());
        }
        else
        {
            super.setPos(getxVal()-x,getyVal());
        }
    }
}
