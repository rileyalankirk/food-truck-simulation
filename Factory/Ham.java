package Factory;

public class Ham extends Meat {

	@Override
	public String toString() {
		return "Ham";
	}

	@Override
	public int getPriceInCents() {
		return 150;
	}

}
