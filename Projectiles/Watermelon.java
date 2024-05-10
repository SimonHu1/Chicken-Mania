package Projectiles;

import Projectiles.Obstacle;

import java.awt.image.BufferedImage;

public class Watermelon extends Obstacle
{
    private double yVelocity;

    public Watermelon(int width, int height, BufferedImage icon)
    {
        super(1920,(int)(Math.random()*1080),width,height,icon,"Projectiles.Watermelon");
//        super(1920,450,width,height,icon,"Projectiles.Watermelon");
    }
    public void setyVelocity(double y)
    {
        yVelocity = y;
    }
    public double getyVelocity()
    {
        return yVelocity;
    }
}
