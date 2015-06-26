package creational.builder;
/** It should be easy to add new conversion without modifying the reader */

abstract class Builder {
	public abstract void build(char token);

	public abstract String getResult();
}

/** Conversion algorithm is separated here */
class ASCIIConverter extends Builder {
	private StringBuilder result = new StringBuilder();

	public void build(char c) {
		result.append(c);
	}

	public String getResult() {
		return result.toString();
	}
}

/** Reader algorithm is here */
class Director {
	private Builder builder;

	public Director(Builder builder) {
		this.builder = builder;
	}

	public void construct(String stream) {
		// it may define a predefined order of call or may even read from a stream, just like defined bellow
		for (char c : stream.toCharArray()) {
			builder.build(c);
		}
	}
}

public class Client {

	public static void main(String[] args) {
		Builder builder = new ASCIIConverter();
		Director director = new Director(builder);
		director.construct("ABC");
		System.out.println("Conversion built: " + builder.getResult());
	}
}