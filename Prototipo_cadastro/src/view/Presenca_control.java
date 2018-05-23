package view;

import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import utility.sqlite_connect;

public class Presenca_control {
	
	@FXML private Label paciente1;
	@FXML private Label paciente2;
	@FXML private Label paciente3;
	@FXML private Label dia;
	@FXML private Label hora;

	@FXML private CheckBox cb1;
	@FXML private CheckBox cb2;
	@FXML private CheckBox cb3;
	
	public Presenca_control() {
		
	}
	
	public void setDay(String paciente1, String paciente2, String paciente3, String dia, String hora) {
		this.paciente1.setText(paciente1);
		this.paciente2.setText(paciente2);
		this.paciente3.setText(paciente3);
		this.dia.setText(dia);
		this.hora.setText(hora);
	}
	
	public void printDay() {
		System.out.println(this.paciente1.getText());
		System.out.println(this.paciente2.getText());
		System.out.println(this.paciente3.getText());
		System.out.println(this.dia.getText());
		System.out.println(this.hora.getText());
	}
	
	public static void createPresenca(String paciente, int presenca, String dia, String hora) {
		String update = "INSERT INTO presenca VALUES(" + paciente + ", " + "'" + dia + "'" + ", " + "'" + hora + "'" + ", " + presenca + ");";
		System.out.println("Create = " + update);
		
		///////
		if(paciente.equals("") || paciente == null){
			//está vazio não tenta fazer nda
			System.out.println("pacinete String vazia");
		}else {
			try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
				PreparedStatement stmt = conn.prepareStatement(update);
				stmt.execute();
			}catch(SQLException e) {
				e.getStackTrace();
			}
		}	
	}
	
	/*
	public void createRegister(String paciente, int presenca) {
		
		String update = "INSERT INTO presenca VALUES(" + paciente + ", " + "'" + dia.getText()+ "'" + ", " + "'" + hora.getText() + "'" + ", " + presenca + ");";
		System.out.println("Create = " + update);
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.execute();
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	*/
	
	public void updatePresenca(String paciente, int presente) {
		
		String update = "UPDATE consulta SET " + paciente + " = " + presente + ";";
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.execute();
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	
	public boolean verifyIfExist(String dia, String hora) {
		
		String verificar = "SELECT * FROM presenca WHERE dia = " + "'" + this.dia.getText() + "'" + " hora = " + "'" + this.hora.getText() + "';";
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			PreparedStatement stmt = conn.prepareStatement(verificar);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				return false;
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return true;
	}
	
	public void registerFactory(CheckBox cb, Label paciente) {
		
		if(verifyIfExist(dia.getText(), hora.getText()) == true) {
			if(cb.isArmed() == true && !paciente.getText().equals("0")) {
				//createRegister(paciente.getText(), 1);
				createPresenca(paciente.getText(), 1, this.dia.getText(), this.hora.getText());
			}else if(cb.isArmed() == false && !paciente.getText().equals("0")){
				//createRegister(paciente.getText(), 0);
				createPresenca(paciente.getText(), 0, this.dia.getText(), this.hora.getText());
			}
		}else {
			if(cb.isArmed() == true && !paciente.getText().equals("0")) {
				updatePresenca(paciente.getText(), 1);
			}else if(cb.isArmed() == false && !paciente.getText().equals("0")){
				updatePresenca(paciente.getText(), 0);
			}	
		}
	}
	
	public void initialize() {
		
		cb1.setOnAction((MouseEvent) -> {
			System.out.println("Event on CheckBox1");
			registerFactory(cb1, paciente1);
		});
		
		cb2.setOnAction((MouseEvent) -> {
			System.out.println("Event on checkBox2");
			registerFactory(cb2, paciente2);
		});
		
		cb3.setOnAction((MouseEvent) -> {
			System.out.println("Event on CheckBox3");
			registerFactory(cb3, paciente3);
		});	
	}
}