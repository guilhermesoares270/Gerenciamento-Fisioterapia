package Custom_Components;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class AutoCompleteTextField<T> extends TextField{
	
	private List<T> content = new ArrayList<T>();
	private ContextMenu context = new ContextMenu();
	
	AutoCompleteTextField(ArrayList<T> l) {
		content.addAll(content);
		
		textProperty().addListener((obs, oldText, newText) -> {
			if(getText().length() == 0) {
				
			}else {
				createMenu();
			}
		});
	}

	public void createMenu() {

		int i = 0;
		while(i < content.size()) {
			context.getItems().add(new MenuItem(content.get(i).toString()));
			i++;
		}
        context.show(this, Side.BOTTOM, 0, 0);	
	}
}
