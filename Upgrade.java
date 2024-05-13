import javax.swing.*;

public class Upgrade
{
    private String description;
    private String upgradeName;
    private int upgradeID;
    private int upgradeLevel;
    private int maxUpgrades;
    private int upgradeRarity;
    public Upgrade(String description,String upgradeName,int upgradeID,int upgradeLevel, int upgradeRarity, int maxUpgrades)
    {
        this.description=description;
        this.upgradeName=upgradeName;
        this.upgradeID=upgradeID;
        this.upgradeLevel=upgradeLevel;
        this.upgradeRarity=upgradeRarity;
        this.maxUpgrades=maxUpgrades;
    }
    public void setDescription()
    {

    }
}
