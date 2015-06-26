package structural.bridge;
abstract class Window {
	protected WindowImpl impl;

	public Window(WindowImpl impl) {
		this.impl = impl;
	}

	public void drawRect(int[] xCoords, int[] yCoords) {
		impl.drawLine(xCoords[0], yCoords[0], xCoords[1], yCoords[1]);
		impl.drawLine(xCoords[1], yCoords[1], xCoords[2], yCoords[2]);
		impl.drawLine(xCoords[2], yCoords[2], xCoords[3], yCoords[3]);
		impl.drawLine(xCoords[3], yCoords[3], xCoords[0], yCoords[0]);
	}

	public void drawText(String text) {
		impl.drawText(text);
	}

	public abstract void draw(int[] xCoords, int[] yCoords);
}

class IconWindow extends Window {
	String caption;
	public IconWindow(WindowImpl impl, String caption) {
		super(impl);
		this.caption = caption;
	}

	public void drawBorder(int[] xCoords, int[] yCoords, String caption) {
		drawRect(xCoords, yCoords);
		drawText(caption);
	}

	public void draw(int[] xCoords, int[] yCoords) {
		drawBorder(xCoords, yCoords, caption);
	}
}

class TransientWindow extends Window {
	public TransientWindow(WindowImpl impl) {
		super(impl);
	}

	public void drawCloseBox(int[] xCoords, int[] yCoords) {
		drawRect(xCoords, yCoords);
		drawText("X");
	}

	public void draw(int[] xCoords, int[] yCoords) {
		drawRect(xCoords, yCoords);
		drawCloseBox(new int[] {0,0,0,0}, new int[] {1,1,1,1});
	}
}

abstract class WindowImpl {
	abstract void drawLine(int p1x, int p1y, int p2x, int p2y);
	abstract void drawText(String text);
}

class XWindowImpl extends WindowImpl {
	public void drawLine(int p1x, int p1y, int p2x, int p2y) {
		System.out.println("X line coords: " + p1y + "," + p1x + "->" + p2y + "," + p2x);
	}

	public void drawText(String text) {
		System.out.println("X text: " + text);
	}
}

class PMWindowImpl extends WindowImpl {
	public void drawLine(int p1x, int p1y, int p2x, int p2y) {
		System.out.println("PM line coords: " + p1y + "," + p1x + "->" + p2y + "," + p2x);
	}

	public void drawText(String text) {
		System.out.println("PM text: " + text);
	}
}

public class Client {
	public static void main(String[] args) {
		Window window = new TransientWindow(new PMWindowImpl());
		window.draw(new int[] {0, 1, 2, 3}, new int[] {0, 1, 2, 3});
	}
}