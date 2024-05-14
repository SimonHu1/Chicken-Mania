import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Shop extends JLabel
{
    private Inventory playerInventory;
    private ArrayList<Upgrade> commonUpgrades = new ArrayList<>();
    private ArrayList<Upgrade> rareUpgrades = new ArrayList<>();
    private ArrayList<Upgrade> epicUpgrades = new ArrayList<>();
    private ArrayList<Upgrade> legendaryUpgrades = new ArrayList<>();
    private Gameplay gameplay;
    private JLabel seedCount = new JLabel();
    private JButton startRun = new JButton("START RUN");
    private ImageIcon[] shopItemIcons = new ImageIcon[5];
    public Shop(Inventory playerInventory)
    {
        instantiateUpgradePool();
        seedCount.setText("SEEDS:"+playerInventory.getSeed());
        seedCount.setBounds(100,0,800,100);
        seedCount.setFont(new Font("Monospaced", Font.BOLD, 60));
        this.playerInventory = playerInventory;
        setBounds(190, 50,1530,820);
        setBackground(new Color(46, 31, 0));
        setBorder(new LineBorder(Color.BLACK,10));
        startRun.setBounds(1120,660,400,150);
        startRun.setFont(new Font("Monospaced", Font.BOLD, 60));
        startRun.addActionListener(this:: goRun);
        startRun.setBackground(Color.red);
        startRun.setBorder(new LineBorder(new Color(46, 31, 0),8));
        startRun.setForeground(new Color(46, 31, 0));
        startRun.setFocusPainted(false);
        instantiateShopItemIcons();
        instantiateShop();
        add(startRun);
        add(seedCount);
    }
    public void instantiateUpgradePool()
    {
        ArrayList<Upgrade> commonUpgrades = new ArrayList<>();
        ArrayList<Upgrade> rareUpgrades = new ArrayList<>();
        ArrayList<Upgrade> epicUpgrades = new ArrayList<>();
        ArrayList<Upgrade> legendaryUpgrades = new ArrayList<>();
        instantiateUpgradePool();
    }
    public void instantiateUpgrades()
    {
        //~,_for positive buffs,` for negative buffs
        commonUpgrades.add(new Upgrade("+~ Max Health","Chonk",0,0,0,99,5));
        commonUpgrades.add(new Upgrade("x~ buffs received from powerups","Powerup Blessing",1,0,0,99,3));
        commonUpgrades.add(new Upgrade("+~ jumps","Feathering Heights",2,0,0,99,2));
        commonUpgrades.add(new Upgrade("x~ Archaic Call Spawn Chance","Archaic Call Seeker",3,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ Proteggtion Spawn Chance","Proteggtion Seeker",4,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ 2x Seed Spawn Chance","2x Seed Seeker",5,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ Seeds during Round Spawn Chance","Seed Sniffing",6,0,0,3,7));
        commonUpgrades.add(new Upgrade("+~ Seeds Upon Round Completion","Service Seeds",7,0,0,3,7));
        commonUpgrades.add(new Upgrade("+~ Max Turboflap","",8,0,0,2,8));
        commonUpgrades.add(new Upgrade("+~% Turboflap Regeneration Speed","Freedom Flight",9,0,0,5,7));
        commonUpgrades.add(new Upgrade("-~% Time Between Parries","Quick Claws",10,9,0,10,6));
        commonUpgrades.add(new Upgrade("+~ Parry Strength","Razor Claws",11,0,0,10,5));
        commonUpgrades.add(new Upgrade("+~% Health Regeneration Speed","Rapid Regeneration",12,0,0,10,7));
        rareUpgrades.add(new Upgrade("Complete Flight Control","Is it a bird, is it a plane, it's... both",13,0,1,1,44));
        rareUpgrades.add(new Upgrade("x~ Tool Damage, x` Fruit Damage, x` Vehicle Damage","Tool Mutation",14,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Fruit Damage, x` Vehicle Damage, x` Tool Damage","Fruit Mutation",15,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Vehicle Damage, x` Fruit Damage, x` Tool Damage","Vehicle Mutation",16,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Max Health, -` Seeds Upon Round Completion","Absolute Unit",17,0,1,3,12));
        rareUpgrades.add(new Upgrade("+~% for Obstacles Disappear after Spawning","Mogged to Death",18,0,1,5,10));
        rareUpgrades.add(new Upgrade("+~ Max Health, +_ Seeds Upon Round Completion","Bandage",19,0,1,5,13));
        rareUpgrades.add(new Upgrade("x~ Seeds during Round Spawn Chance, x' Damage Received","Googly Goggles",20,0,1,5,15));
        rareUpgrades.add(new Upgrade("+~ Max Health, +_% Health Regeneration Speed, -' Seeds Upon Round Completion","Objective: Survive",21,0,1,3,15));
        epicUpgrades.add(new Upgrade("+9 jumps, +3 Max Turboflap","Cloud Nine",22,0,2,1,15));
        epicUpgrades.add(new Upgrade("x~ Powerup Spawn Chance, -` Max Turboflap","Outsource",23,0,2,3,15));
        epicUpgrades.add(new Upgrade("+~% Turboflap Regeneration Speed, +_ Parry Strength","Nutrients",24,0,2,3,15));
        epicUpgrades.add(new Upgrade("+~ Health per Successful Parry, -' Parry Strength","Shock Absorption",25,0,2,15,12));
        epicUpgrades.add(new Upgrade("+1 Available Upgrade per Round, -2 Seeds Upon Round Completion","Sight Seeing",26,0,2,1,32));
        epicUpgrades.add(new Upgrade("x~ Fruit Damage, -' Max Health","Fruit Fiend",27,0,2,3,17));
        epicUpgrades.add(new Upgrade("x~ Vehicle Damage, -' Seeds Upon Round Completion","Vehicle Venerator",28,0,2,3,17));
        epicUpgrades.add(new Upgrade("x~ Tool Damage, x' Time Between Parries","Tool Tolerance",29,0,2,3,17));
        epicUpgrades.add(new Upgrade("x2 Parry Strength, -20% Time Between Parries, x0 Archaic Call Spawn Chance, x0 Proteggtion Spawn Chance, x0 2x Seed Spawn Chance","1v20",30,0,2,1,22));
        legendaryUpgrades.add(new Upgrade("Parries have a 10% to create eggsplosions","EGGSPLOSION",31,0,3,3,35));
        legendaryUpgrades.add(new Upgrade("x~ Max Health","Supersize Me",31,0,32,3,30));
        legendaryUpgrades.add(new Upgrade("+66 Max Health, x0 Max Turboflaps, x0 Jumps","Grounded",33,0,3,1,30));
        legendaryUpgrades.add(new Upgrade("+15 Seeds Upon Round Completion, x0.1 Max Health","All or Nothing",34,0,3,1,35));
        legendaryUpgrades.add(new Upgrade("x~ Vehicle Damage, x~ Fruit Damage, x~ Tool Damage","The Mutation",35,0,1,10,10));
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
    }
    public void instantiateShop()
    {
        ShopItemLabel proteggtionPowerup = new ShopItemLabel(this, playerInventory,"Proteggtion", shopItemIcons[0],0,"Increases Proteggtion Uptime and Health");
        ShopItemLabel seedGalorePowerup = new ShopItemLabel(this, playerInventory,"Projectiles.Seed Galore", shopItemIcons[1],1,"Increases Projectiles.Seed Galore Uptime");
        ShopItemLabel archaicCallPowerup = new ShopItemLabel(this, playerInventory,"Archaic Call", shopItemIcons[2],2,"Increases number of obstacles removed by 1");
        ShopItemLabel artOfTheCatUpgrade = new ShopItemLabel(this, playerInventory,"Art Of The Cat", shopItemIcons[3],3,"Increases lives by 1");
        ShopItemLabel featheringHeightsUpgrade = new ShopItemLabel(this, playerInventory,"Feathering Heights", shopItemIcons[4],4,"Increases jumps by 3");
        proteggtionPowerup.setBounds(0,100,760,180);
        seedGalorePowerup.setBounds(0,280,760,180);
        archaicCallPowerup.setBounds(0,460,760,180);
        artOfTheCatUpgrade.setBounds(760,100,760,180);
        featheringHeightsUpgrade.setBounds(760,280,760,180);
        add(proteggtionPowerup);
        add(seedGalorePowerup);
        add(archaicCallPowerup);
        add(artOfTheCatUpgrade);
        add(featheringHeightsUpgrade);
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
    public void setGameplay(Gameplay gameplay)
    {
        this.gameplay = gameplay;
    }
    public void goRun(ActionEvent actionEvent) {
        gameplay.setVisible(true);
        gameplay.setRun(true);
        setVisible(false);
        gameplay.hideEndScreen();
        gameplay.updateStats();
    }
    public void updateSeedCount()
    {
        seedCount.setText("SEEDS:"+playerInventory.getSeed());
    }
}
