import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.border.LineBorder;
public class Game extends JFrame
{
    private Gameplay gameplay;
    private Shop shopMenu;
    private Inventory playerInventory;
    private String current;
    public Game()
    {
        current = "game";
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(800, 600);
        setSize(1920,1080);
        setPreferredSize(new Dimension(1920, 1080));
        setLayout(null);
        setResizable(true);
        gameplay = new Gameplay();
        gameplay.setBounds(0,0,1920,1080);
        add(gameplay);
        gameplay.setVisible(true);
        pack();
        setVisible(true);
    }
}
