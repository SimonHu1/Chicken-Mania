import Projectiles.Shovel;
import Projectiles.Tractor;
import Projectiles.Watermelon;

public class Round
{
    private int num;
    private Gameplay gameplay;
    private double waveTime;
    public Round(int num,Gameplay gameplay)
    {
        this.num = num;
        this.gameplay = gameplay;
        waveTime=30;
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    public void createWave()
    {
        switch(num)
        {
            case 1:
            {
                baseRound(5,0,0);
                waveTime = 15.0;
                break;
            }
            case 2:
            {
                baseRound(10,0,0);
                waveTime = 15.0;
                break;
            }
            case 3:
            {
                baseRound(10,5,90);
                waveTime = 20.0;
                break;
            }
            case 4:
            {
                baseRound(15,15,0);
                waveTime = 25.0;
                break;
            }
            case 5:
            {
                baseRound(10,10,5);
                waveTime = 25.0;
                break;
            }
            case 6:
            {
                baseRound(20,15,5);
                waveTime = 25.0;
                break;
            }
            case 7:
            {
                baseRound(5,30,5);
                waveTime = 25.0;
                break;
            }
            case 8:
            {
                baseRound(45,5,5);
                waveTime = 25.0;
                break;
            }
            case 9:
            {
                baseRound(5,5,30);
                waveTime = 25.0;
                break;
            }
            case 10:
            {
                baseRound(45,30,50);
                waveTime = 45.0;
                break;
            }
            case 11:
            {
                baseRound(40,10,75);
                waveTime = 30.0;
                break;
            }
            case 12:
            {
                baseRound(45,25,75);
                waveTime = 30.0;
                break;
            }
            case 13:
            {
                baseRound(60,35,50);
                waveTime = 35.0;
                break;
            }
            case 14:
            {
                baseRound(10,100,100);
                waveTime = 35.0;
                break;
            }
            case 15:
            {
                baseRound(10,10,130);
                waveTime = 35.0;
                break;
            }
            case 16:
            {
                baseRound(50,10,130);
                waveTime = 35.0;
                break;
            }
            case 17:
            {
                baseRound(120,30,50);
                waveTime = 35.0;
                break;
            }
            case 18:
            {
                baseRound(100,80,80);
                waveTime = 35.0;
                break;
            }
            case 19:
            {
                baseRound(120,120,120);
                waveTime = 35.0;
                break;
            }
            case 20:
            {
                baseRound(160,160,160);
                waveTime = 60.0;
                break;
            }
        }
    }
    public void baseRound(int toolFreq, int vehicleFreq, int fruitFreq)
    {
        if((int)(Math.random()*100+1)<=gameplay.getBaseStats()[15][0])
        {
            return;
        }
        if(Math.random()*1000>=1000-toolFreq)
        {
            gameplay.getObstacles().add(new Shovel(2000+(int)(Math.random()*620),324,72,gameplay.getImagesArray().get(1),calcDamage(1)));
        }
        if(Math.random()*1000>=1000-(vehicleFreq)&&!gameplay.isContainsTractor())
        {
            gameplay.getObstacles().add(new Tractor(576,420,gameplay.getImagesArray().get(3),calcDamage(2)));
            System.out.println("Projectiles.Tractor made");
            System.out.println(gameplay.getObstacles().size());
            gameplay.setContainsTractor(true);
        }
        if(Math.random()*1000>=1000-(fruitFreq))
        {
            gameplay.getObstacles().add(new Watermelon(100,100,gameplay.getImagesArray().get(2),calcDamage(3)));
            ((Watermelon)gameplay.getObstacles().get(gameplay.getObstacles().size()-1)).setyVelocity((double) ((gameplay.getCharacterY()-(gameplay.getObstacles().get(gameplay.getObstacles().size()-1)).getyVal())*50-66248)/91);
            //41405/91: 41405 is with 10 grav; 91 is frames it takes for x to match
            System.out.println("Projectiles.Watermelon made");
            System.out.println(gameplay.getObstacles().size());
        }
        gameplay.generateSeeds();
        gameplay.generatePowerup();
    }
    public int calcDamage(int t)
    {
        switch(t)
        {
            case 1: return (int)(2+(num/3)*gameplay.getBaseStats()[12][0]);
            case 2: return (int)(8+(num/3*gameplay.getBaseStats()[14][0]));
            case 3: return (int)(1+(num/5)*gameplay.getBaseStats()[13][0]);
        }
        return 0;
    }
    public double getWaveTime()
    {
        return waveTime;
    }
    public Shovel randomShovel()
    {
        return new Shovel(2000+(int)(Math.random()*620),324,72,gameplay.getImagesArray().get(1),calcDamage(1));
    }
}
