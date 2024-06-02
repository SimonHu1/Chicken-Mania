package Projectiles;

import java.awt.image.BufferedImage;

public class Obstacle
{
    private int xVal;
    private int yVal;
    private int width;
    private int height;
    private BufferedImage icon;
    private String type;
    private boolean isParried;
    private int damage;
    public Obstacle(int xVal, int yVal, int width, int height, BufferedImage icon,String type)
    {
        this.xVal = xVal;
        this.yVal = yVal;
        this.width = width;
        this.height = height;
        this.icon = icon;
        this.type = type;
    }
    public Obstacle(int xVal, int yVal, int width, int height, BufferedImage icon,String type, int damage)
    {
        this.xVal = xVal;
        this.yVal = yVal;
        this.width = width;
        this.height = height;
        this.icon = icon;
        this.type = type;
        this.damage = damage;
    }
    public void setPos(int x, int y)
    {
        xVal = x;
        yVal = y;
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
    public String getType(){return type;}
    public String toString()
    {
        return xVal+","+yVal+":"+width+","+height+"\nType:"+type;
    }
    public boolean equals(Obstacle other)
    {
        return(type.equals(other.type)&&width==other.width&&height==other.height);
    }
    public int getDamage()
    {
         return damage;
    }
    public boolean getIsParried()
    {
        return isParried;
    }
    public void setParried(boolean b)
    {
        isParried = b;
    }
}
