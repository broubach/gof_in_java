package behavioral.strategy;

class Composition {
	private Compositor compositor;
	private Component[] components = new Component[] { new Component(1, 1, 2, 2, 3, 3), new Component(2, 2, 3, 3, 4, 4),
			new Component(3, 3, 4, 4, 5, 5) };
	private int lineWidth = 80;
	private int lineCount = 100;

	public Composition(Compositor compositor) {
		this.compositor = compositor;
	}

	public void repair() {
		int[][] natural = new int[components.length][2];
		for (int i = 0; i < components.length; i++) {
			natural[i][0] = components[i].getWidth();
			natural[i][1] = components[i].getHeight();
		}

		int[][] stretchability = new int[components.length][2];
		for (int i = 0; i < components.length; i++) {
			stretchability[i][0] = components[i].getStretchabilityWidth();
			stretchability[i][1] = components[i].getStretchabilityHeight();
		}

		int[][] shrinkability = new int[components.length][2];
		for (int i = 0; i < components.length; i++) {
			shrinkability[i][0] = components[i].getShrinkabilityWidth();
			shrinkability[i][1] = components[i].getShrinkabilityHeight();
		}
		int componentCount = components.length;
		// each position is a line
		int[] lineBreaks = new int[components.length];
		
		System.out.println("About to calculate..");

		int breakCount = compositor.compose(natural, stretchability, shrinkability, componentCount, lineBreaks);
		
		System.out.println("Good! Now let's lay down the components according to the calculated lineBreaks.");

	}
}

class Component {
	private int height;
	private int width;
	private int stretchabilityWidth;
	private int stretchabilityHeight;
	private int shrinkabilityWidth;
	private int shrinkabilityHeight;

	public Component(int width, int height, int stretchabilityWidth, int stretchabilityHeight, int shrinkabilityWidth,
			int shrinkabilityHeight) {
		this.width = width;
		this.height = height;
		this.stretchabilityWidth = stretchabilityWidth;
		this.stretchabilityHeight = stretchabilityHeight;
		this.shrinkabilityWidth = shrinkabilityWidth;
		this.shrinkabilityHeight = shrinkabilityHeight;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getStretchabilityWidth() {
		return stretchabilityWidth;
	}

	public void setStretchabilityWidth(int stretchabilityWidth) {
		this.stretchabilityWidth = stretchabilityWidth;
	}

	public int getStretchabilityHeight() {
		return stretchabilityHeight;
	}

	public void setStretchabilityHeight(int stretchabilityHeight) {
		this.stretchabilityHeight = stretchabilityHeight;
	}

	public int getShrinkabilityWidth() {
		return shrinkabilityWidth;
	}

	public void setShrinkabilityWidth(int shrinkabilityWidth) {
		this.shrinkabilityWidth = shrinkabilityWidth;
	}

	public int getShrinkabilityHeight() {
		return shrinkabilityHeight;
	}

	public void setShrinkabilityHeight(int shrinkabilityHeight) {
		this.shrinkabilityHeight = shrinkabilityHeight;
	}
}

interface Compositor {
	public int compose(int[][] natural, int[][] stretchability, int[][] shrinkability, int componentCount,
			int[] lineBreaks);
}

class SimpleCompositor implements Compositor {

	@Override
	public int compose(int[][] natural, int[][] stretchability, int[][] shrinkability, int componentCount,
			int[] lineBreaks) {
		System.out.println("Calculation done by " + this.getClass().getName());
		return 0;
	}

}

class TeXCompositor implements Compositor {

	@Override
	public int compose(int[][] natural, int[][] stretchability, int[][] shrinkability, int componentCount,
			int[] lineBreaks) {
		System.out.println("Calculation done by " + this.getClass().getName());
		return 0;
	}

}

class ArrayCompositor implements Compositor {

	@Override
	public int compose(int[][] natural, int[][] stretchability, int[][] shrinkability, int componentCount,
			int[] lineBreaks) {
		System.out.println("Calculation done by " + this.getClass().getName());
		return 0;
	}
}

public class Client {

	public static void main(String[] args) {
		Composition quick = new Composition(new SimpleCompositor());
		quick.repair();

		Composition slick = new Composition(new TeXCompositor());
		slick.repair();

		Composition iconic = new Composition(new ArrayCompositor());
		iconic.repair();
	}
}