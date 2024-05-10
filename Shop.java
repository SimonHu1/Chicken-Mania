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
        upgradePool[0].add(new Upgrade("Health","",0,0,0));
        upgradePool[0].add(new Upgrade("Powerup Strength","",1,0,0));
        upgradePool[0].add(new Upgrade("Jump","",2,0,0));
        upgradePool[0].add(new Upgrade("Archaic Call Spawn Chance","",3,0,0));
        upgradePool[0].add(new Upgrade("Proteggtion Spawn Chance","",4,0,0));
        upgradePool[0].add(new Upgrade("Seeds During Round Spawn Chance","",5,0,0));
        upgradePool[0].add(new Upgrade("Seeds When Round is Completed","",6,0,0));
        upgradePool[0].add(new Upgrade("Max Turboflap","",7,0,0));
        upgradePool[0].add(new Upgrade("Turboflap Regeneration Speed","",8,0,0));
        upgradePool[0].add(new Upgrade("Time Between Parries","",9,0,0));
        upgradePool[0].add(new Upgrade("Parry Strength","",10,0,0));
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
