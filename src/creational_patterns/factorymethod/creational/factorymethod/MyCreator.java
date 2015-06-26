package creational.factorymethod;
abstract class Product {
	public void open() {
		System.out.println("Default open implementation");
	}
}

class MyProduct extends Product {
	public void open() {
		System.out.println("MyProduct open implementation");
	}
}

abstract class Creator {
	private java.util.List<Product> docs = new java.util.ArrayList<Product>();

	// this is a factory method
	public abstract Product createProduct();

	public void newProduct() {
		Product doc = createProduct();
		docs.add(doc);
		doc.open();
	}
}

public class MyCreator extends Creator {
	// this is a factory method
	public Product createProduct() {
		return new MyProduct();
	}

	public static void main(String[] args) {
		Creator myCreator = new MyCreator();
		myCreator.newProduct();
	}
}