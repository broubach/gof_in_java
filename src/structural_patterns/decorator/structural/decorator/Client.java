package structural.decorator;
abstract class Component {
	public abstract void draw();
}

class TextView extends Component {
	private String text;

	public TextView(String text) {
		this.text = text;
	}

	public void draw() {
		System.out.println("Text: " + text);
	}
}

class Decorator extends Component {
	private Component decorated;

	public Decorator(Component decorated) {
		this.decorated = decorated;
	}

	public void draw() {
		decorated.draw();
	}
}

// decorator is also a Component
class ScrollDecorator extends Decorator {
	public ScrollDecorator(Component decorated) {
		super(decorated);
	}

	public void draw() {
		super.draw();
		System.out.println("Creating a scroll bar");
	}
}

class BorderDecorator extends Decorator {
	public BorderDecorator(Component decorated) {
		super(decorated);
	}

	public void draw() {
		super.draw();
		System.out.println("Creating a Border");
	}
}

public class Client {
	public static void main(String[] args) {
		TextView component = new TextView("random text");

		BorderDecorator borderDecorator = new BorderDecorator(component);
		ScrollDecorator scrollDecorator = new ScrollDecorator(borderDecorator);

		scrollDecorator.draw();
	}
}