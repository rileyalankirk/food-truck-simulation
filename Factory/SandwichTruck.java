package Factory;


import java.util.ArrayList;

/**
 *
 * @author Abdullah
 *
 */
public abstract class SandwichTruck {
	private ArrayList<Price> toppings = new ArrayList<Price>();
	private int totalPrice = 0;

	protected abstract String getType();

	public void addTopping(Price t) {
		toppings.add(t);
		totalPrice += t.getPriceInCents();
	}

	public int getTotalPrice() {

		return totalPrice + (totalPrice / 10);
	}

	public String toString() {
		//TODO add typeing thing
		String ret = getType() + " ";
		for (Price t : toppings) {
			ret = ret + t.toString();
		}
		ret = ret.substring(0, ret.length() - 1);
		return ret;
	}
}



//public abstract class SandwichTruck {
//
//
//    protected abstract Sandwich createSandwitch(String item);
//
//    public Sandwich orderSandwich(String type) {
//        Sandwich sandwich = createSandwitch(type);
//        System.out.println("--- Making a " + sandwich.getName() + " ---");
//        Sandwich.prepare();
//        sandwich.cooking();
//        sandwich.cut();
//        sandwich.box();
//        return sandwich;
//    }
//}