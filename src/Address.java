import java.util.Random;

public class Address implements Comparable<Address>
{

    private boolean direction; //TRUE, 1 = east, FALSE, 0 = south
    private int houseNum; //house numbers are multiples of 10, starting at 100.  However, there are no houses at the multiples of 100s, as the first house on each street would be, for example, 110.
    private int streetNum; //street numbers start at 1, and go
    private String time;
    private static final int DISTRIBUTION_HOUSENUM = 910;
    private static final int DISTRIBUTION_STREETNUM = 9;

    Random rand = new Random();

    protected Address()
    {
        direction = rand.nextBoolean();
        houseNum = getRandomHouseNum();
        streetNum = getRandomStreetNum();
        time = getRandomTime();
    }

    protected Address(int houseNum, boolean direction, int streetNum, String time)
    {
     if(houseNum >= 0 && houseNum < 2000)
        this.houseNum = houseNum;

     this.direction = direction;

     if(streetNum >= 0 && streetNum < 20)
         this.streetNum = streetNum;
      
      this.time = time;
    }

    private int getRandomNumberInRange(int min, int max)
    {
        if (min >= max){
            throw new IllegalArgumentException("The max must be more than the min!");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public int getRandomHouseNum()
    {
        int n = getRandomNumberInRange(0,19);

        n = n * 100;

        n = n + (rand.nextInt(10) * 10);

        if (n % 100 == 0 || n == 0)
            n = n + 10;
        return n;
    }

    public int getRandomStreetNum()
    {
        int n = getRandomNumberInRange(0,20);
        return n;
    }
    
    public String getRandomTime(){
        // last order at 18:59 (7pm)
       int time_hours = getRandomNumberInRange(10, 18); 
       int time_minutes = getRandomNumberInRange(0, 59);
       //time_minutes = (time_hours == 19) ? 0 : time_minutes;
       
       String t = ((time_hours%12==0) ? "12" : String.valueOf(time_hours%12)) + ":" + (time_minutes>9 ? time_minutes : "0"+time_minutes) + " " +((time_hours>=12) ? "PM" : "AM");
       return t;
        
        
    }


    public boolean isDirection() {
        return direction;
    }

    public String directionToString()
    {
        if (direction == false)
            return "South";
        else
            return "East";
    }

    public int getHouseNum()
    {
        return houseNum;
    }

    public int getStreetNum()
    {
        return streetNum;
    }
    
    public String getTime(){
        return time;
    }

    public double distance()
    {
        /*   if(!direction)
            return Math.sqrt(Math.pow(DISTRIBUTION_HOUSENUM - (streetNum * 100), 2) + Math.pow((DISTRIBUTION_STREETNUM * 100) - houseNum, 2));

        return Math.sqrt(Math.pow(DISTRIBUTION_HOUSENUM - houseNum,2) + Math.pow((DISTRIBUTION_STREETNUM * 100) - (streetNum * 100),2));

        */ // BAsed on actual line distance rather than actual time distance
       /*
        if(direction){
            return Math.abs(DISTRIBUTION_HOUSENUM - houseNum) + Math.abs(DISTRIBUTION_STREETNUM * 100);
        }

        return Math.abs(DISTRIBUTION_HOUSENUM - (streetNum * 100)) + Math.abs((DISTRIBUTION_STREETNUM * 100) - houseNum);
        
        /*
         Based on time
        */
        
        // order starts at 10:00 AM
        
        String orderTime[] = time.split(":");
        int hours = Integer.parseInt(orderTime[0]);
        int minutes = Integer.parseInt(orderTime[1]);

        
        if(hours >= 1 && hours <=7)
            hours +=12;
        
        int diff_hrs = hours - 10;

        return (double) (diff_hrs*60) + minutes;
        
         
            
        
}

    @Override
    public String toString()
    {
        return Integer.toString(getHouseNum()) + " " + directionToString() + " " + Integer.toString(getStreetNum()) + " " + getTime();
    }

    @Override
    public int compareTo(Address o)
    { 
        if (distance() < o.distance())
            return -1;

        if (distance() > o.distance())
            return 1;

        return 0;
    }
}
