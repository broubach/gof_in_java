package appendix.btree;
/** Knuth btree implementation **/
class Btree {
	private int max;
	private Node root;

	public Btree(int max) {
		this.max = max;
		this.root = new Node();
	}

	public void insert(int value) {
		Node node = search(value);
		node.addElement(value);
		balanceNode(node);
	}

	public void balanceNode(Node node) {
		System.out.println("Balancing node");
		if (node.getElements().length >= max) {
			int middleIndex = max/2;
			System.out.println("Middle index found: " + middleIndex);
			if (max%2 == 0) {
				middleIndex += 1;
			    System.out.println("Middle index recalculated: " + middleIndex);
			}
			int middle = node.getElements()[middleIndex];
			System.out.println("Middle found: " + middle);

			if (node.getParent() == null) {
				Node parent = new Node();
				parent.setElements(new int[0]);
				parent.setChildren(new Node[] {node});
				node.setParent(parent);
				System.out.println("New parent created");
				if (node == root) {
					root = parent;
					System.out.println("Parent is new root");
				}
			}

			int[][] leftAndRightElements = node.removeElement(middleIndex);
			node.setElements(leftAndRightElements[0]);
			Btree.debugArray("Left elements", leftAndRightElements[0]);
			Btree.debugArray("Right elements", leftAndRightElements[1]);

			Node newRightNode = new Node();
			newRightNode.setElements(leftAndRightElements[1]);
			newRightNode.setParent(node.getParent());

			node.getParent().addChild(newRightNode);
			node.getParent().addElement(middle);

		    balanceNode(node.getParent());
		}
	}

	public Node search(int value) {
		return search(value, root);
	}

	private Node search(int value, Node parent) {
		if (parent.getChildren() != null) {
			// for each children of parent
			for (Node node : parent.getChildren()) {
				// if value is less then last element in child
				if (value <= node.getLastElement()) {
					return search(value, node);
				}
				// end for
			}
			if (parent.getLastChild() != null) {
				return search(value, parent.getLastChild());
			}
		}
		return parent;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public static void debugArray(String message, int[] array) {
		System.out.print(message + " ");
		for (int i=0; i<array.length; i++) {
			System.out.print(array[i]);
		}
		System.out.print('\n');
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("BTree: M=" + max + '\n');
		stringBuilder.append(root);
		return stringBuilder.toString();
	}
}

class Node {
	private Node parent;
	private int[] elements = new int[0];
	private Node[] children = new Node[0];

	public Node[] getChildren() {
		return children;
	}

	public void setChildren(Node[] children) {
		this.children = children;
	}

	public Node getLastChild() {
		if (children.length > 0) {
			return children[children.length - 1];
		}
		return null;
	}

	public int[] getElements() {
		return elements;
	}

	public void setElements(int[] elements) {
		this.elements = elements;
	}

	public int getLastElement() {
		if (elements.length > 0) {
			return elements[elements.length - 1];
		}
		return -1;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getLevel() {
		return getLevel(0, parent);
	}

	private int getLevel(int currentLevel, Node node) {
		if (node != null) {
			return getLevel(currentLevel + 1, node.getParent());
		}
		return currentLevel;
	}

	public int[][] removeElement(int elementIndex) {
		int[] arrayLeft = new int[elementIndex];
		System.arraycopy(elements, 0, arrayLeft, 0, elementIndex);
		Btree.debugArray("arrayLeft: ", arrayLeft);
		int[] arrayRight = new int[elements.length-(elementIndex+1)];
		System.arraycopy(elements, elementIndex+1, arrayRight, 0, elements.length-(elementIndex+1));
		Btree.debugArray("arrayRight: ", arrayRight);

		int[] newElements = new int[elements.length-1];
		System.arraycopy(arrayLeft, 0, newElements, 0, arrayLeft.length);
		System.arraycopy(arrayRight, 0, newElements, arrayLeft.length, arrayRight.length);
		elements = newElements;
		Btree.debugArray("newElements: ", newElements);
		return new int[][]{arrayLeft, arrayRight};
	}

	public void addChild(Node child) {
		Node[] newChildren = new Node[children.length + 1];
		System.arraycopy(children, 0, newChildren, 0, children.length);
		newChildren[newChildren.length-1] = child;

		Node lowestChild = null;
		int lowestIndex = -1;
		Node aux = null;
		for (int startingIndex = 0; startingIndex<newChildren.length; startingIndex++) {
			lowestChild = newChildren[startingIndex];
			lowestIndex = startingIndex;
			for (int i=startingIndex+1; i<newChildren.length; i++) {
				if (newChildren[i].getLastElement() < lowestChild.getLastElement()) {
					lowestChild = newChildren[i];
					lowestIndex = i;
				}
			}
			if (lowestIndex > startingIndex) {
				aux = newChildren[startingIndex];
				newChildren[startingIndex] = newChildren[lowestIndex];
				newChildren[lowestIndex] = aux;
			}
		}

		children = newChildren;
	}

	public void addElement(int newElement) {
		int[] newElements = new int[elements.length + 1];
		System.arraycopy(elements, 0, newElements, 0, elements.length);
		newElements[newElements.length-1] = newElement;
		java.util.Arrays.sort(newElements);
		elements = newElements;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i=0; i<getLevel();i++) {
			builder.append('*');
		}
		for (int element : elements) {
			builder.append(element + " ");
		}
		builder.append('\n');
		if (children != null && children.length > 0) {
			for (Node child : children) {
				builder.append(child);
			}
		}
		return builder.toString();
	}
}

public class Client {
	private static Node buildRoot() {
		Node root = new Node();
		root.setElements(new int[] {22});

		Node[] rootChildren = new Node[2];
		root.setChildren(rootChildren);

		Node child1 = new Node();
		child1.setParent(root);
		child1.setElements(new int[] {5, 10});
		rootChildren[0] = child1;

		Node[] child1Children = new Node[3];
		child1.setChildren(child1Children);

		Node child2 = new Node();
		child2.setParent(child1);
		child2.setElements(new int[] {2, 3});
		child1Children[0] = child2;

		child2 = new Node();
		child2.setParent(child1);
		child2.setElements(new int[] {6, 7});
		child1Children[1] = child2;

		child2 = new Node();
		child2.setParent(child1);
		child2.setElements(new int[] {17, 24});
		child1Children[2] = child2;

		child1 = new Node();
		child1.setParent(root);
		child1.setElements(new int[] {50,67});
		rootChildren[1] = child1;

		child1Children = new Node[3];
		child1.setChildren(child1Children);

		child2 = new Node();
		child2.setParent(child1);
		child2.setElements(new int[] {44,45});
		child1Children[0] = child2;

		child2 = new Node();
		child2.setParent(child1);
		child2.setElements(new int[] {55,66});
		child1Children[1] = child2;

		child2 = new Node();
		child2.setParent(child1);
		child2.setElements(new int[] {68,70});
		child1Children[2] = child2;

		return root;
	}

	// This tree is incomplete and the insertion algorithm is buggy. But it served as a good start for understanding how btrees work.
	public static void main(String[] args) {
		Btree btree = new Btree(3);

		btree.insert(10);
		btree.insert(50);
		btree.insert(2);
		btree.insert(3);
		btree.insert(5);
		btree.insert(7);
		btree.insert(17);
		btree.insert(22);
		btree.insert(44);
		btree.insert(45);
		btree.insert(55);
		btree.insert(66);
		btree.insert(68);
		btree.insert(70);
		System.out.println(btree);
	}
}