package structural.composite;

abstract class Graphic {
	private Graphic parent;

	abstract void draw();

	public void add(Graphic graphic) {
		graphic.setParent(this);
	}

	public void remove(Graphic graphic) {
		graphic.setParent(null);
	}

	public Graphic getChild(int i) {
		return null;
	}

	public void setParent(Graphic parent) {
		this.parent = parent;
	}

	public Graphic getParent() {
		return this.parent;
	}
}

class Line extends Graphic {
	public void draw() {
		System.out.println("Drawing a line which has as parent: " + getParent());
	}
}

class Rectangle extends Graphic {
	public void draw() {
		System.out.println("Drawing a rectangle which has as parent: " + getParent());
	}
}

class Text extends Graphic {
	private String text;

	public Text(String text) {
		this.text = text;
	}

	public void draw() {
		System.out.println("Drawing a text: " + text + " / which has as parent: " + getParent());
	}
}

class Picture extends Graphic {
	private java.util.List<Graphic> graphics = new java.util.ArrayList<Graphic>();

	public void draw() {
		System.out.println("Drawing a picture which has as parent: " + getParent());
		for (Graphic g : graphics) {
			g.draw();
		}
	}

	public void add(Graphic graphic) {
		super.add(graphic);
		graphics.add(graphic);
	}

	public void remove(Graphic graphic) {
		super.remove(graphic);
		graphics.remove(graphic);
	}

	public Graphic getChild(int i) {
		return graphics.get(i);
	}
}

public class Client {
	public static void main(String[] args) {
		Picture picture = new Picture();
		picture.add(new Line());
		picture.add(new Rectangle());
		picture.add(new Text("first text"));
		picture.add(new Picture());
		picture.getChild(3).add(new Text("second text"));

		picture.draw();
	}
}