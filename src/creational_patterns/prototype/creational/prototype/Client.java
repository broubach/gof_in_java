package creational.prototype;

/** Graphic class -> Notes, staves and rests

Tool class extended by GraphicTool, creeates instances of Graphic objects

How to extend the framework without creating an extension of GraphicTool for each graphic class?

All Graphic subclass should support clone operation

"Each GraphicTool instance will produce a music object by cloning its prototype and adding the clone to the score."
*/

abstract class Graphic implements Cloneable {
	public abstract void draw(int x, int y);

	public Object clone() {
		try {
			return getClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

class Staff extends Graphic {
	public static String KEY = "staff";

	public void draw(int x, int y) {
		System.out.println("Drawing for Staff id " + toString());
	}

	public Object clone() {
		return super.clone();
	}
}

class MusicalNote extends Graphic {
	public static String KEY = "note";

	public void draw(int x, int y) {
		System.out.println("Drawing for Musical note id " + toString());
	}

	public Object clone() {
		return super.clone();
	}
}

class Rest extends Graphic {
	public static String KEY = "rest";

	public void draw(int x, int y) {
		System.out.println("Drawing for rest id " + toString());
	}

	public Object clone() {
		return super.clone();
	}
}

/** the client can actually be a prototype factory
 *  I should imagine that there is a framework based on the Graphic object.
 *  I should imagine that the client runs in a classpath different than the one used to construct the basic blocks for the Graphic object framework.
 */
public class Client {
	private java.util.Set<Graphic> canvas = new java.util.HashSet<Graphic>();
	private java.util.Map<String, Object> prototypeManager = new java.util.HashMap<String, Object>();

	public Client() {
		prototypeManager.put(MusicalNote.KEY, new MusicalNote());
		prototypeManager.put(Rest.KEY, new Rest());
		prototypeManager.put(Staff.KEY, new Staff());
	}

	public void dragToCanvas(String objectKey) {
		Graphic newObject = (Graphic) ((Graphic) prototypeManager.get(objectKey)).clone();
		newObject.draw(1, 1);
		canvas.add(newObject);
	}

	public static void main(String[] args) {
		Client graphicTool = new Client();
		graphicTool.dragToCanvas(Rest.KEY);
		graphicTool.dragToCanvas(Staff.KEY);
		graphicTool.dragToCanvas(MusicalNote.KEY);
	}
}