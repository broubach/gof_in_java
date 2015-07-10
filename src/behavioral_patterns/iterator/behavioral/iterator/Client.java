package behavioral.iterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

class RandomExternalIterator implements Iterator {
	private List list;
	private Iterator randomIterator;

	public RandomExternalIterator(List list) {
		this.list = list;
		this.randomIterator = new HashSet(list).iterator();
	}

	@Override
	public boolean hasNext() {
		return randomIterator.hasNext();
	}

	@Override
	public Object next() {
		return randomIterator.next();
	}

	@Override
	public void remove() {
		randomIterator.remove();
	}
}

class RandomInternalIterator extends RandomExternalIterator {

	public RandomInternalIterator(List list) {
		super(list);
	}

	public void traverse() {
		while (hasNext()) {
			processItem(next());
		}
	}
	
	public void processItem(Object item) {
		System.out.println(item);
	}
}

public class Client {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		
		System.out.println("Internal iterator");
		RandomExternalIterator iterator = new RandomExternalIterator(list);
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		System.out.println("External iterator");
		RandomInternalIterator internalIterator = new RandomInternalIterator(list);
		internalIterator.traverse();
}

}