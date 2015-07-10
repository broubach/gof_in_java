package behavioral.memento;

import java.util.Stack;

class Originator {
	private StringBuilder internalState = new StringBuilder();

	public Memento createMemento() {
		return new Memento(internalState.toString());
	}
	
	public void setState(Memento memento) {
		this.internalState = new StringBuilder(memento.getState());
	}

	public void updateState() {
		internalState.append("A");
	}
	
	@Override
	public String toString() {
		return internalState.toString();
	}
}

class Memento {
	private String state;

	public Memento(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

public class Caretaker {
	private Stack<Memento> mementos = new Stack<Memento>();
	
	public void addMemento(Memento memento) {
		mementos.push(memento);
	}
	
	public static void main(String[] args) {
		Originator originator = new Originator();
		System.out.println("Originator before: " + originator);

		Caretaker caretaker = new Caretaker();
		caretaker.addMemento(originator.createMemento());
		
		originator.updateState();
		caretaker.addMemento(originator.createMemento());

		originator.updateState();
		caretaker.addMemento(originator.createMemento());
		System.out.println("Originator after: " + originator);
		
		
		caretaker.get();
		originator.setState(caretaker.get());
		System.out.println("Restored previous originator: " + originator);
	}

	private Memento get() {
		return mementos.pop();
	}
}