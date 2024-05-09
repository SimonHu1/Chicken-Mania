import java.awt.image.BufferedImage;

public class Seed extends Obstacle
{
    public Seed(int xVal, int yVal, int width, int height, BufferedImage icon)
    {
        super(xVal,yVal,width,height,icon,"Seed");
    }
    public void moveLeft(int x)
    {
        super.setPos(getxVal()-x,getyVal());
    }
}
