package Factory;

public class Turkey extends Meat {

	@Override
	public String toString() {
		return "Turkey";
	}

	@Override
	public int price() {
		return 125;
	}

}
