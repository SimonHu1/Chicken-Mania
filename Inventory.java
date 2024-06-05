public class Inventory
{
    private int seed;
    private int[] upgrades;
    private int lastRoundSeed;
    public Inventory()
    {
        seed = 10000;
        upgrades = new int[]{0,0,0,0,0};
        //first number indicates type
        //second number indicates upgrade level or true/false
    }
    public void setLastRoundSeed(int s)
    {
        lastRoundSeed = s;
    }
    public void setSeed(int c)
    {
        seed = c;
    }
    public void changeUpgrades(int n)
    {
        upgrades[n]++;
    }
    public int getSeed()
    {
        return seed;
    }
    public int getLastRoundSeed() {return lastRoundSeed;}
    public int[] getUpgrades()
    {
        return upgrades;
    }
}
