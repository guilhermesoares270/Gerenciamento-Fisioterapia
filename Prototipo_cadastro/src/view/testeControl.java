package view;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;

public class testeControl implements Initializable{
	
	@FXML
	private AnchorPane rootPane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			Load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Load() throws IOException {
		System.out.println("Iniciando");
		AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("PersonDetails.fxml"));
		rootPane.getChildren().setAll(pane);
	}
	
}
