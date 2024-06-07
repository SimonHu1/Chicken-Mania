import Projectiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import java.io.IOException;
import java.util.ArrayList;

public class Gameplay extends JLayeredPane implements ActionListener {
    private Inventory playerInventory = new Inventory();
    private Round roundManager = new Round(20,this);
    private double[][] baseStats = new double[17][3];
    private boolean[] keyTracker = new boolean[3];
    private int characterY = 200; // Initial character position
    private double yVelocity = 0; // Initial vertical velocity
    private double gravity = 16; // Gravity acceleration
    private int jumpCount, cd, lives, seedsThisRun, eggShield, round, parryStrength, currentTurboflap, maxTurboflap;
    private final int PIXELS_PER_METER = 50; // Conversion factor for physics simulation
    private double airTime, jumpCD, time, parryUptime, parryCD, spamJumpCD, turboflapCD;
    private JProgressBar healthBar = new JProgressBar();
    private JProgressBar jumpBar = new JProgressBar();
    private JProgressBar levelProgress = new JProgressBar();
    private ArrayList<BufferedImage> imagesArray = new ArrayList<>();
    private ArrayList<BufferedImage> commonUpgradeIcons = new ArrayList<>();
    private ArrayList<BufferedImage> rareUpgradeIcons = new ArrayList<>();
    private ArrayList<BufferedImage> epicUpgradeIcons = new ArrayList<>();
    private ArrayList<BufferedImage> legendaryUpgradeIcons = new ArrayList<>();
    private Timer timer;
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private ArrayList<Effect> effects = new ArrayList<>();
    private JButton shopButton = new JButton("SHOP");
    private JTextField deathText = new JTextField();
    private JTextField congratsText = new JTextField();
    private JPanel endScreen = new JPanel();
    private boolean run, containsTractor;
    private double[] activePowerups = new double[3]; //1.0 = Proteggtion, 2.0 = pecking machine, 3.0 = Archaic Call
    private Shop shop;
    public Gameplay() {
        updateBaseStats();
        instantiateBars();
        makeMultiplicativeOne();
        inititalizeEndScreen();
        jumpCount=9;
        maxTurboflap=3;
        this.playerInventory = new Inventory();
        this.shop = new Shop(this, this.playerInventory);
        seedsThisRun=0;
        run = false;
        initializeShop();
        shop.setVisible(true);
        lives = 30;
        timer = new Timer(10, this); // 200 FPS (1000 ms / 200)
        timer.start();
        setFocusable(true); // Allow the panel to receive keyboard input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    keyTracker[0] = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_S) {
                    keyTracker[1] = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    keyTracker[2] = true;
                }

