/*
 * Authors: Originally written by Abdullah.
 */

package Factory;

public class Mustard extends Condiment {

	@Override
	public String toString() {
		return "Mustard";
	}

	@Override
	public int price() {
		return 25;
	}

}
