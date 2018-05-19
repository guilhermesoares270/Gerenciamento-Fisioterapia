package view;

import java.security.MessageDigest;
import java.sql.DriverManager;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import utility.sqlite_connect;

public class cadastro_usuario {
	
	@FXML private TextField nome;
	@FXML private TextField sobrenome;
	@FXML private TextField cpf;
	@FXML private TextField email;
	@FXML private TextField acesso;
	@FXML private TextField senha;
	@FXML private Button cadastrar;
	
	@FXML
	private void initialize() {
		
		cadastrar.setOnAction((KeyEvent) -> {
			saveInDB();
		});
	}
	
	public void saveInDB() {
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			String save = "INSERT INTO login nome, sobrenome, cpf, email, acesso, senha VALUES("
					+ "'" + nome.getText() + "'"
					+ "'" + sobrenome.getText() + "'"
					+ Long.parseLong(cpf.getText())
					+ "'" + email.getText() + "'"
					+ "'" + acesso.getText() + "'"
					+ "'" + hash_sha256(senha.getText()) + "'"
					+ ");";
			
			Statement st = conn.prepareStatement(save);
			st.execute(save);
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	
	public String hash_sha256(String entrada) {
		StringBuffer hexString = new StringBuffer();
		try {
			//cria o messageDigest para SHA-256
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte hash[] = entrada.getBytes("UTF-8");
			digest.digest(hash);
			
			
			for(int i = 0; i < hash.length; i++) {
				String hex = Long.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
		        hexString.append(hex);
		    }
			
		}catch(Exception e) {
			e.getMessage();
		}
		return hexString.toString();
	}
}
