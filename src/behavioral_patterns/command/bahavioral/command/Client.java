package bahavioral.command;

abstract class Command {

	public abstract void execute();
}

class OpenCommand extends Command {

	private Application receiver;

	public OpenCommand(Application receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		String name = askUser();
		Document doc = new Document(name);
		((Application) receiver).add(doc);
		doc.open();
		System.out.println("Great! Command was executed!");
	}

	public String askUser() {
		System.out.println("Asking where to place file");
		return "randomPlace";
	}
}

class Document {

	private String name;

	public Document(String name) {
		this.name = name;
	}

	public void open() {
		System.out.println("Opening document name " + name);
	}

}

class UI {
	private Command command;

	// only one command in this example
	public void setCommand(Command command) {
		this.command = command;
	}

	public void triggerCommand() {
		command.execute();
	}
}

class Application {
	Document[] docs = new Document[0];

	// this is an example of what could be an <<action>>
	public void add(Document doc) {
		Document[] newDocs = new Document[docs.length + 1];

		System.arraycopy(docs, 0, newDocs, 0, docs.length);

		newDocs[docs.length] = doc;

		this.docs = newDocs;
	}

}

public class Client {

	public static void main(String[] args) {
		Command command = new OpenCommand(new Application());
		UI invoker = new UI();
		invoker.setCommand(command);

		invoker.triggerCommand();

		// other UIs or the same UI could invoke other commands, not only the
		// one described here
	}
}
