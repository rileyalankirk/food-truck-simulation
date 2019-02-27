import java.util.Random;

public class Address {

    boolean direction; //TRUE, 1 = east, FALSE, 0 = south
    int houseNum; //house numbers are multiples of 10, starting at 100.  However, there are no houses at the multiples of 100s, as the first house on each street would be, for example, 110.
    int streetNum; //street numbers start at 1, and go

    Random rand = new Random();

    protected Address(){
        direction = rand.nextBoolean();
        houseNum = getRandomHouseNum();
        streetNum = getRandomStreetNum();
    }

    public int getRandomHouseNum(){
        int n = rand.nextInt(20);

        if(n == 0)
            n = 1;

        n = n * 100;

        n = n + (rand.nextInt(10) * 10);

        if (n % 100 == 0)
            n = n + 10;

        return n;
    }

    public int getRandomStreetNum(){
        int n = rand.nextInt(10);

        if (n == 0)
            n = 1;

        return n;

    }


    public boolean isDirection() {
        return direction;
    }

    public String directionToString(){
        if (direction == false)
            return "South";
        else
            return "East";
    }

    public int getHouseNum() {
        return houseNum;
    }

    public int getStreetNum() {
        return streetNum;
    }


    @Override
    public String toString() {
        return Integer.toString(getHouseNum()) + " " + directionToString() + " " + Integer.toString(getStreetNum());
    }
}
