package application;

import java.util.logging.Logger;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application
{
	Logger info = Logger.getLogger("Main");
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/cadastro_cliente.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de Clientes");
			primaryStage.show();
		}catch(Exception e) {
			info.info(e.getMessage());
		}
		
		
		/*
		try {
			AnchorPane consulta = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/consulta.fxml"));
			Scene scene = new Scene(consulta);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Consulta");
			primaryStage.show();
		}catch(Exception e) {
			info.info(e.getMessage());
		}
		*/
	}
	
	
	public static void main (String args[]) {
		Main.launch(args);//Pode não funcionar teste
	}
	
	
}
