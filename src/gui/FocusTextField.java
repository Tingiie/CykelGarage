package gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class FocusTextField extends JTextField implements FocusListener{
	private static final long serialVersionUID = 8946985698864615304L;

	public FocusTextField() {
		super();
		addFocusListener(this);
	}
	
	public FocusTextField(int arg0) {
		super(arg0);
		addFocusListener(this);
	}
	
	public FocusTextField(String arg0) {
		super(arg0);
		addFocusListener(this);
	}
	
	public FocusTextField(String arg0, int arg1) {
		super(arg0, arg1);
		addFocusListener(this);
	}
	
	public FocusTextField(Document arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
		addFocusListener(this);
	}
	
	public void focusGained(FocusEvent e) {
		select(0, getText().length());
	}

	public void focusLost(FocusEvent e) {
		select(0, 0);
	}
}



