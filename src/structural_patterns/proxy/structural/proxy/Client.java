package structural.proxy;
abstract class Graphic {
	public abstract void draw();
	public abstract void getExtent();
	public abstract void store();
	public abstract void load(String fileName);
}

class Image extends Graphic {
	private String fileName;

	public Image(String fileName) {
		this.fileName = fileName;
		load(fileName);
	}

	public void draw() {
		System.out.println("Drawing real image");
	}

	public void getExtent() {
		System.out.println("This is the real extent");
	}

	public void store() {
		System.out.println("Image can be stored");
	}

	public void load(String fileName) {
		System.out.println("Image was loaded: " + fileName);
	}
}

class ImageProxy extends Graphic {
	private Image image;
	private String fileName;
	private String extent;

	public ImageProxy(String fileName, String extent) {
		this.fileName = fileName;
		this.extent = extent;
	}

	public void draw() {
		if (image == null) {
			loadImage();
		}
		image.draw();
	}

	public void getExtent() {
		if (image == null) {
			System.out.println("This is the fake extent");
		} else {
			image.getExtent();
		}
	}

	public void store() {
		if (image == null) {
			loadImage();
		}
		image.store();
	}

	public void load(String fileName) {
		System.out.println("Image was loaded: " + fileName);
	}

	private void loadImage() {
		image = new Image(fileName);
	}
}

public class Client {
	public static void main(String[] args) {
		ImageProxy proxy = new ImageProxy("randomfile.jpg", "1600x200");
		proxy.getExtent();
		proxy.draw();
	}
}