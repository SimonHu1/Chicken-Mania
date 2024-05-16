import javax.swing.*;

public class Upgrade
{
    private String description;
    private String upgradeName;
    private double[][] buff;
    private int upgradePrice;
    private int upgradeID;
    private int upgradeLevel;
    private int maxUpgrades;
    private int upgradeRarity;
    public Upgrade(String description,String upgradeName,int upgradeID,int upgradeLevel, int upgradeRarity, int maxUpgrades, int upgradePrice)
    {
        this.description=description;
        this.upgradeName=upgradeName;
        this.upgradeID=upgradeID;
        this.upgradeLevel=upgradeLevel;
        this.upgradeRarity=upgradeRarity;
        this.maxUpgrades=maxUpgrades;
        this.upgradePrice=upgradePrice;
    }
    public void setBuff(double[][] buff)
    {
        this.buff=buff;
    }
}
