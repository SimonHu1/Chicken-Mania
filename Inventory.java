public class Inventory
{
    private int seed;
    private int[] upgrades;
    public Inventory()
    {
        seed = 10000;
        upgrades = new int[]{0,0,0,0,0};
        //first number indicates type
        //second number indicates upgrade level or true/false
    }
    public  int getSeed()
    {
        return seed;
    }
    public void setSeed(int c)
    {
        seed = c;
    }
    public void changeUpgrades(int n)
    {
        upgrades[n]++;
    }

    public int[] getUpgrades()
    {
        return upgrades;
    }
}
