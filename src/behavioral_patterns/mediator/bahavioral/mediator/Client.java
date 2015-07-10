package bahavioral.mediator;

import java.util.HashSet;
import java.util.Set;

class Dialog {

	private Set<Widget> widgets = new HashSet<Widget>();

	public void show() {
		System.out.println("Showing dialog");
	}

	public void addWidget(Widget widget) {
		this.widgets.add(widget);
	}
}

abstract class Director {

	abstract void widgetChanged(Widget widgetChanged);

	abstract void createWidgets();
}

abstract class DialogDirector extends Director {
	private Dialog dialog = new Dialog();
	
	public void showDialog() {
		createWidgets();
		dialog.show();
	}

	public Dialog getDialog() {
		return dialog;
	}
}

class FontDialogDirector extends DialogDirector {
	private ListBox listBox = new ListBox("_a_list_box", this);
	private EntryField entryField = new EntryField("_an_entry_field", this);

	public EntryField getEntryField() {
		return entryField;
	}

	public ListBox getListBox() {
		return listBox;
	}

	@Override
	void widgetChanged(Widget widgetChanged) {
		if (widgetChanged.getId().equals("_a_list_box")) {
			String selection = listBox.getSelection();
			entryField.setText(selection);
		}
	}

	@Override
	void createWidgets() {
		getDialog().addWidget(listBox);
		getDialog().addWidget(entryField);
	}
}

abstract class Widget {
	private Director director;
	private String id;
	
	public Widget(String id, Director director) {
		this.id = id;
		this.director = director;
	}

	public Object getId() {
		return id;
	}

	public void changed() {
		director.widgetChanged(this);
	}
}

class ListBox extends Widget {
	public ListBox(String id, Director director) {
		super(id, director);
	}

	public String getSelection() {
		return "aSelection";
	}
}

class EntryField extends Widget {
	public EntryField(String id, Director director) {
		super(id, director);
	}

	private String text;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}

public class Client {

	public static void main(String[] args) {
		FontDialogDirector director = new FontDialogDirector();
		director.showDialog();
		
		director.getListBox().changed();
		
		System.out.println("Mediator helped change content of entryField to: " + director.getEntryField().getText());
	}

}
