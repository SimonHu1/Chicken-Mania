package Projectiles;

import java.awt.image.BufferedImage;

public class Effect
{
    private int xVal;
    private int yVal;
    private int width;
    private int height;
    private BufferedImage icon;
    private double time;
    public Effect(int xVal, int yVal, int width, int height, BufferedImage icon,double time)
    {
        this.xVal = xVal;
        this.yVal = yVal;
        this.width = width;
        this.height = height;
        this.icon = icon;
        this.time = time;
    }
    public void changeTime(double v)
    {
        time+=v;
    }
    public double getTime()
    {
        return time;
    }
    public int getxVal()
    {
        return xVal;
    }
    public BufferedImage getIcon()
    {
        return icon;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public int getyVal()
    {
        return yVal;
    }
}
