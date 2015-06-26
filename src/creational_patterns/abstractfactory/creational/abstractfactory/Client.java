package creational.abstractfactory;

abstract class WidgetAbstractFactory {

	// a Factory Method for each product
	public abstract ScrollBar createScrollBar();

	// a Factory Method for each product
	public abstract Window createWindow();
}

class MotifWidgetFactory extends WidgetAbstractFactory {
	private static MotifWidgetFactory instance;

	public ScrollBar createScrollBar() {
		return new MotifScrollBar();
	}

	public Window createWindow() {
		return new MotifWindow(createScrollBar());
	}

	public static MotifWidgetFactory getInstance() {
		if (instance == null) {
			instance = new MotifWidgetFactory();
		}
		return instance;
	}
}

class PMWidgetFactory extends WidgetAbstractFactory {
	private static PMWidgetFactory instance;

	public ScrollBar createScrollBar() {
		return new PMScrollBar();
	}

	public Window createWindow() {
		return new PMWindow(createScrollBar());
	}

	public static PMWidgetFactory getInstance() {
		if (instance == null) {
			instance = new PMWidgetFactory();
		}
		return instance;
	}
}

abstract class Window {
	private ScrollBar scrollBar;

	public Window(ScrollBar scrollBar) {
		this.scrollBar = scrollBar;
	}

	public void scrollDown() {
		scrollBar.scrollDown();
	}

	public void scrollUp() {
		scrollBar.scrollUp();
	}
}

class MotifWindow extends Window {
	MotifWindow(ScrollBar scrollBar){
		super(scrollBar);
	}
}

class PMWindow extends Window {
	PMWindow(ScrollBar scrollBar){
		super(scrollBar);
	}
}

abstract class ScrollBar {
	public abstract void scrollDown();

	public abstract void scrollUp();
}

class MotifScrollBar extends ScrollBar {

	public void scrollDown() {
		System.out.println("Rolling down motif");
	}

	public void scrollUp() {
		System.out.println("Rolling up motif");
	}
}

class PMScrollBar extends ScrollBar {
	public void scrollDown() {
		System.out.println("Rolling down PM");
	}

	public void scrollUp() {
		System.out.println("Rolling up PM");
	}
}

/**
 * Makes it easiear to change behavior of a set of classes, called products in this Pattern.
 *
 * By default, the AbstractFactory is implemented using Factory Methods, but it could also be
 * written based on the Prototype pattern.
 */
public class Client {

	public static void main(String args[]) {
		// Clients controll what type of family will be instantiated here, only once during the application
		WidgetAbstractFactory abstractFactory = PMWidgetFactory.getInstance();

		// Clients don't know from now on what are the concrete classes
		Window window = abstractFactory.createWindow();
		window.scrollDown();
		window.scrollUp();
	}
}