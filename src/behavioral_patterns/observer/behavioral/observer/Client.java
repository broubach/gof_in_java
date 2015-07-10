package behavioral.observer;

import java.util.HashSet;
import java.util.Set;

abstract class Observer {

	protected Subject subject;

	/**
	 * @param subject
	 *            this subject is useful when an observer can watch multiple
	 *            subjects. In this case, we don't need to store the object
	 *            above.
	 */
	public Observer(Subject subject) {
		this.subject = subject;
	}

	public abstract void update(Subject subject);
}

abstract class Subject {
	private Set<Observer> observers = new HashSet<Observer>();

	public void attach(Observer observer) {
		observers.add(observer);
	}

	public void detach(Observer observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

	public abstract String getId();
}

class ClockTimer extends Subject {
	private String id;
	private int hour;
	private int minute;
	private int second;

	public ClockTimer(String id) {
		this.id = id;
	}

	public void tick() {
		second++;
		if (second >= 60) {
			second = 0;
			minute++;
			if (minute >= 60) {
				minute = 0;
				hour++;
			}
		}
		notifyObservers();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(hour).append(":").append(minute).append(":").append(second).toString();
	}
}

class DigitalClock extends Observer {

	public DigitalClock(ClockTimer subject) {
		super(subject);
		subject.attach(this);
	}

	@Override
	public void update(Subject subject) {
		if (this.subject.getId().equals(((ClockTimer) subject).getId())) {
			draw();
		}
	}

	private void draw() {
		System.out.println("Digital: " + subject);
	}
}

class AnalogClock extends Observer {

	public AnalogClock(ClockTimer subject) {
		super(subject);
		subject.attach(this);
	}

	@Override
	public void update(Subject subject) {
		if (this.subject.getId().equals(((ClockTimer) subject).getId())) {
			draw();
		}
	}

	private void draw() {
		System.out.println("Analogic: " + subject);
	}
}

public class Client {

	public static void main(String args[]) {
		ClockTimer timer = new ClockTimer("id1");

		DigitalClock digitalClock = new DigitalClock(timer);
		AnalogClock analogClock = new AnalogClock(timer);

		timer.tick();
		timer.tick();

	}

}
