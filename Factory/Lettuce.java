/*
 * Authors: Originally written by Abdullah.
 */

package Factory;

public class Lettuce extends Vegetables {

	@Override
	public String toString() {
		return "Lettuce";
	}

	@Override
	public int price() {
		return 50;
	}

}
