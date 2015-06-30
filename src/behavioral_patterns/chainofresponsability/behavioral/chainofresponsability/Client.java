package behavioral.chainofresponsability;

class HelpHandler {
	
	public static int NO_HELP_TOPIC = -1;
	
	private HelpHandler successor;
	private int topic;

	public HelpHandler(HelpHandler successor, int topic) {
		this.successor = successor;
		this.topic = topic;
	}
	
	public int getTopic() {
		return topic;
	}

	public boolean hasHelp() {
		return topic != NO_HELP_TOPIC;
	}
	
	public void handleHelp() {
		if (successor != null) {
			successor.handleHelp();
		}
	}
}

class Widget extends HelpHandler {

	public Widget(HelpHandler parent, int topic) {
		super(parent, topic);
	}

}

class Button extends Widget {

	public Button(HelpHandler successor, int topic) {
		super(successor, topic);
	}
}

class Dialog extends Widget {

	public Dialog(HelpHandler successor, int topic) {
		super(successor, topic);
	}

	@Override
	public void handleHelp() {
		if (hasHelp()) {
			System.out.println("Help available! The index is " + getTopic());
		}
		super.handleHelp();
	}
}

class Application extends HelpHandler {

	public Application(int topic) {
		super(null, topic);
	}
}

public class Client {
	
	public static void main(String[] args) {
		Application application = new Application(0);
		Dialog dialog = new Dialog(application, 1);
		Button button = new Button(dialog, HelpHandler.NO_HELP_TOPIC);
		
		button.handleHelp();
	}
}