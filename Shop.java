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
    private ShopItemLabel upgradeChoice1, upgradeChoice2, upgradeChoice3, upgradeChoice4;
    private Gameplay gameplay;
    private JLabel seedCount = new JLabel();
    private JButton rerollButton = new JButton("REROLL");
    private JButton startRun = new JButton("START RUN");
    private ImageIcon[] shopItemIcons = new ImageIcon[5];
    public Shop(Gameplay gameplay,Inventory playerInventory)
    {
        this.gameplay=gameplay;
        instantiateUpgradePool();
        seedCount.setText("SEEDS:"+playerInventory.getSeed());
        seedCount.setBounds(50,0,800,100);
        seedCount.setFont(new Font("Monospaced", Font.BOLD, 60));
        seedCount.setForeground(new Color(61, 41, 0));
        this.playerInventory = playerInventory;
        setBounds(390, 0,1130,920);
        setOpaque(true);
        setBackground(new Color(161, 144, 111));
        setBorder(new LineBorder(new Color(61, 41, 0),10));
        startRun.setBounds(720,760,400,150);
        startRun.setFont(new Font("Monospaced", Font.BOLD, 60));
        startRun.addActionListener(this:: goRun);
        startRun.setBackground(Color.red);
        startRun.setBorder(new LineBorder(new Color(61, 41, 0),8));
        startRun.setForeground(new Color(61, 41, 0));
        startRun.setFocusPainted(false);
        rerollButton.setBounds(320,760,400,150);
        rerollButton.setFont(new Font("Monospaced", Font.BOLD, 60));
        rerollButton.addActionListener(this:: updateShop);
        rerollButton.setBackground(Color.red);
        rerollButton.setBorder(new LineBorder(new Color(61, 41, 0),8));
        rerollButton.setForeground(new Color(61, 41, 0));
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
        //~,_for positive buffs,` for negative buffs
        commonUpgrades.add(new Upgrade("+~ Max Health","Chonk",0,0,0,99,5));
        commonUpgrades.add(new Upgrade("x~ buffs received from powerups","Powerup Blessing",1,0,0,99,3));
        commonUpgrades.add(new Upgrade("+~ jumps","Feathering Heights",2,0,0,99,2));
        commonUpgrades.add(new Upgrade("x~ Archaic Call Spawn Chance","Archaic Call Seeker",3,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ Proteggtion Spawn Chance","Proteggtion Seeker",4,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ 2x Seed Spawn Chance","2x Seed Seeker",5,0,0,10,6));
        commonUpgrades.add(new Upgrade("x~ Seeds during Round Spawn Chance","Seed Sniffing",6,0,0,3,7));
        commonUpgrades.add(new Upgrade("+~ Seeds Upon Round Completion","Service Seeds",7,0,0,3,7));
        commonUpgrades.add(new Upgrade("+~ Max Turboflap","WINGS",8,0,0,2,8));
        commonUpgrades.add(new Upgrade("+~% Turboflap Regeneration Speed","Freedom Flight",9,0,0,5,7));
        commonUpgrades.add(new Upgrade("-~% Time Between Parries","Quick Claws",10,9,0,10,6));
        commonUpgrades.add(new Upgrade("+~ Parry Strength","Razor Claws",11,0,0,10,5));
        commonUpgrades.add(new Upgrade("+~% Health Regeneration Speed","Rapid Regeneration",12,0,0,10,7));
        rareUpgrades.add(new Upgrade("Complete Flight Control","Is it a bird, is it a plane, it's... both",13,0,1,1,44));
        rareUpgrades.add(new Upgrade("x~ Tool Damage, x` Fruit Damage, x` Vehicle Damage","Tool Mutation",14,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Fruit Damage, x` Vehicle Damage, x` Tool Damage","Fruit Mutation",15,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Vehicle Damage, x` Fruit Damage, x` Tool Damage","Vehicle Mutation",16,0,1,10,10));
        rareUpgrades.add(new Upgrade("x~ Max Health, -` Seeds Upon Round Completion","Absolute Unit",17,0,1,3,12));
        rareUpgrades.add(new Upgrade("+~% for Obstacles to Disappear after Spawning","Mogged to Death",18,0,1,5,10));
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
        legendaryUpgrades.add(new Upgrade("Parries have a ~% to create eggsplosions","EGGSPLOSION",31,0,3,3,35));
        legendaryUpgrades.add(new Upgrade("x~ Max Health","Supersize Me",31,0,32,3,30));
        legendaryUpgrades.add(new Upgrade("+66 Max Health, x0 Max Turboflaps, x0 Jumps","Grounded",33,0,3,1,30));
        legendaryUpgrades.add(new Upgrade("+15 Seeds Upon Round Completion, x0.1 Max Health","All or Nothing",34,0,3,1,35));
        legendaryUpgrades.add(new Upgrade("x~ Vehicle Damage, x~ Fruit Damage, x~ Tool Damage","The Mutation",35,0,3,10,10));
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
        //epicUpgrades.get(0) is a 1 time upgrade
        epicUpgrades.get(1).setBuff(new double[][]{{1.3,1.42,1.5},{1,2,3}});
        epicUpgrades.get(2).setBuff(new double[][]{{10,15,20},{1,2,3}});
        epicUpgrades.get(3).setBuff(new double[][]{{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}});
        //epicUpgrades.get(4) is a 1 time upgrade
        epicUpgrades.get(5).setBuff(new double[][]{{0.75,0.622,0.5},{5,10,15}});
        epicUpgrades.get(6).setBuff(new double[][]{{0.75,0.622,0.5},{4,8,12}});
        epicUpgrades.get(7).setBuff(new double[][]{{0.75,0.622,0.5},{1.5,1.78,2}});
        //epicUpgrades.get(8) is a 1 time upgrade
        legendaryUpgrades.get(0).setBuff(new double[][]{{6,11,15}});
        legendaryUpgrades.get(1).setBuff(new double[][]{{1.9,3.7,5}});
        //legendaryUpgrades.get(2) is a 1 time upgrade
        //legendaryUpgrades.get(3) is a 1 time upgrade
        legendaryUpgrades.get(1).setBuff(new double[][]{{0.91,0.833,0.77,0.71,0.666,0.625,0.588,0.555,0.526,0.5}});
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
        if(upgradesForRound.size()==4)
        {
            upgradeChoice4 = new ShopItemLabel(this,upgradesForRound.get(3),new ImageIcon());
            add(upgradeChoice4);
            upgradeChoice4.setBounds(0,700,1000,300);
        }
        upgradeChoice1.setBounds(0,100,1000,300);
        upgradeChoice2.setBounds(0,300,1000,300);
        upgradeChoice3.setBounds(0,500,1000,300);

    }
    //UPDATE SHOP NOT WORKING
    public void updateShop(ActionEvent e)
    {
        ArrayList<Upgrade> upgradesForRound = randomUpgrades();
        remove(upgradeChoice1);
        remove(upgradeChoice2);
        remove(upgradeChoice3);
        if(upgradesForRound.size()==4) {
            remove(upgradeChoice4);
        }
        upgradeChoice1 = new ShopItemLabel(this,upgradesForRound.get(0),new ImageIcon());
        upgradeChoice2 = new ShopItemLabel(this,upgradesForRound.get(1),new ImageIcon());
        upgradeChoice3 = new ShopItemLabel(this,upgradesForRound.get(2),new ImageIcon());
        System.out.println(upgradesForRound.get(0).getUpgradeName());
        System.out.println(upgradesForRound.get(1).getUpgradeName());
        System.out.println(upgradesForRound.get(2).getUpgradeName());
        if(upgradesForRound.size()==4)
        {
            upgradeChoice4 = new ShopItemLabel(this,upgradesForRound.get(3),new ImageIcon());
            add(upgradeChoice4);
            upgradeChoice4.setBounds(0,700,1000,300);
        }
        rerollButton.setVisible(false);

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
            if(random>95)
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
            else if(random>70)
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
        gameplay.updateStats();
    }
    public void updateSeedCount()
    {
        seedCount.setText("SEEDS:"+playerInventory.getSeed());
    }
    public void updateInventory(Inventory inventory){playerInventory=inventory;}
}
