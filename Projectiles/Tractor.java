package Projectiles;

import Projectiles.Obstacle;

import java.awt.image.BufferedImage;
public class Tractor extends Obstacle
{
    public Tractor(int width, int height, BufferedImage icon)
    {
        super(2000,550,width,height,icon,"Projectiles.Tractor");
    }
    public Tractor(int width, int height, BufferedImage icon, int damage)
    {
        super(2000,550,width,height,icon,"Projectiles.Tractor",damage);
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
