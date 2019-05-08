package Factory;


import java.util.ArrayList;

/*
 * Authors: Originally written by Abdullah.
 */

public abstract class SandwichTruck {
	private ArrayList<Price> ingredients  = new ArrayList<Price>();
	private int Price = 0;

	protected abstract String getType();

	public void ingredient(Price ingredient) {
		ingredients.add(ingredient);
		Price += ingredient.price();
	}

	public int fullPrice() {

		return Price + (Price / 10);
	}

	public String toString() {

		String show = getType() + "topped ";
		for (Price ingredient : ingredients) {
			show = show + ingredients.toString();
		}
		show = show.substring(0, show.length() - 1);
		return show;
	}
}



