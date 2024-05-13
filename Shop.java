import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Shop extends JLabel
{
    private Inventory playerInventory;
    private ArrayList[] upgradePool;
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
        upgradePool = new ArrayList[]{commonUpgrades,rareUpgrades,epicUpgrades,legendaryUpgrades};
    }
    public void instantiateUpgrades()
    {
        //~ for positive buffs,` for negative buffs
        upgradePool[0].add(new Upgrade("+~ Max Health","Chonk",0,0,0,99));
        upgradePool[0].add(new Upgrade("+~% buffs received from powerups","Powerup Blessing",1,0,0,99));
        upgradePool[0].add(new Upgrade("+~ jumps","Feathering Heights",2,0,0,99));
        upgradePool[0].add(new Upgrade("x~ Archaic Call Spawn Chance","Archaic Call Seeker",3,0,0,99));
        upgradePool[0].add(new Upgrade("x~ Proteggtion Spawn Chance","Proteggtion Seeker",4,0,0,99));
        upgradePool[0].add(new Upgrade("x~ Seeds during Round Spawn Chance","Seed Seeker",5,0,0,3));
        upgradePool[0].add(new Upgrade("+~ Seeds Upon Round Completion","Service Seeds",6,0,0,3));
        upgradePool[0].add(new Upgrade("+~ Max Turboflap","",7,0,0,2));
        upgradePool[0].add(new Upgrade("+~ Turboflap Regeneration Speed","Freedom Flight",8,0,0,5));
        upgradePool[0].add(new Upgrade("-~% Time Between Parries","Quick Claws",9,9,0,10));
        upgradePool[0].add(new Upgrade("Increases the strength of your parry's by ~","Razor Claws",10,0,0,10));
        upgradePool[1].add(new Upgrade("Complete Flight Control","Is it a bird, is it a plane, it's... both",11,0,1,1));
        upgradePool[1].add(new Upgrade("x~ Tool Damage, x` Fruit Damage, x` Vehicle Damage","Tool Mutation",12,0,1,3));
        upgradePool[1].add(new Upgrade("x~ Fruit Damage, x` Vehicle Damage, x` Tool Damage","Fruit Mutation",13,0,1,3));
        upgradePool[1].add(new Upgrade("x~ Vehicle Damage, x` Fruit Damage, x` Tool Damage","Vehicle Mutation",14,0,1,3));
        upgradePool[1].add(new Upgrade("x~ Max Health, -` Seeds Upon Round Completion","Absolute Unit",15,0,1,3));
        upgradePool[3].add(new Upgrade("Parries have a 10% to create eggsplosions","EGGSPLOSION",31,0,3,1));
        upgradePool[3].add(new Upgrade("x~ Max Health","Tank",31,0,3,3));
        upgradePool[3].add(new Upgrade("+66 Max Health, x0 Max Turboflaps, x0 Jumps","Grounded",31,0,3,3));
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