                if(keyTracker[2] && keyTracker[0])
                {
                    turboflap(20);
                }
                else if(keyTracker[2] && keyTracker[1])
                {
                    turboflap(-20);
                }
                else if (keyTracker[0] && shop.getRareUpgrades().get(0).getUpgradeLevel() == 0) {
                    jump();
                }
                else if (keyTracker[0] && shop.getRareUpgrades().get(0).getUpgradeLevel() == 1) {
                    changeY(10);
                }
                else if (keyTracker[1] && shop.getRareUpgrades().get(0).getUpgradeLevel() == 1) {
                    changeY(-8);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                // Update flags when keys are released
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    keyTracker[0] = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_S) {
                    keyTracker[1] = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    keyTracker[2] = false;
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    parry();
                }
            }
        });
        try {
            imagesArray.add(ImageIO.read(new File("Images/ThingieRooster.png")));
            imagesArray.add(ImageIO.read(new File("Images/Shovel.png")));
            imagesArray.add(ImageIO.read(new File("Images/Watermelon.png")));
            imagesArray.add(ImageIO.read(new File("Images/Tractor.png")));
            imagesArray.add(ImageIO.read(new File("Images/Seed.png")));
            imagesArray.add(ImageIO.read(new File("Images/Actual Background.png")));
            imagesArray.add(ImageIO.read(new File("Images/Archaic Call Symbol.png")));
            imagesArray.add(ImageIO.read(new File("Images/Proteggtion Symbol.png")));
            imagesArray.add(ImageIO.read(new File("Images/Seed Galore Symbol.png")));
            imagesArray.add(ImageIO.read(new File("Images/Egg.png")));
            imagesArray.add(ImageIO.read(new File("Images/Parry Spark.png")));
            imagesArray.add(ImageIO.read(new File("Images/RevShovel.png")));
            imagesArray.add(ImageIO.read(new File("Images/RevTractor.png")));
            imagesArray.add(ImageIO.read(new File("Images/Turboflap.png")));
            imagesArray.add(ImageIO.read(new File("Images/Explosion.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if(imagesArray.size()!=15) return;
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(new Font("Monospaced",Font.BOLD,40));
        g2d.fillRect(0, 0, getWidth(), getHeight()); // Clear the screen
        g2d.drawImage(imagesArray.get(5),0,-100,1920,1080,null);
        healthBar.setString("Health: " + lives);
        jumpBar.setString("Jumps: " + jumpCount);
        levelProgress.setString("Level Progress: " + (int)(time/roundManager.getWaveTime()*100) + "%");
        healthBar.setValue(lives);
        jumpBar.setValue(jumpCount);
        levelProgress.setValue((int)(time/roundManager.getWaveTime()*100));
        g2d.drawImage(imagesArray.get(4),10,150,75,75,null);
        g2d.drawImage(imagesArray.get(13),110,150,75,75,null);
        g2d.drawString(""+seedsThisRun,45,230);
        g2d.drawString(""+currentTurboflap,160,230);
        if (imagesArray.get(0) != null) {
            g2d.drawImage(imagesArray.get(0), 100, characterY, 70, 70, null);
        }
        if(eggShield>0.0)
        {
            g2d.drawImage(imagesArray.get(9),100,characterY,80,80,null);
        }
        for(Obstacle o: obstacles)
        {
            g2d.drawImage(o.getIcon(),o.getxVal(),o.getyVal(),o.getWidth(),o.getHeight(),null);
        }
        for(Effect e: effects)
        {
            g2d.drawImage(e.getIcon(),e.getxVal(),e.getyVal(),e.getWidth(),e.getHeight(),null);
        }
        g2d.setFont(new Font("Monospaced",Font.BOLD,20));
        for(int i = 0; i<3; i++)
        {
            if(activePowerups[i]>0.0)
            {
                if(i==0)
                {
                    g2d.drawImage(imagesArray.get(7),1650,0,100,100,null);
                    g2d.drawString((int)(activePowerups[i]*100)/100+"",1730,120);
                }
                if(i==1)
                {
                    g2d.drawImage(imagesArray.get(8),1750,0,100,100,null);
                    g2d.drawString((int)(activePowerups[i]*100)/100+"",1830,120);
                }
            }
        }
        g2d.setFont(new Font("Monospaced",Font.BOLD,50));
        g2d.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(imagesArray.size()!=15) return;
        if(!run) return;
        if(lives<=0)
        {
            selectionSorter(obstacles);
            insertionSorter(obstacles);
//            endScreen.setVisible(true);
            shop.setVisible(true);
            playerInventory.setLastRoundSeed(seedsThisRun);
            playerInventory.setSeed(playerInventory.getSeed()+seedsThisRun);
            shop.updateSeedCount();
            shop.updateInventory(playerInventory);
            run = false;
        }
        if(roundManager.getNum()!=21)
        {
            if(roundManager.getWaveTime()<time)
            {
                time=1;
                selectionSorter(obstacles);
                insertionSorter(obstacles);
                endScreen.setVisible(true);
                healthBar.setVisible(false);
                jumpBar.setVisible(false);
                levelProgress.setVisible(false);
                congratsText.setText("Round " + roundManager.getNum() + " Win!");
                deathText.setText("Seeds Received: " + ((int)(seedsThisRun+baseStats[4][0])));
                playerInventory.setLastRoundSeed(seedsThisRun+(int)baseStats[4][0]);
                playerInventory.setSeed(playerInventory.getSeed()+seedsThisRun+(int)baseStats[4][0]);
                shop.updateSeedCount();
                shop.updateInventory(playerInventory);
                roundManager.setNum(roundManager.getNum()+1);
                makeMultiplicativeOne();
                run = false;
            }
        }
        else
        {
            this.setVisible(false);
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
        if(time>1)
        {
            roundManager.createWave();
        }
        checkHit();
        updateObstacles();
    }

    public void update() {
        yVelocity += gravity;
        airTime+=0.1;
        time+=0.015;
        spamJumpCD-=0.008;
        if(lives>(int)baseStats[0][0]) lives = (int)baseStats[0][0];
        if(parryUptime>-0.01)
        {
            parryUptime-=0.04;
        }
        if(parryCD>-0.01)
        {
            parryCD-=0.015;
        }
        if(turboflapCD<1.2&&currentTurboflap!=maxTurboflap)
        {
            turboflapCD+=0.005*(baseStats[9][0]/100);
        }
        if(turboflapCD>1)
        {
            turboflapCD--;
            currentTurboflap++;
            if(currentTurboflap>maxTurboflap) currentTurboflap=maxTurboflap;
        }
        ArrayList<Effect> toRemove = new ArrayList<>();
        for(Effect e: effects)
        {
            e.changeTime(-0.075);
            if(e.getTime()<0)
            {
                toRemove.add(e);
            }
        }
        for(Effect e: toRemove)
        {
            effects.remove(e);
        }
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
        jumpCD+=0.1;
        double deltaY = yVelocity / PIXELS_PER_METER;
        characterY += deltaY;

        if (characterY >= getHeight() - 180) {
            characterY = getHeight() - 180;
            yVelocity = 0;
            airTime = 0.0;
            jumpCD = 0.0;
            jumpCount = (int)baseStats[1][0];
        }
        if(characterY <= 0)
        {
            characterY = 1;
            yVelocity = 0;
        }
    }
    public void jump() {
        if(jumpCount==0) return;
        if ((characterY == getHeight() - 180)||(jumpCount>0&&airTime>0.5&&jumpCD>1.0))
        {
            yVelocity = -12 * PIXELS_PER_METER;
            jumpCount--;
            jumpCD = 0.0;
        }
    }
    public void parry(){
        if(parryCD<0)
        {
            parryCD = 1;
            parryUptime = 1;
        }
    }
    public void changeY(int v)
    {
        if(jumpCount==0) return;
        yVelocity = -v * PIXELS_PER_METER;
        if(spamJumpCD<0)
        {
            jumpCount--;
            spamJumpCD=0.2;
        }
    }
    public void turboflap(int v)
    {
        if(jumpCount==0||currentTurboflap<=0) return;
        yVelocity = -v * PIXELS_PER_METER;
        if(spamJumpCD<0)
        {
            currentTurboflap--;
            spamJumpCD=0.2;
        }
    }
    public void updateObstacles()
    {
        if(obstacles.size()==0) return;
        for(int i = obstacles.size()-1; i>=0;i--)
        {
            if(obstacles.get(i).getType().equals("Projectiles.Shovel"))
            {
                ((Shovel)obstacles.get(i)).moveLeft((int)(8+Math.pow(time,0.5)));
                if(obstacles.get(i).getxVal()<-600)
                {
                    obstacles.remove(i);
                    System.out.println("shovel deleted");
                    System.out.println(obstacles.size());
                }
            }
            else if(obstacles.get(i).getType().equals("Projectiles.Tractor"))
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
            else if(obstacles.get(i).getType().equals("Projectiles.Watermelon"))
            {
                ((Watermelon)obstacles.get(i)).setyVelocity(((Watermelon)obstacles.get(i)).getyVelocity()+gravity);
                (obstacles.get(i)).setPos((obstacles.get(i)).getxVal()-20,(obstacles.get(i)).getyVal()+(int)(((Watermelon)obstacles.get(i)).getyVelocity()/PIXELS_PER_METER));
                if(obstacles.get(i).getyVal()>1200)
                {
                    obstacles.remove(i);
                }
            }
            else if(obstacles.get(i).getType().equals("Projectiles.Seed"))
            {
                ((Seed)obstacles.get(i)).moveLeft((int)(8+Math.pow(time,0.5)));
                if(obstacles.get(i).getxVal()<0)
                {
                    obstacles.remove(i);
                    System.out.println("seed deleted");
                    System.out.println(obstacles.size());
                }
            }
            else if(obstacles.get(i).getType().equals("Projectiles.Powerup"))
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
                    if(o.getType().equals("Projectiles.Shovel"))
                    {
                        if(parryUptime>0&&parryStrength>=o.getDamage())
                        {
                            o.setParried(true);
                            o.setIcon(imagesArray.get(11));
                            cd=15;
                            lives+=(int)baseStats[16][0];
                            effects.add(new Effect(120,characterY,70,200,imagesArray.get(10),0.5));
                            checkExplosion();
                        }
                        if(cd<0)
                        {
                            ((Shovel)o).moveLeft(1000);
                            if(eggShield>0)
                            {
                                eggShield-=o.getDamage();
                            }
                            else {
                                lives-=o.getDamage();
                            }
                            cd=15;
                            System.out.println("Hit detected");
                        }
                    }
                    if(o.getType().equals("Projectiles.Tractor"))
                    {
                        if(parryUptime>0&&parryStrength>=o.getDamage())
                        {
                            o.setParried(true);
                            o.setIcon(imagesArray.get(12));
                            cd=15;
                            lives+=(int)baseStats[16][0];
                            effects.add(new Effect(120,characterY,70,200,imagesArray.get(10),0.5));
                            checkExplosion();
                        }
                        if(cd<0)
                        {
                            ((Tractor)o).moveLeft(1000);
                            if(eggShield>0)
                            {
                                eggShield-=o.getDamage();
                            }
                            else {
                                lives-=o.getDamage();
                            }
                            cd=15;
                            System.out.println("Hit detected");
                        }
                    }
                    if(o.getType().equals("Projectiles.Watermelon"))
                    {
                        if(parryUptime>0&&parryStrength>=o.getDamage())
                        {
                            o.setParried(true);
                            o.setPos(0,5000);
                            cd=15;
                            lives+=(int)baseStats[16][0];
                            effects.add(new Effect(120,characterY,70,200,imagesArray.get(10),0.5));
                            checkExplosion();
                        }
                        if(cd<0)
                        {
                            o.setPos(0,5000);
                            if(eggShield>0)
                            {
                                eggShield-=o.getDamage();
                            }
                            else {
                                lives-=o.getDamage();
                            }
                            cd=15;
                            System.out.println("Hit detected");
                        }
                    }
                    if(o.getType().equals("Projectiles.Seed"))
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
                    if(o.getType().equals("Projectiles.Powerup"))
                    {
                        activatePowerup(((Powerup)o).getPower());
                        ((Powerup)o).moveLeft(1000);
                    }
                }
            }
        }
    }
    public void initializeShop()
    {
        add(shop);
    }
    public void generateSeeds()
    {
        if((int)(time*10000)/100%300==0)
        {
            double random = Math.random();
            if(random>0.75)
            {
                int randomY = (int)(Math.random()*780+100);
                obstacles.add(new Seed(2150,randomY,50,50, imagesArray.get(4)));
                obstacles.add(new Seed(2200,randomY,50,50, imagesArray.get(4)));
                obstacles.add(new Seed(2250,randomY,50,50, imagesArray.get(4)));
            }
            else if(random>0.5)
            {
                obstacles.add(new Seed(2150,400,50,50, imagesArray.get(4)));
                obstacles.add(new Seed(2200,450,50,50, imagesArray.get(4)));
                obstacles.add(new Seed(2250,500,50,50, imagesArray.get(4)));
            }
            else if(random>0.25)
            {
                obstacles.add(new Seed(2150,500,50,50, imagesArray.get(4)));
                obstacles.add(new Seed(2200,450,50,50, imagesArray.get(4)));
                obstacles.add(new Seed(2250,400,50,50, imagesArray.get(4)));
            }
            else
            {
                int randomY = (int)(Math.random()*780+100);
                obstacles.add(new Seed(2100,(int)(Math.random()*780+100),50,50, imagesArray.get(4)));
                obstacles.add(new Seed(2100,(int)(Math.random()*780+100),50,50, imagesArray.get(4)));
                obstacles.add(new Seed(2100,(int)(Math.random()*780+100),50,50, imagesArray.get(4)));
            }
        }
    }
    public void generatePowerup()
    {
        generateArchaicCall();
        generateProteggtion();
        generateSeedGalore();
    }
    public void generateArchaicCall()
    {
        if(Math.random()*5000+1<baseStats[5][0])
        {
            obstacles.add(new Powerup(2150,(int)(Math.random()*780+100),75,75,imagesArray.get(6),2));
        }
    }
    public void generateProteggtion()
    {
        if(Math.random()*5000+1<baseStats[6][0])
        {
            obstacles.add(new Powerup(2150,(int)(Math.random()*780+100),75,75,imagesArray.get(7),0));
        }
    }
    public void generateSeedGalore()
    {
        if(Math.random()*5000+1<baseStats[7][0])
        {
            obstacles.add(new Powerup(2150,(int)(Math.random()*780+100),75,75,imagesArray.get(8),1));
        }
    }
    public void activatePowerup(int n)
    {
        if(n==0)
        {
            eggShield=(int)(3*shop.getCommonUpgrades().get(1).getBuff()[0][shop.getCommonUpgrades().get(1).getUpgradeLevel()]);
            activePowerups[0]=5.0*shop.getCommonUpgrades().get(1).getBuff()[0][shop.getCommonUpgrades().get(1).getUpgradeLevel()];
        }
        else if(n==1)
        {
            activePowerups[1]=5.0*shop.getCommonUpgrades().get(1).getBuff()[0][shop.getCommonUpgrades().get(1).getUpgradeLevel()];
        }
        else {
            int i = obstacles.size();
            for(int b = i-1;b>=0&&b>=obstacles.size()-3;b--)
            {
                if(obstacles.get(b).getType().equals("Projectiles.Shovel"))
                {
                    ((Shovel)obstacles.get(b)).moveLeft(10000);
                }
                else if(obstacles.get(b).getType().equals("Projectiles.Tractor"))
                {
                    ((Tractor)obstacles.get(b)).moveLeft(10000);
                }
                else if(obstacles.get(b).getType().equals("Projectiles.Watermelon"))
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
    public void inititalizeEndScreen()
    {
        add(endScreen);
        endScreen.add(deathText);
        endScreen.add(shopButton);
        endScreen.add(congratsText);
        endScreen.setVisible(false);
        endScreen.setLayout(null);
        endScreen.setBounds(560,200,800,700);
        endScreen.setBackground(new Color(224, 181, 61));
        endScreen.setBorder(new LineBorder(Color.BLACK,12));
        deathText.setBounds(70,150,2000,100);
        deathText.setOpaque(false);
        deathText.setEditable(false);
        deathText.setBorder(null);
        deathText.setFont(new Font("Monospaced", Font.BOLD, 60));
        deathText.setForeground(new Color(46, 31, 0));
        deathText.setText("Seeds Received: " + ((int)(seedsThisRun+baseStats[4][0])));
        congratsText.setBounds(70,50,2000,100);
        congratsText.setOpaque(false);
        congratsText.setEditable(false);
        congratsText.setBorder(null);
        congratsText.setFont(new Font("Monospaced", Font.BOLD, 90));
        congratsText.setForeground(Color.green);
        initializeButtons();
    }
    public void initializeButtons()
    {
        shopButton.setFont(new Font("Monospaced", Font.BOLD, 60));
        shopButton.addActionListener(this::goShop);
        shopButton.setBounds(200,400,400,100);
        shopButton.setBackground(Color.red);
        shopButton.setForeground(new Color(46, 31, 0));
        shopButton.setBorder(new LineBorder(new Color(46, 31, 0),8));
        shopButton.setFocusPainted(false);
    }
    public void instantiateBars()
    {
        add(levelProgress);
        add(healthBar);
        add(jumpBar);
        healthBar.setMinimum(0);
        jumpBar.setMinimum(0);
        levelProgress.setMinimum(0);
        healthBar.setStringPainted(true);
        healthBar.setForeground(new Color(50, 250, 93)); // Custom color
        healthBar.setOpaque(false);
        healthBar.setFont(new Font("Monospaced", Font.BOLD, 32)); // Custom font
        healthBar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        jumpBar.setStringPainted(true);
        jumpBar.setForeground(new Color(94, 212, 255)); // Custom color
        jumpBar.setOpaque(false);
        jumpBar.setFont(new Font("Monospaced", Font.BOLD, 32)); // Custom font
        jumpBar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        levelProgress.setStringPainted(true);
        levelProgress.setForeground(new Color(250, 250, 100)); // Custom color
        levelProgress.setFont(new Font("Monospaced", Font.BOLD, 32)); // Custom font
        levelProgress.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        levelProgress.setOpaque(false);
        healthBar.setBounds(0,0,600,50);
        jumpBar.setBounds(0,50,500,50);
        levelProgress.setBounds(0,100,400,50);
        healthBar.setVisible(false);
        jumpBar.setVisible(false);
        levelProgress.setVisible(false);
    }
    public Shovel randomShovel()
    {
        return new Shovel(2000+(int)(Math.random()*620),324,72,imagesArray.get(1),1);
    }
    public void checkExplosion()
    {
        if(shop.getLegendaryUpgrades().get(0).getUpgradeLevel()==0) return;
        if((int)(Math.random()*100)+1<=shop.getLegendaryUpgrades().get(0).getBuff()[0][shop.getLegendaryUpgrades().get(0).getUpgradeLevel()-1])
        {
            effects.add(new Effect(180,characterY,300,300,imagesArray.get(14),0.5));
            for(Obstacle o: obstacles)
            {
                if (o.getxVal() <= 620 && o.getxVal() + o.getWidth() >= 0){
                    if(o.getyVal() <= characterY + 300 && o.getyVal() + o.getHeight() >= characterY-100) {
                        if(o.getType().equals("Projectiles.Shovel"))
                        {
                            ((Shovel)o).moveLeft(1000);
                            System.out.println("delete");
                        }
                        if(o.getType().equals("Projectiles.Tractor"))
                        {
                            ((Tractor)o).moveLeft(1000);
                            System.out.println("delete");
                        }
                        if(o.getType().equals("Projectiles.Watermelon"))
                        {
                            o.setPos(0,5000);
                            System.out.println("delete");
                        }
                    }
                }
            }
        }
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
    public void insertionSorter(ArrayList<Obstacle> obs) {
        Obstacle temp;
        for (int j = 1; j < obs.size(); j++) {
            for (int i = j; i > 0; i--) {
                if (obs.get(i).getxVal() < obs.get(i - 1).getxVal()) {
                    temp = obs.get(i - 1);
                    obs.set(i - 1, obs.get(i));
                    obs.set(i, temp);
                }
            }
        }
    }
    public void goShop(ActionEvent event)
    {
        endScreen.setVisible(false);
        shop.setVisible(true);
        shop.updateSeedCount();
    }
    public void updateStats()
    {
        seedsThisRun = 0;
        time = 0.0;
        yVelocity=0;
        lives=1;
        obstacles = new ArrayList<>();
        activePowerups = new double[]{0.0,0.0,0.0};
        updateBaseStats();
        shop.applyAllUpgrades();
        applyStats();
        healthBar.setMaximum((int)baseStats[0][0]);
        jumpBar.setMaximum((int)baseStats[1][0]);
        levelProgress.setMaximum(100);
        healthBar.setVisible(true);
        jumpBar.setVisible(true);
        levelProgress.setVisible(true);
    }
    public void applyStats()
    {
        for(int i = 0; i<baseStats.length; i++)
        {
            System.out.println(i);
            for(int u = 1; u<baseStats[0].length; u++)
            {
                if(u==1)
                {
                    baseStats[i][0] += baseStats[i][u];
                    System.out.println("ADDED VALUE:" + baseStats[i][u]);
                }
                if(u==2)
                {
                    baseStats[i][0] *= baseStats[i][u];
                    System.out.println("Multiplied VALUE:" + baseStats[i][u]);
                }
            }
        }
        lives = (int)baseStats[0][0];
        if(lives==0||lives<0) lives = 1;
        jumpCount = (int)baseStats[1][0];
        parryStrength = (int)baseStats[2][0];
        maxTurboflap = (int)baseStats[3][0];
    }
    public void updateBaseStats()
    {
        //0 = health, 1 = jumps, 2 = parry strength, 3 = max turboflaps, 4 = seeds upon round completion
        //5 = archaic call spawn chance, 6 = proteggtion spawn chance, 7 = 2x seed spawn chance, 8 = seeds during round spawn chance
        //9 = turboflap regeneration speed, 10 = time between parries, 11 = health regeneration speed
        //12 = tool damage, 13 = fruit damage, 14 = vehicle damage, 15 = obstacles to disappear after spawning
        //16 = health per successful parry
        //column 0 = base, column 1 = additive, column 2 = multiplicative
        baseStats[0][0] = 3 + (roundManager.getNum()/2);
        baseStats[1][0] = 9 + roundManager.getNum();
        baseStats[2][0] = 2 + (roundManager.getNum()/3);
        baseStats[3][0] = 1 + (roundManager.getNum()/10);
        baseStats[4][0] = 8 + (roundManager.getNum()*3/2);
        baseStats[5][0] = 3 + (roundManager.getNum()/6);
        baseStats[6][0] = 3 + (roundManager.getNum()/6);
        baseStats[7][0] = 3 + (roundManager.getNum()/6);
        baseStats[8][0] = 2 + (roundManager.getNum()/10);
        baseStats[9][0] = 100;
        baseStats[10][0] = 100;
        baseStats[11][0] = 100-(roundManager.getNum()/3);
        baseStats[12][0] = 2+(roundManager.getNum()/3);
        baseStats[13][0] = 1+(roundManager.getNum()/5);
        baseStats[14][0] = 8+(roundManager.getNum()/3);
        baseStats[15][0] = 0;
        baseStats[16][0] = 0;
    }
    public void setBaseStats(int statIndex, int columnNum, double val)
    {
        if(columnNum==1)
        {
            baseStats[statIndex][1] = baseStats[statIndex][1]+val;
        }
        else
        {
            baseStats[statIndex][2] = 1*baseStats[statIndex][2]*val;
        }
    }
    public void makeMultiplicativeOne()
    {
        for(int i = 0; i<baseStats.length; i++)
        {
            baseStats[i][2] = 1;
        }
    }
    public void setRun(boolean b)
    {
        run = b;
    }
    public int getRound()
    {
        return round;
    }
    public ArrayList<Obstacle> getObstacles()
    {
        return obstacles;
    }
    public ArrayList<BufferedImage> getImagesArray()
    {
        return imagesArray;
    }
    public int getCharacterY()
    {
        return characterY;
    }
    public boolean isContainsTractor()
    {
        return containsTractor;
    }
    public void setContainsTractor(boolean b)
    {
        containsTractor=b;
    }
    public double[][] getBaseStats()
    {
        return baseStats;
    }
}