package Custom_Components;

import javafx.scene.control.TextField;

public class FloatTextField extends TextField{
	
	public FloatTextField() {
		this.setPromptText("Insert float numbers");
	}
	
	@Override
	public void replaceText(int i, int i1, String string) {
		if(string.matches("^([+-]?\\d*\\.?\\d*)$") || string.isEmpty()) {
			super.replaceText(i, i1, string);
		}
	}
	
	@Override
	public void replaceSelection(String string) {
		super.replaceSelection(string);
	}
	
}
