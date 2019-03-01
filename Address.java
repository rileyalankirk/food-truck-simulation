import java.util.Comparator;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Address implements Comparable<Address> {

    boolean direction; //TRUE, 1 = east, FALSE, 0 = south
    int houseNum; //house numbers are multiples of 10, starting at 100.  However, there are no houses at the multiples of 100s, as the first house on each street would be, for example, 110.
    int streetNum; //street numbers start at 1, and go
    private static final int DISTRIBUTION_HOUSENUM = 910;
    private static final int DISTRIBUTION_STREETNUM = 9;

    Random rand = new Random();

    protected Address(){
        direction = rand.nextBoolean();
        houseNum = getRandomHouseNum();
        streetNum = getRandomStreetNum();
    }

    protected Address(int houseNum, boolean direction, int streetNum){
     if(houseNum >= 0 && houseNum < 2000)
        this.houseNum = houseNum;

     this.direction = direction;

     if(streetNum >= 0 && streetNum < 20)
         this.streetNum = streetNum;
    }

    private int getRandomNumberInRange(int min, int max){
        if (min >= max){
            throw new IllegalArgumentException("The max must be more than the min!");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public int getRandomHouseNum(){
        int n = getRandomNumberInRange(0,19);

        n = n * 100;

        n = n + (rand.nextInt(10) * 10);

        if (n % 100 == 0 || n == 0)
            n = n + 10;
        return n;
    }

    public int getRandomStreetNum(){
        int n = getRandomNumberInRange(0,20);
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

    public double distance() {
        /*   if(!direction)
            return Math.sqrt(Math.pow(DISTRIBUTION_HOUSENUM - (streetNum * 100), 2) + Math.pow((DISTRIBUTION_STREETNUM * 100) - houseNum, 2));

        return Math.sqrt(Math.pow(DISTRIBUTION_HOUSENUM - houseNum,2) + Math.pow((DISTRIBUTION_STREETNUM * 100) - (streetNum * 100),2));

        */ // BAsed on actual line distance rather than actual time distance
        if(direction){
            return Math.abs(DISTRIBUTION_HOUSENUM - houseNum) + Math.abs(DISTRIBUTION_STREETNUM * 100);
        }

        return Math.abs(DISTRIBUTION_HOUSENUM - (streetNum * 100)) + Math.abs((DISTRIBUTION_STREETNUM * 100) - houseNum);
    }

    @Override
    public String toString() {
        return Integer.toString(getHouseNum()) + " " + directionToString() + " " + Integer.toString(getStreetNum());
    }

    @Override
    public int compareTo(Address o) {
        if (distance() < o.distance())
            return -1;

        if (distance() > o.distance())
            return 1;

        return 0;
    }
}
