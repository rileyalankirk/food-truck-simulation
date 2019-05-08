/*
 * Authors: Originally written by Abdullah.
 */

package Factory;

public class Cheese extends Condiment {

	@Override
	public String toString() {
		return "Cheese";
	}

	@Override
	public int price() {

		return 75;
	}

}
