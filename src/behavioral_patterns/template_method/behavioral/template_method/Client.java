package behavioral.template_method;

class View {
	private boolean focused;

	public void display() {
		setFocus();
		doDisplay();
		resetFocus();
	}

	private void setFocus() {
		this.focused = true;
	}

	protected void doDisplay() {
		
	}

	private void resetFocus() {
		this.focused = false;
	}
}

class CustomView extends View {

	@Override
	public void display() {
		System.out.println("This is the custom display");
	}
	
}

public class Client {
	
	public static void main(String[] args) {
		System.out.println("Calling standard view");
		View view = new View();
		view.display();

		System.out.println("Calling custom view");
		CustomView customView = new CustomView();
		customView.display();
	}

}
