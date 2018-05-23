package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.sqlite_connect;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import java.io.IOException;
import java.sql.*;

public class login_control {
	
	private principal_control pc_control;
	
	@FXML 
	private TextField nome;
	@FXML 
	private PasswordField senha;
	@FXML 
	private Button entrar;

	@FXML
	private void initialize() {

		entrar.setOnAction((event) -> {
			
			try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){

				//String pesquisa = "SELECT * FROM login WHERE nome = " + "'" + nome.getText() + "'" + " AND " + "senha = " + senha.getText();
				String pesquisa = "SELECT * FROM login WHERE nome = " + "'" + nome.getText() + "'" + " AND senha = " + "'" + cadastro_usuario.hash_sha256(senha.getText()) + "';";
				System.out.println("Pesquisa = " + pesquisa);
				
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(pesquisa);
				
				if(!rs.isBeforeFirst()) {
					System.out.println("usuário não encontrado");
				}else {
					String nome = null;
					int lvl = 0;
					while(rs.next()) {
						nome = rs.getString("nome");
						//String sobrenome = rs.getString("sobrenome");
						//long cpf = rs.getLong("cpf");
						//String email = rs.getString("email");
						lvl = rs.getInt("acesso");
						String senha = rs.getString("senha");
					}			
					loadMain(lvl, nome);
					Stage stage = (Stage)entrar.getScene().getWindow();
					stage.close();
				}		
			}catch(SQLException e) {
				e.getStackTrace();
			}
		});	
	}
	
	public void loadMain(int lvl, String nome) {
		Stage stage = null;
		try {
			stage = new Stage();	
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/view/Principal.fxml"));
		    AnchorPane principal = (AnchorPane) loader.load();
		    
			Scene scene = new Scene(principal);

			//pega o controler
		    pc_control = loader.getController();
		    pc_control.init(lvl, nome);
			
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public String getSenha() {
		return senha.getText();
	}
	
	public String getNome() {
		return nome.getText();
	}
}