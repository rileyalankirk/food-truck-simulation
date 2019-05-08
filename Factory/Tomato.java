package Factory;

public class Tomato extends Vegetables {

	@Override
	public String toString() {
		return "Tomato";
	}

	@Override
	public int price() {
		return 75;
	}

}
