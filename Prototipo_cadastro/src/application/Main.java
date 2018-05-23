package application;

import java.util.logging.Logger;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application
{
	private Logger info = Logger.getLogger("Main");
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Fisioterapia");
		
        initLogin();
	}
	
	public void initLogin() {
		
		try {
			AnchorPane cadastro = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/Login.fxml"));
			Scene scene = new Scene(cadastro);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tela Inicial");
			primaryStage.setResizable(false);
			//primaryStage.resizableProperty().setValue(Boolean.FALSE);
			primaryStage.show();
		}catch(Exception e) {
			info.info(e.getMessage());
		}
	}
	
	public void initPrincipal() {
		
		try {
			AnchorPane cadastro = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/Principal.fxml"));
			Scene scene = new Scene(cadastro);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tela Inicial");
			primaryStage.setResizable(false);
			//primaryStage.resizableProperty().setValue(Boolean.FALSE);
			primaryStage.show();
		}catch(Exception e) {
			info.info(e.getMessage());
			e.getStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main (String args[]) {
		Main.launch(args);
	}	
}
