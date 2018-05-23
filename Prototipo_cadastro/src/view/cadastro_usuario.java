package view;

import java.security.MessageDigest;
import java.sql.DriverManager;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import utility.sqlite_connect;

public class cadastro_usuario {
	
	@FXML private TextField nome;
	@FXML private TextField sobrenome;
	@FXML private TextField cpf;
	@FXML private TextField email;
	@FXML private CheckBox acesso;
	@FXML private PasswordField senha;
	@FXML private Button cadastrar;
	
	@FXML
	private void initialize() {
		
		cadastrar.setOnAction((KeyEvent) -> {
			saveInDB();
		});
	}
	
	public void saveInDB() {
		
		int acesso = 0;
		//set to 1 atentende ou 0 adm
		if(!this.acesso.isArmed()) {
			//adm
			acesso = 0;
		}
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			String save = "INSERT INTO login (nome, sobrenome, cpf, email, acesso, senha) VALUES("
					+ "'" + nome.getText() + "',"
					+ " '" + sobrenome.getText() + "',"
					+ Long.parseLong(cpf.getText()) + ","
					+ " '" + email.getText() + "',"
					+ " " + acesso + ","
					+ "'" + hash_sha256(senha.getText()) + "'"
					+ ");";
			
			System.out.println("Saving usuário: " + save);
			
			PreparedStatement st = conn.prepareStatement(save);
			st.execute();
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	
	public static String hash_sha256(String entrada) {
		
		StringBuilder hexString = new StringBuilder();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte hash[] = digest.digest(entrada.getBytes("UTF-8"));
			
			for(byte b : hash) {
				//hexString.append(String.format("%02X", 0xFF & b));
				hexString.append(Integer.toHexString(0xFF & b));
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return hexString.toString();
	}
}
