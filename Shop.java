import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Shop extends JLabel
{
    private Inventory playerInventory;
    private ArrayList<Upgrade> commonUpgrades = new ArrayList<>();
    private ArrayList<Upgrade> rareUpgrades = new ArrayList<>();
    private ArrayList<Upgrade> epicUpgrades = new ArrayList<>();
    private ArrayList<Upgrade> legendaryUpgrades = new ArrayList<>();
    private ArrayList<BufferedImage> upgradeIcons = new ArrayList<>();
    private ShopItemLabel upgradeChoice1, upgradeChoice2, upgradeChoice3, upgradeChoice4;
    private Gameplay gameplay;
    private JLabel seedCount = new JLabel();
    private JButton rerollButton = new JButton("REROLL");
    private JButton startRun = new JButton("START RUN");
    private ImageIcon[] shopItemIcons = new ImageIcon[5];
    private int rerollPrice;
    public Shop(Gameplay gameplay,Inventory playerInventory)
    {
        this.gameplay=gameplay;
        rerollPrice=6;
        instantiateUpgradeIcons();
        instantiateUpgradePool();
        seedCount.setText("SEEDS:"+playerInventory.getSeed());
        seedCount.setBounds(50,-6,800,100);
        seedCount.setFont(new Font("Monospaced", Font.BOLD, 60));
        seedCount.setForeground(new Color(61, 41, 0));
        this.playerInventory = playerInventory;
        setBounds(390, 0,1130,970);
        setOpaque(true);
        setBackground(new Color(161, 144, 111));
        setBorder(new LineBorder(new Color(61, 41, 0),10));
        startRun.setBounds(720,810,400,150);
        startRun.setFont(new Font("Monospaced", Font.BOLD, 60));
        startRun.addActionListener(this:: goRun);
        startRun.setBackground(Color.red);
        startRun.setBorder(new LineBorder(new Color(61, 41, 0),8));
        startRun.setForeground(new Color(61, 41, 0));
        startRun.setFocusPainted(false);
        rerollButton.setBounds(10,810,400,150);
        rerollButton.setFont(new Font("Monospaced", Font.BOLD, 32));
        rerollButton.addActionListener(this:: updateShop);
        rerollButton.setBackground(Color.red);
        rerollButton.setBorder(new LineBorder(new Color(61, 41, 0),8));
        rerollButton.setForeground(new Color(61, 41, 0));
        rerollButton.setText("REROLL ("+ rerollPrice + " Seeds)");
        rerollButton.setFocusPainted(false);
        rerollButton.setVisible(true);
        instantiateShopItemIcons();
        instantiateShop();
        add(startRun);
        add(rerollButton);
        add(seedCount);
    }
    public void instantiateUpgradePool()
    {
        ArrayList<Upgrade> commonUpgrades = new ArrayList<>();
        ArrayList<Upgrade> rareUpgrades = new ArrayList<>();
        ArrayList<Upgrade> epicUpgrades = new ArrayList<>();
        ArrayList<Upgrade> legendaryUpgrades = new ArrayList<>();
        instantiateUpgrades();
    }
    public void instantiateUpgrades()
    {
        //~ then ' then _
        commonUpgrades.add(new Upgrade("+~ Max Health","Chonk",0,0,0,99,5));
        commonUpgrades.add(new Upgrade("x~ Buffs Received From Powerups","Powerup Blessing",1,0,0,99,3));
        commonUpgrades.add(new Upgrade("+~ Jumps","Feathering Heights",2,0,0,99,2));
        commonUpgrades.add(new Upgrade("x~ Archaic Call Spawn Chance","Archaic Call Seeker",3,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ Proteggtion Spawn Chance","Proteggtion Seeker",4,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ 2x Seed Spawn Chance","2x Seed Seeker",5,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ Seeds during Round Spawn Chance","4 Leaf Clover",6,0,0,3,7));
        commonUpgrades.add(new Upgrade("+~ Seeds Upon Round Completion","Service Seeds",7,0,0,3,7));
        commonUpgrades.add(new Upgrade("+~ Max Turboflap","WINGS",8,0,0,2,8));
        commonUpgrades.add(new Upgrade("+~% Turboflap Regeneration Speed","Freedom Flight",9,0,0,5,7));
        commonUpgrades.add(new Upgrade("-~% Time Between Parries","Quick Claws",10,0,0,10,6));
        commonUpgrades.add(new Upgrade("+~ Parry Strength","Razor Claws",11,0,0,10,5));
        commonUpgrades.add(new Upgrade("+~% Health Regeneration Speed","Rapid Regeneration",12,0,0,10,7));
        rareUpgrades.add(new Upgrade("Complete Flight Control","Is It A Bird, Is It A Plane, It's... Both",13,0,1,1,44));
        rareUpgrades.add(new Upgrade("x~ Tool Damage, x' Fruit Damage, x' Vehicle Damage","Tool Mutation",14,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Fruit Damage, x' Vehicle Damage, x' Tool Damage","Fruit Mutation",15,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Vehicle Damage, x' Fruit Damage, x' Tool Damage","Vehicle Mutation",16,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Max Health, -' Seeds Upon Round Completion","Absolute Unit",17,0,1,3,12));
        rareUpgrades.add(new Upgrade("+~% For Obstacles To Disappear After Spawning","Scram(bled)",18,0,1,5,10));
        rareUpgrades.add(new Upgrade("+~ Max Health, +' Seeds Upon Round Completion","Bandage",19,0,1,5,13));
        rareUpgrades.add(new Upgrade("x~ Seeds during Round Spawn Chance, x' Damage Received","Googly Goggles",20,0,1,5,15));
        rareUpgrades.add(new Upgrade("+~ Max Health, +'% Health Regeneration Speed, -_ Seeds Upon Round Completion","Objective: Survive",21,0,1,3,15));
        epicUpgrades.add(new Upgrade("+9 Jumps, +3 Max Turboflap","Cloud Nine",22,0,2,1,15));
        epicUpgrades.add(new Upgrade("x~ Powerup Spawn Chance, -' Max Turboflap","Outsource",23,0,2,3,15));
        epicUpgrades.add(new Upgrade("+~% Turboflap Regeneration Speed, +' Parry Strength","Nutrients",24,0,2,3,15));
        epicUpgrades.add(new Upgrade("+~ Health Per Successful Parry, -' Parry Strength","Shock Absorption",25,0,2,15,12));
        epicUpgrades.add(new Upgrade("+1 Available Upgrade per Round, -2 Seeds Upon Round Completion","Sight Seeing",26,0,2,1,32));
        epicUpgrades.add(new Upgrade("x~ Fruit Damage, -' Max Health","Fruit Fiend",27,0,2,3,17));
        epicUpgrades.add(new Upgrade("x~ Vehicle Damage, -' Seeds Upon Round Completion","Vehicle Venerator",28,0,2,3,17));
        epicUpgrades.add(new Upgrade("x~ Tool Damage, x' Time Between Parries","Tool Tolerance",29,0,2,3,17));
        epicUpgrades.add(new Upgrade("x2 Parry Strength, -20% Time Between Parries, x0 Powerup Spawn Chance","1v20",30,0,2,1,22));
        legendaryUpgrades.add(new Upgrade("Parries Have A ~% To Create Eggsplosions","EGGSPLOSION",31,0,3,3,35));
        legendaryUpgrades.add(new Upgrade("x~ Max Health","Supersize Me",31,0,32,3,30));
        legendaryUpgrades.add(new Upgrade("+66 Max Health, x0 Max Turboflaps, x0 Jumps","Grounded",33,0,3,1,30));
        legendaryUpgrades.add(new Upgrade("+15 Seeds Upon Round Completion, x0.1 Max Health","All or Nothing",34,0,3,1,35));
        legendaryUpgrades.add(new Upgrade("x~ Vehicle Damage, x~ Fruit Damage, x~ Tool Damage","Genetic Diff",35,0,3,10,10));
        setBuffs();
    }
    public void setBuffs()
    {
        double[] longUpgrade = new double[99];
        double[] multiplicativeLongUpgrade = new double[99];
        for(int i=0;i<99;i++)
        {
            longUpgrade[i]=i+1;
            multiplicativeLongUpgrade[i]=1+((i+1)/100);
        }
        commonUpgrades.get(0).setBuff(new double[][]{longUpgrade});
        commonUpgrades.get(1).setBuff(new double[][]{multiplicativeLongUpgrade});
        commonUpgrades.get(2).setBuff(new double[][]{longUpgrade});
        commonUpgrades.get(3).setBuff(new double[][]{{1.05,1.1,1.14,1.18,1.21,1.24,1.26,1.28,1.29,1.30}});
        commonUpgrades.get(4).setBuff(new double[][]{{1.05,1.1,1.14,1.18,1.21,1.24,1.26,1.28,1.29,1.30}});
        commonUpgrades.get(5).setBuff(new double[][]{{1.05,1.1,1.14,1.18,1.21,1.24,1.26,1.28,1.29,1.30}});
        commonUpgrades.get(6).setBuff(new double[][]{{1.3,1.43,1.5}});
        commonUpgrades.get(7).setBuff(new double[][]{{2,4,6}});
        commonUpgrades.get(8).setBuff(new double[][]{{1,2}});
        commonUpgrades.get(9).setBuff(new double[][]{{5,9,13,17,20}});
        commonUpgrades.get(10).setBuff(new double[][]{{5,10,15,18,23,27,30,33,36,40}});
        commonUpgrades.get(11).setBuff(new double[][]{{1,2,3,4,5,6,7,8,9,10}});
        commonUpgrades.get(12).setBuff(new double[][]{{8,16,24,31,38,45,51,57,63,69}});
        //rareUpgrades.get(0) is a 1 time upgrade
        rareUpgrades.get(1).setBuff(new double[][]{{0.75,0.6,0.43,0.375,0.333,0.3,0.27,0.25,0.23,0.21},{1.333,1.666,2.32,2.666,3,3.333,3.7,4,4.35,4.76}});
        rareUpgrades.get(2).setBuff(new double[][]{{0.75,0.6,0.43,0.375,0.333,0.3,0.27,0.25,0.23,0.21},{1.333,1.666,2.32,2.666,3,3.333,3.7,4,4.35,4.76}});
        rareUpgrades.get(3).setBuff(new double[][]{{0.75,0.6,0.43,0.375,0.333,0.3,0.27,0.25,0.23,0.21},{1.333,1.666,2.32,2.666,3,3.333,3.7,4,4.35,4.76}});
        rareUpgrades.get(4).setBuff(new double[][]{{1.25,1.333,1.5},{2,4,6}});
        rareUpgrades.get(5).setBuff(new double[][]{{5,9,13,17,20}});
        rareUpgrades.get(6).setBuff(new double[][]{{2,4,6,8,10},{1,2,3,4,5}});
        rareUpgrades.get(7).setBuff(new double[][]{{1.1,1.18,1.24,1.28,1.3},{1.1,1.18,1.24,1.28,1.3}});
        rareUpgrades.get(8).setBuff(new double[][]{{4,7,9},{12,22,30},{3,5,6}});
        epicUpgrades.get(0).setBuff(new double[][]{{9},{3}});
        epicUpgrades.get(1).setBuff(new double[][]{{1.3,1.42,1.5},{1,2,3}});
        epicUpgrades.get(2).setBuff(new double[][]{{10,15,20},{1,2,3}});
        epicUpgrades.get(3).setBuff(new double[][]{{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}});
        //epicUpgrades.get(4) is a 1 time upgrade
        epicUpgrades.get(5).setBuff(new double[][]{{0.75,0.622,0.5},{5,10,15}});
        epicUpgrades.get(6).setBuff(new double[][]{{0.75,0.622,0.5},{4,8,12}});
        epicUpgrades.get(7).setBuff(new double[][]{{0.75,0.622,0.5},{1.5,1.78,2}});
        epicUpgrades.get(8).setBuff(new double[][]{{2},{20},{0}});
        legendaryUpgrades.get(0).setBuff(new double[][]{{6,11,15}});
        legendaryUpgrades.get(1).setBuff(new double[][]{{1.9,3.7,5}});
        legendaryUpgrades.get(2).setBuff(new double[][]{{66},{0},{0}});
        legendaryUpgrades.get(3).setBuff(new double[][]{{15},{0.1}});
        legendaryUpgrades.get(4).setBuff(new double[][]{{0.91,0.833,0.77,0.71,0.666,0.625,0.588,0.555,0.526,0.5}});
    }
    public void instantiateShop()
    {
        ArrayList<Upgrade> upgradesForRound = randomUpgrades();
        upgradeChoice1 = new ShopItemLabel(this,upgradesForRound.get(0),new ImageIcon());
        upgradeChoice2 = new ShopItemLabel(this,upgradesForRound.get(1),new ImageIcon());
        upgradeChoice3 = new ShopItemLabel(this,upgradesForRound.get(2),new ImageIcon());
        add(upgradeChoice1);
        add(upgradeChoice2);
        add(upgradeChoice3);
        System.out.println(upgradesForRound.get(0).getUpgradeName());
        System.out.println(upgradesForRound.get(1).getUpgradeName());
        System.out.println(upgradesForRound.get(2).getUpgradeName());
//        if(upgradesForRound.size()==4)
//        {
//            upgradeChoice4 = new ShopItemLabel(this,upgradesForRound.get(3),new ImageIcon());
//            add(upgradeChoice4);
//            upgradeChoice4.setBounds(20,700,1200,300);
//        }
        upgradeChoice4 = new ShopItemLabel(this,upgradesForRound.get(2),new ImageIcon());
        add(upgradeChoice4);
        upgradeChoice4.setVisible(false);
        upgradeChoice1.setBounds(20,84,1200,300);
        upgradeChoice2.setBounds(20,266,1200,300);
        upgradeChoice3.setBounds(20,448,1200,300);
        upgradeChoice4.setBounds(20,630,1200,300);

    }
    public void updateShop(ActionEvent e)
    {
        if(playerInventory.getSeed()>rerollPrice)
        {
            playerInventory.setSeed(playerInventory.getSeed()-rerollPrice);
            rerollPrice += 2+(gameplay.getRound()/5);
            updateSeedCount();
            rerollButton.setText("REROLL ("+ rerollPrice + " Seeds)");
        }
        else
        {
            return;
        }
        ArrayList<Upgrade> upgradesForRound = randomUpgrades();
        upgradeChoice1.updateLabel(upgradesForRound.get(0),new ImageIcon());
        upgradeChoice1.setVisible(true);
        upgradeChoice2.updateLabel(upgradesForRound.get(1),new ImageIcon());
        upgradeChoice2.setVisible(true);
        upgradeChoice3.updateLabel(upgradesForRound.get(2),new ImageIcon());
        upgradeChoice3.setVisible(true);
        System.out.println(upgradesForRound.get(0).getUpgradeName());
        System.out.println(upgradesForRound.get(1).getUpgradeName());
        System.out.println(upgradesForRound.get(2).getUpgradeName());
        if(upgradesForRound.size()==4)
        {
            upgradeChoice4.updateLabel(upgradesForRound.get(3),new ImageIcon());
            upgradeChoice4.setVisible(true);
//            upgradeChoice4.setBounds(20,700,1200,300);
        }
//        rerollButton.setVisible(false);

    }
    public void applyCommonUpgrades()
    {
        for(int i = 0; i<commonUpgrades.size(); i++)
        {
            if(commonUpgrades.get(i).getUpgradeLevel()>0)
            {
                switch(i)
                {
                    case 0:
                    {
                        gameplay.setBaseStats(0,1,commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    //case 1 is directly implemented
                    case 2:
                    {
                        gameplay.setBaseStats(1,1,commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 3:
                    {
                        gameplay.setBaseStats(5,2,commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 4:
                    {
                        gameplay.setBaseStats(6,2,commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 5:
                    {
                        gameplay.setBaseStats(7,2,commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 6:
                    {
                        gameplay.setBaseStats(8,2,commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 7:
                    {
                        gameplay.setBaseStats(4,1,commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 8:
                    {
                        gameplay.setBaseStats(3,1,commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 9:
                    {
                        gameplay.setBaseStats(9,1,-1*commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 10:
                    {
                        gameplay.setBaseStats(10,1,-1*commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 11:
                    {
                        gameplay.setBaseStats(2,1, commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 12:
                    {
                        gameplay.setBaseStats(11,1,-1*commonUpgrades.get(i).getBuff()[0][commonUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                }
            }
        }
    }
    public void applyRareUpgrades()
    {
        for(int i = 0; i<rareUpgrades.size(); i++)
        {
            if(rareUpgrades.get(i).getUpgradeLevel()>0)
            {
                System.out.println("THIS UPGRADE ID HAS A LEVEL MORE THAN 0:" + i);
                switch(i)
                {
                    case 1:
                    {
                        gameplay.setBaseStats(12,2,rareUpgrades.get(i).getBuff()[0][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(13,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(14,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 2:
                    {
                        gameplay.setBaseStats(13,2,rareUpgrades.get(i).getBuff()[0][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(14,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(12,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 3:
                    {
                        gameplay.setBaseStats(14,2,rareUpgrades.get(i).getBuff()[0][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(13,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(12,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 4:
                    {
                        gameplay.setBaseStats(0,2,rareUpgrades.get(i).getBuff()[0][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(4,1,-1*rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 5:
                    {
                        gameplay.setBaseStats(15,1,rareUpgrades.get(i).getBuff()[0][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 6:
                    {
                        gameplay.setBaseStats(0,1,rareUpgrades.get(i).getBuff()[0][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(4,1,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 7:
                    {
                        gameplay.setBaseStats(8,2,rareUpgrades.get(i).getBuff()[0][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(12,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(13,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(14,2,rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 8:
                    {
                        gameplay.setBaseStats(0,1,rareUpgrades.get(i).getBuff()[0][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(11,1,-1*rareUpgrades.get(i).getBuff()[1][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(4,2,-1*rareUpgrades.get(i).getBuff()[2][rareUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                }
            }
        }
    }
    public void applyEpicUpgrades()
    {
        for(int i = 0; i<epicUpgrades.size(); i++)
        {
            if(epicUpgrades.get(i).getUpgradeLevel()>0)
            {
                switch(i)
                {
                    case 0:
                    {
                        gameplay.setBaseStats(1, 1, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(3, 1, epicUpgrades.get(i).getBuff()[1][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 1:
                    {
                        gameplay.setBaseStats(5, 2, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(6, 2, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(7, 2, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(3, 1, -1*epicUpgrades.get(i).getBuff()[1][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 2:
                    {
                        gameplay.setBaseStats(9, 1, -1*epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(2, 1, epicUpgrades.get(i).getBuff()[1][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 3:
                    {
                        gameplay.setBaseStats(16, 1, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(2, 1, -1*epicUpgrades.get(i).getBuff()[1][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    //case 4 is directly implemented
                    case 5:
                    {
                        gameplay.setBaseStats(13, 2, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(0, 1, -1*epicUpgrades.get(i).getBuff()[1][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 6:
                    {
                        gameplay.setBaseStats(14, 2, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(4, 1, -1*epicUpgrades.get(i).getBuff()[1][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 7:
                    {
                        gameplay.setBaseStats(12, 2, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(10, 2, epicUpgrades.get(i).getBuff()[1][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 8:
                    {
                        gameplay.setBaseStats(2, 2, epicUpgrades.get(i).getBuff()[0][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(10, 1, -1*epicUpgrades.get(i).getBuff()[1][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(12, 2, epicUpgrades.get(i).getBuff()[2][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(13, 2, epicUpgrades.get(i).getBuff()[2][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(14, 2, epicUpgrades.get(i).getBuff()[2][epicUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                }
            }
        }
    }
    public void applyLegendaryUpgrades()
    {
        for(int i = 0; i<legendaryUpgrades.size(); i++)
        {
            if(legendaryUpgrades.get(i).getUpgradeLevel()>0)
            {
                switch(i)
                {
                    //case 0 is directly implemented
                    case 1:
                    {
                        gameplay.setBaseStats(0, 2, legendaryUpgrades.get(i).getBuff()[0][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 2:
                    {
                        gameplay.setBaseStats(0, 1, legendaryUpgrades.get(i).getBuff()[0][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(3, 2, legendaryUpgrades.get(i).getBuff()[1][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(1, 2, legendaryUpgrades.get(i).getBuff()[2][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 3:
                    {
                        gameplay.setBaseStats(4, 1, legendaryUpgrades.get(i).getBuff()[0][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(0, 2, legendaryUpgrades.get(i).getBuff()[1][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                    case 4:
                    {
                        gameplay.setBaseStats(12, 2, legendaryUpgrades.get(i).getBuff()[0][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(13, 2, legendaryUpgrades.get(i).getBuff()[0][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        gameplay.setBaseStats(14, 2, legendaryUpgrades.get(i).getBuff()[0][legendaryUpgrades.get(i).getUpgradeLevel()-1]);
                        break;
                    }
                }
            }
        }
    }
    public ArrayList<Upgrade> randomUpgrades()
    {
        ArrayList<Upgrade> upgradeForRound = new ArrayList<>();
        int size = 3;
        if(epicUpgrades.get(4).getUpgradeLevel()==1) size++;
        while(upgradeForRound.size()!=size)
        {
            int random = (int)(Math.random()*100)+1;
            Upgrade placeholder;
            if(random>96)
            {
                placeholder = legendaryUpgrades.get((int)(Math.random()*legendaryUpgrades.size()));
                if(placeholder.getUpgradeLevel()<placeholder.getMaxUpgrades())
                {
                    if(!upgradeForRound.contains(placeholder))
                    {
                        upgradeForRound.add(placeholder);
                    }
                }
            }
            else if(random>85)
            {
                placeholder = epicUpgrades.get((int)(Math.random()*epicUpgrades.size()));
                if(placeholder.getUpgradeLevel()<placeholder.getMaxUpgrades())
                {
                    if(!upgradeForRound.contains(placeholder))
                    {
                        upgradeForRound.add(placeholder);
                    }
                }
            }
            else if(random>60)
            {
                placeholder = rareUpgrades.get((int)(Math.random()*rareUpgrades.size()));
                if(placeholder.getUpgradeLevel()<placeholder.getMaxUpgrades())
                {
                    if(!upgradeForRound.contains(placeholder))
                    {
                        upgradeForRound.add(placeholder);
                    }
                }
            }
            else
            {
                placeholder = commonUpgrades.get((int)(Math.random()*commonUpgrades.size()));
                if(placeholder.getUpgradeLevel()<placeholder.getMaxUpgrades())
                {
                    if(!upgradeForRound.contains(placeholder))
                    {
                        upgradeForRound.add(placeholder);
                    }
                }
            }
        }
        return upgradeForRound;
    }
    public void applyAllUpgrades()
    {
        applyCommonUpgrades();
        applyRareUpgrades();
        applyEpicUpgrades();
        applyLegendaryUpgrades();
    }
    public void instantiateShopItemIcons()
    {
        ImageIcon ProteggtionIcon = new ImageIcon("Images/Proteggtion Powerup.png");
        ImageIcon SeedGaloreIcon = new ImageIcon("Images/Seed Galore Powerup.png");
        ImageIcon ArchaicCallIcon = new ImageIcon("Images/Archaic Call Powerup.png");
        ImageIcon ArtOfTheCatIcon = new ImageIcon("Images/Art Of The Cat Upgrade.png");
        ImageIcon FeatheringHeightsIcon = new ImageIcon("Images/Feathering Heights Upgrade.png");
        shopItemIcons[0] = ProteggtionIcon;
        shopItemIcons[1] = SeedGaloreIcon;
        shopItemIcons[2] = ArchaicCallIcon;
        shopItemIcons[3] = ArtOfTheCatIcon;
        shopItemIcons[4] = FeatheringHeightsIcon;
    }
    public void instantiateUpgradeIcons()
    {
        try {
            upgradeIcons.add(ImageIO.read(new File("Images/ThingieRooster.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setGameplay(Gameplay gameplay)
    {
        this.gameplay = gameplay;
    }
    public void goRun(ActionEvent actionEvent) {
        gameplay.setVisible(true);
        gameplay.setRun(true);
        setVisible(false);
        gameplay.updateStats();
        updateShop(actionEvent);
        rerollPrice = 6+(gameplay.getRound()/2);
        for(int i=0;i<commonUpgrades.size();i++)
        {
            commonUpgrades.get(i).printUpgradeInfo();
        }
        for(int i=0;i<rareUpgrades.size();i++)
        {
            rareUpgrades.get(i).printUpgradeInfo();
        }
        for(int i=0;i<epicUpgrades.size();i++)
        {
            epicUpgrades.get(i).printUpgradeInfo();
        }
        for(int i=0;i<legendaryUpgrades.size();i++)
        {
            legendaryUpgrades.get(i).printUpgradeInfo();
        }
    }
    public void updateSeedCount()
    {
        seedCount.setText("SEEDS:"+playerInventory.getSeed());
    }
    public void updateInventory(Inventory inventory){playerInventory=inventory;}
    public void incrementUpgrade(int upgradeID, int upgradeRarity)
    {
        ArrayList<Upgrade> list = new ArrayList();
        if(upgradeRarity==0){list=commonUpgrades;}
        else if(upgradeRarity==1){list=rareUpgrades;}
        else if(upgradeRarity==2){list=epicUpgrades;}
        else{list=legendaryUpgrades;}
        for(int i=0; i<list.size(); i++)
        {
            if(list.get(i).getUpgradeID()==upgradeID)
            {
                list.get(i).incrementUpgrade();
            }
        }
    }
    public Inventory getPlayerInventory()
    {
        return playerInventory;
    }
    public ArrayList<Upgrade> getCommonUpgrades()
    {
        return commonUpgrades;
    }
    public ArrayList<Upgrade>  getRareUpgrades()
    {
        return rareUpgrades;
    }
    public ArrayList<Upgrade>  getEpicUpgrades()
    {
        return epicUpgrades;
    }
    public ArrayList<Upgrade>  getLegendaryUpgrades()
    {
        return legendaryUpgrades;
    }
}
