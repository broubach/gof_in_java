package structural.adapter;

interface Manipulator {}

interface Shape { //target
	public int[] boundingBox();
	public Manipulator createManipulator();
}

class Line implements Shape {
	public int[]  boundingBox() {
		return new int[] {0,0,0,0};
	}
	public Manipulator createManipulator() {
		return new Manipulator(){};
	}
}

class TextManipulator implements Manipulator {
}

class TextShape implements Shape { //adapter
	private TextView text = new TextView();

	public int[] boundingBox() {
		return text.getExtent();
	}

	public Manipulator createManipulator() {
		return new TextManipulator();
	}
}

class TextView { // adaptee
	public int[] getExtent() {
		return new int[] {0,1,2,3};
	}
}

public class Client {
	public static void main(String[] args) {
		Shape textShape = new TextShape();
		int[] boundingBox = textShape.boundingBox();
		System.out.println("Information provided by external toolkit: " + boundingBox[0] + ", " + boundingBox[1] + ", " + boundingBox[2] + ", " + boundingBox[3]);
	}
}