package application;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application
{
	Logger info = Logger.getLogger("Main");
	
	private Stage primaryStage;
	//private BorderPane rootLayout;
	private AnchorPane rootLayout;
	
    private AnchorPane cadastroPessoa;
    private SplitPane consulta;
    private AnchorPane personDeatails;
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Fisioterapia");
		
        initConsultar();
        
        //initCadastro();
        //initTest();
        //showPersonDeatils();
        //initPesquisar();
	}
	
	public void initTest() {
		try {
			AnchorPane cadastro = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/teste.fxml"));
			Scene scene = new Scene(cadastro);
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de Clientes");
			primaryStage.show();
		}catch(Exception e) {
			info.info(e.getMessage());
		}
	}
	
	public void initCadastro() {
		
		try {
			AnchorPane cadastro = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/cadastro_cliente.fxml"));
			Scene scene = new Scene(cadastro);
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de Clientes");
			primaryStage.show();
		}catch(Exception e) {
			info.info(e.getMessage());
		}
	}
	
	public void initConsultar() {
		
		try {
			
            AnchorPane rootLayout = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/consultar.fxml"));
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Consulta");
            primaryStage.show();
            
		
		}catch(Exception e) {
			info.info(e.getMessage());
		}
	}
	
	public void initConsulta() {
		
		try {
			
            AnchorPane rootLayout = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/consulta.fxml"));
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Consulta");
            primaryStage.show();
            
		
		}catch(Exception e) {
			info.info(e.getMessage());
		}
	}
	
	public void initPesquisar() {
		
		 AnchorPane rootLayout;
		try {
			rootLayout = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/pesquisar.fxml"));
		
			// Show the scene containing the root layout.
	         Scene scene = new Scene(rootLayout);
	         primaryStage.setScene(scene);
	         primaryStage.setTitle("Consulta");
	         primaryStage.show();
	         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	
	public void showPersonDeatils() {
		
		try {
			
            AnchorPane rootLayout = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/consultar.fxml"));
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Consulta");
            primaryStage.show();
            
		}catch(Exception e) {
			info.info(e.getMessage());
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	public static void main (String args[]) {
		Main.launch(args);//Pode não funcionar teste
	}
	
	
}
