/*
 * Authors: Originally written by Abdullah.
 */

package Factory;

public class ContainerCost extends Container {

	@Override
	public String toString() {
		return "ContainerCost";
	}

	@Override
	public int price() {
		return 75;
	}

}
