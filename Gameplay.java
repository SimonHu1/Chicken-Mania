import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.io.IOException;
import java.util.ArrayList;

public class Gameplay extends JLayeredPane implements ActionListener {
    private Inventory playerInventory;
    private int characterY = 200; // Initial character position
    private double yVelocity = 0; // Initial vertical velocity
    private double gravity = 10; // Gravity acceleration
    private int jumpCount;
    private final int PIXELS_PER_METER = 50; // Conversion factor for physics simulation
    private double airTime = 0.0; // Time in the air
    private double timeUntilNextJump = 0.0;
    private double time;
    private int cd;
    private BufferedImage characterImage;
    private BufferedImage shovelImage;
    private BufferedImage watermelonImage;
    private BufferedImage tractorImage;
    private BufferedImage seedImage;
    private BufferedImage backgroundImage;
    private BufferedImage archaicCallSymbol;
    private BufferedImage proteggtionSymbol;
    private BufferedImage seedGaloreSymbol;
    private BufferedImage egg;
    private Timer timer;
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private int lives;
    private JButton shopButton = new JButton("SHOP");
    private JButton playAgainButton = new JButton("PLAY AGAIN");
    private JTextField deathText = new JTextField();
    private boolean run;
    private int seedsThisRun;
    private boolean containsTractor;
    private JPanel endScreen = new JPanel();
    private double[] activePowerups = new double[3]; //1.0 = Proteggtion, 2.0 = pecking machine, 3.0 = Archaic Call
    private Shop shop;
    private int eggShield;
    public Gameplay(Inventory playerInventory,Shop shop) {
        jumpCount=9;
        this.shop = shop;
        this.playerInventory = playerInventory;
        time=0.0;
        seedsThisRun=0;
        run = true;
        inititalizeEndScreen();
        lives = 3;
        timer = new Timer(5, this); // 200 FPS (1000 ms / 200)
        timer.start();
        setFocusable(true); // Allow the panel to receive keyboard input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    jump();
                }
            }
        });
        try {
            characterImage = ImageIO.read(new File("Images/ThingieRooster.png"));
            shovelImage = ImageIO.read(new File("Images/Shovel.png"));
            watermelonImage = ImageIO.read(new File("Images/Watermelon.png"));
            tractorImage = ImageIO.read(new File("Images/Tractor.png"));
            seedImage = ImageIO.read(new File("Images/Seed.png"));
            backgroundImage = ImageIO.read(new File("Images/Background.png"));
            archaicCallSymbol = ImageIO.read(new File("Images/Archaic Call Symbol.png"));
            proteggtionSymbol = ImageIO.read(new File("Images/Proteggtion Symbol.png"));
            seedGaloreSymbol = ImageIO.read(new File("Images/Seed Galore Symbol.png"));
            egg = ImageIO.read(new File("Images/Egg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(new Font("Monospaced",Font.BOLD,50));
        g2d.fillRect(0, 0, getWidth(), getHeight()); // Clear the screen
        g2d.drawImage(backgroundImage,0,-100,1920,1080,null);
        g2d.drawString("Seeds: "+seedsThisRun,0,50);
        if(eggShield>0.0)
        {
            g2d.drawString("Lives: "+lives+"(+"+eggShield+")",0,100);
        }
        else
        {
            g2d.drawString("Lives: "+lives,0,100);
        }
        g2d.drawString("Jumps: "+jumpCount,0,150);
        if (characterImage != null) {
            g2d.drawImage(characterImage, 100, characterY, 70, 70, null);
        }
        System.out.println(eggShield);
        if(eggShield<0) eggShield=0;
        if(eggShield==0) activePowerups[0]=0.0;
        if(eggShield>0.0)
        {
            g2d.drawImage(egg,100,characterY,80,80,null);
        }
        for(Obstacle o: obstacles)
        {
            g2d.drawImage(o.getIcon(),o.getxVal(),o.getyVal(),o.getWidth(),o.getHeight(),null);
        }
        g2d.setFont(new Font("Monospaced",Font.BOLD,20));
        for(int i = 0; i<3; i++)
        {
            if(activePowerups[i]>0.0)
            {
                if(i==0)
                {
                    g2d.drawImage(proteggtionSymbol,1650,0,100,100,null);
                    g2d.drawString((int)(activePowerups[i]*100)/100+"",1730,120);
                }
                if(i==1)
                {
                    g2d.drawImage(seedGaloreSymbol,1750,0,100,100,null);
                    g2d.drawString((int)(activePowerups[i]*100)/100+"",1830,120);
                }
            }
        }
        g2d.setFont(new Font("Monospaced",Font.BOLD,50));
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!run) return;
        if(lives<=0)
        {
            selectionSorter(obstacles);
            insertionSorter(obstacles);
            endScreen.setVisible(true);
            run = false;
            deathText.setText("SEEDS RECEIVED: " + seedsThisRun);
            int seedsReceived = seedsThisRun;
            playerInventory.setSeed(playerInventory.getSeed()+seedsReceived);
        }
        if(time>999)
        {
            Obstacle[][] endAll = new Obstacle[][]{{randomShovel(),null,null},{null,null,null},{null,null,null}};
            for(int r = 0; r<endAll.length;r++)
            {
                for(int c = 0; c<endAll[0].length;c++)
                {
                    endAll[r][c] = randomShovel();
                }
            }
            for(int c = 0; c<endAll[0].length;c++)
            {
                for(int r = 0; r<endAll.length;r++)
                {
                    obstacles.add(endAll[r][c]);
                }
            }
            for(Obstacle[] o: endAll)
            {
                for(Obstacle ob: o)
                {
                    System.out.println(ob);
                    System.out.println(ob.equals(ob));
                }
            }
        }
        update();
        repaint();
        if(time>2)
        {
            if(Math.random()*1000>=1000-((time+3)/3))
            {
                obstacles.add(new Shovel(2000+(int)(Math.random()*620),324,72,shovelImage));
                System.out.println("Shovel made");
                System.out.println(obstacles.size());
            }
            if(Math.random()*1000>=1000-(time/10)&&!containsTractor&&time>20)
            {
                obstacles.add(new Tractor(576,420,tractorImage));
                System.out.println("Tractor made");
                System.out.println(obstacles.size());
                containsTractor=true;
            }
            if(Math.random()*1000>=1000-(time/8)&&time>40)
            {
                obstacles.add(new Watermelon(100,100,watermelonImage));
                ((Watermelon)obstacles.get(obstacles.size()-1)).setyVelocity((double) ((characterY-(obstacles.get(obstacles.size()-1)).getyVal())*PIXELS_PER_METER-41405)/91);
                System.out.println("Watermelon made");
                System.out.println(obstacles.size());
            }
            generateSeeds();
            generatePowerup();
        }
        checkHit();
        updateObstacles();
    }

    public void update() {
        yVelocity += gravity;
        airTime+=0.1;
        time+=0.015;
        for(int i = 0; i<2; i++)
        {
            if(activePowerups[i]>0.0)
            {
                activePowerups[i]-=0.015;
                if(activePowerups[i]<0.0)
                {
                    activePowerups[i]=0.0;
                    if(i==0)
                    {
                        eggShield=0;
                    }
                }
            }
        }
        if(eggShield<0) eggShield=0;
        if(eggShield==0) activePowerups[0]=0.0;
        cd-=1;
        timeUntilNextJump+=0.1;
        double deltaY = yVelocity / PIXELS_PER_METER;
        characterY += deltaY;

        if (characterY >= getHeight() - 180) {
            characterY = getHeight() - 180;
            yVelocity = 0;
            airTime = 0.0;
            timeUntilNextJump = 0.0;
            jumpCount = 9+playerInventory.getUpgrades()[4]*3;
        }
        if(characterY <= 0)
        {
            characterY = 1;
            yVelocity = 0;
        }
    }
    public void jump() {
        if ((characterY == getHeight() - 180)||(jumpCount>0&&airTime>0.5&&timeUntilNextJump>1.0))
        {
            yVelocity = -7 * PIXELS_PER_METER;
            jumpCount--;
            timeUntilNextJump = 0.0;
        }
    }
    public void updateObstacles()
    {
        if(obstacles.size()==0) return;
        for(int i = obstacles.size()-1; i>=0;i--)
        {
            if(obstacles.get(i).getType().equals("Shovel"))
            {
                ((Shovel)obstacles.get(i)).moveLeft((int)(8+Math.pow(time,0.5)));
                if(obstacles.get(i).getxVal()<-600)
                {
                    obstacles.remove(i);
                    System.out.println("shovel deleted");
                    System.out.println(obstacles.size());
                }
            }
            else if(obstacles.get(i).getType().equals("Tractor"))
            {
                ((Tractor)obstacles.get(i)).moveLeft((int)(15+Math.pow(time,0.5)));
                if(obstacles.get(i).getxVal()<-1500)
                {
                    obstacles.remove(i);
                    containsTractor=false;
                    System.out.println("shovel deleted");
                    System.out.println(obstacles.size());
                }
            }
            else if(obstacles.get(i).getType().equals("Watermelon"))
            {
                ((Watermelon)obstacles.get(i)).setyVelocity(((Watermelon)obstacles.get(i)).getyVelocity()+gravity);
                (obstacles.get(i)).setPos((obstacles.get(i)).getxVal()-20,(obstacles.get(i)).getyVal()+(int)(((Watermelon)obstacles.get(i)).getyVelocity()/PIXELS_PER_METER));
                if(obstacles.get(i).getyVal()>1200)
                {
                    obstacles.remove(i);
                }
            }
            else if(obstacles.get(i).getType().equals("Seed"))
            {
                ((Seed)obstacles.get(i)).moveLeft((int)(8+Math.pow(time,0.5)));
                if(obstacles.get(i).getxVal()<0)
                {
                    obstacles.remove(i);
                    System.out.println("seed deleted");
                    System.out.println(obstacles.size());
                }
            }
            else if(obstacles.get(i).getType().equals("Powerup"))
            {
                ((Powerup)obstacles.get(i)).moveLeft((int)(8+Math.pow(time,0.5)));
                if(obstacles.get(i).getxVal()<0)
                {
                    obstacles.remove(i);
                    System.out.println("powerup deleted");
                    System.out.println(obstacles.size());
                }
            }
            if(obstacles.size()==0) return;
        }
    }
    public void checkHit()
    {
        if(eggShield<0) eggShield=0;
        if(eggShield==0) activePowerups[0]=0.0;
        for (Obstacle o : obstacles) {
            if (o.getxVal() <= 170 && o.getxVal() + o.getWidth() >= 100){
                if(o.getyVal() <= characterY + 70 && o.getyVal() + o.getHeight() >= characterY){
                    if(o.getType().equals("Shovel"))
                    {
                        ((Shovel)o).moveLeft(1000);
                        if(cd<0)
                        {
                            if(eggShield>0)
                            {
                                eggShield-=2;
                            }
                            else {
                                lives-=2;
                            }
                            cd=20;
                            System.out.println("Hit detected");
                        }
                    }
                    if(o.getType().equals("Tractor"))
                    {
                        ((Tractor)o).moveLeft(1000);
                        if(cd<0)
                        {
                            if(eggShield>0)
                            {
                                eggShield-=3;
                            }
                            else {
                                lives-=3;
                            }
                            cd=20;
                            System.out.println("Hit detected");
                        }
                    }
                    if(o.getType().equals("Watermelon"))
                    {
                        o.setPos(0,5000);
                        if(cd<0)
                        {
                            if(eggShield>0)
                            {
                                eggShield--;
                            }
                            else {
                                lives--;
                            }
                            cd=20;
                            System.out.println("Hit detected");
                        }
                    }
                    if(o.getType().equals("Seed"))
                    {
                        ((Seed)o).moveLeft(1000);
                        if(activePowerups[1]>0.0)
                        {
                            seedsThisRun+=2;
                        }
                        else {
                            seedsThisRun++;
                        }
                    }
                    if(o.getType().equals("Powerup"))
                    {
                        activatePowerup(((Powerup)o).getPower());
                        ((Powerup)o).moveLeft(1000);
                    }
                }
            }
        }
    }
    public void inititalizeEndScreen()
    {
        add(endScreen);
        endScreen.add(deathText);
        endScreen.add(shopButton);
        endScreen.add(playAgainButton);
        endScreen.setVisible(false);
        endScreen.setLayout(null);
        endScreen.setBounds(560,200,800,700);
        endScreen.setBackground(new Color(224, 181, 61));
        endScreen.setBorder(new LineBorder(Color.BLACK,12));
        deathText.setBounds(100,100,2000,100);
        deathText.setOpaque(false);
        deathText.setEditable(false);
        deathText.setBorder(null);
        deathText.setFont(new Font("Monospaced", Font.BOLD, 60));
        deathText.setForeground(new Color(46, 31, 0));
        deathText.setText("SEEDS RECEIVED: " + seedsThisRun);
        initializeButtons();
    }
    public void initializeButtons()
    {
        shopButton.setFont(new Font("Monospaced", Font.BOLD, 60));
        shopButton.addActionListener(this::goShop);
        shopButton.setBounds(200,300,400,100);
        shopButton.setBackground(Color.red);
        shopButton.setForeground(new Color(46, 31, 0));
        shopButton.setBorder(new LineBorder(new Color(46, 31, 0),8));
        shopButton.setFocusPainted(false);
        playAgainButton.setFont(new Font("Monospaced", Font.BOLD, 60));
        playAgainButton.addActionListener(this::playAgain);
        playAgainButton.setBounds(200,450,400,100);
        playAgainButton.setBackground(Color.red);
        playAgainButton.setBorder(new LineBorder(new Color(46, 31, 0),8));
        playAgainButton.setForeground(new Color(46, 31, 0));
        playAgainButton.setFocusPainted(false);
    }
    public void generateSeeds()
    {
        if((int)(time*10000)/100%300==0)
        {
            double random = Math.random();
            if(random>0.75)
            {
                int randomY = (int)(Math.random()*780+100);
                obstacles.add(new Seed(2150,randomY,50,50, seedImage));
                obstacles.add(new Seed(2200,randomY,50,50, seedImage));
                obstacles.add(new Seed(2250,randomY,50,50, seedImage));
            }
            else if(random>0.5)
            {
                obstacles.add(new Seed(2150,400,50,50, seedImage));
                obstacles.add(new Seed(2200,450,50,50, seedImage));
                obstacles.add(new Seed(2250,500,50,50, seedImage));
            }
            else if(random>0.25)
            {
                obstacles.add(new Seed(2150,500,50,50, seedImage));
                obstacles.add(new Seed(2200,450,50,50, seedImage));
                obstacles.add(new Seed(2250,400,50,50, seedImage));
            }
            else
            {
                int randomY = (int)(Math.random()*780+100);
                obstacles.add(new Seed(2100,(int)(Math.random()*780+100),50,50, seedImage));
                obstacles.add(new Seed(2100,(int)(Math.random()*780+100),50,50, seedImage));
                obstacles.add(new Seed(2100,(int)(Math.random()*780+100),50,50, seedImage));
            }
        }
    }
    public void generatePowerup()
    {
        if((int)(time*10000)/100%1200==0)
        {
            double random = Math.random();
            if(random>0.666)
            {
                obstacles.add(new Powerup(2150,(int)(Math.random()*780+100),75,75,proteggtionSymbol,0));
            }
            else if(random>0.333)
            {
                obstacles.add(new Powerup(2150,(int)(Math.random()*780+100),75,75,seedGaloreSymbol,1));
            }
            else
            {
                obstacles.add(new Powerup(2150,(int)(Math.random()*780+100),75,75,archaicCallSymbol,2));
            }
        }
    }
    public void activatePowerup(int n)
    {
        if(n==0)
        {
            eggShield=3+playerInventory.getUpgrades()[0];
            activePowerups[0]=5.0+playerInventory.getUpgrades()[0]*5;
        }
        else if(n==1)
        {
            activePowerups[1]=5.0+playerInventory.getUpgrades()[1]*5;
        }
        else {
            int i = obstacles.size();
            for(int b = i-1;b>=0&&b>=obstacles.size()-(playerInventory.getUpgrades()[2]+3);b--)
            {
                if(obstacles.get(b).getType().equals("Shovel"))
                {
                    ((Shovel)obstacles.get(b)).moveLeft(10000);
                }
                else if(obstacles.get(b).getType().equals("Tractor"))
                {
                    ((Tractor)obstacles.get(b)).moveLeft(10000);
                }
                else if(obstacles.get(b).getType().equals("Watermelon"))
                {
                    (obstacles.get(b)).setPos(0,5000);
                }
                else
                {
                    b--;
                }
            }
        }
    }
    public Shovel randomShovel()
    {
        return new Shovel(2000+(int)(Math.random()*620),324,72,shovelImage);
    }
    public void selectionSorter(ArrayList<Obstacle> obs)
    {
        int smallest = 0;
        Obstacle temp;
        for(int j = 0; j<obs.size();j++)
        {
            for (int i = j; i < obs.size(); i++)
            {
                if (obs.get(i).getxVal()<obs.get(smallest).getxVal())
                {
                    smallest = i;
                }
            }
            temp = obs.get(j);
            obs.set(j,obs.get(smallest));
            obs.set(smallest,temp);
            smallest=j+1;
        }
    }
    public void insertionSorter(ArrayList<Obstacle> obs)
    {
        Obstacle temp;
        for(int j = 1; j<obs.size();j++)
        {
            for (int i = j; i>0; i--)
            {
                if(obs.get(i).getxVal()<obs.get(i-1).getxVal())
                {
                    temp = obs.get(i-1);
                    obs.set(i-1,obs.get(i));
                    obs.set(i,temp);
                }
            }
        }
    }
    public void goShop(ActionEvent event)
    {
        updateStats();
        this.setVisible(false);
        shop.setVisible(true);
        shop.updateSeedCount();
    }
    public void updateStats()
    {
        seedsThisRun = 0;
        time = 0.0;
        lives = 3+playerInventory.getUpgrades()[3];
        jumpCount = 9+playerInventory.getUpgrades()[4]*3;
        yVelocity=0;
        obstacles = new ArrayList<>();
        activePowerups = new double[]{0.0,0.0,0.0};
    }
    public void playAgain(ActionEvent event)
    {
        updateStats();
        run=true;
        endScreen.setVisible(false);
    }
    public void setRun(boolean b)
    {
        run = b;
    }
    public void hideEndScreen()
    {
        endScreen.setVisible(false);
    }
}