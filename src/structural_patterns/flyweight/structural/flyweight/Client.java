package structural.flyweight;
import java.util.*;

class FlyweightFactory {
	private Map<String, Flyweight> flyweights = new HashMap<String, Flyweight>();

	public Flyweight getFlyweight(String key) {
		if (flyweights.containsKey(key)) {
			return flyweights.get(key);
		}
		ConcreteFlyweight flyweight = new ConcreteFlyweight(key);
		flyweights.put(key, flyweight);
		return flyweight;
	}
}

class Flyweight {
	public void operation(Map<String, Object> context) {
		System.out.println("This is the operation that receives an extrinsicState, which means that this object is light.");
	}
}

class ConcreteFlyweight extends Flyweight {
	// this is the intrinsic state. It should be very light and not put any weight in the design.
	private String key;

	public ConcreteFlyweight(String key) {
		this.key = key;
	}
}

class UnsharedConcreteFlyweight {
}

public class Client {
	public static void main(String[] args) {
		FlyweightFactory factory = new FlyweightFactory();
		Flyweight flyweight = factory.getFlyweight("a");
		flyweight.operation(new HashMap<String, Object>());
	}
}