package view;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import utility.Consulta;
import utility.Paciente;
import utility.Pessoa;
import utility.sqlite_connect;

import java.awt.Font;
import java.beans.EventHandler;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Side;

public class consulta_control {
	
	List<Long> possible = new ArrayList<Long>();
	
	Consulta consulta = null;
	
	@FXML private AnchorPane leftPane;
	@FXML private AnchorPane rightPane;
	
	@FXML private TextField paciente1;
	@FXML private TextField paciente2;
	@FXML private TextField paciente3;
	@FXML private TextField fisioterapeuta;
	
	@FXML private ContextMenu context;
	
	@FXML private Button cadastrar;
	
	//Data e hora
	@FXML private DatePicker day;
	@FXML private String selectedDay = null;
	@FXML private Pane hor1;
	@FXML private Pane hor2;
	@FXML private Pane hor3;
	@FXML private Pane hor4;
	@FXML private Pane hor5;
	@FXML private Pane hor6;
	
	private String selectedHour = null;
	
	private boolean canRegister = false;
	
	public String setDate() {
		String retorno = null;
		try{
			//Revisar
			if(day.getEditor().getText() != null) {
				System.out.println("Iniciando verificação data");
				LocalDate ld = day.getValue();
				if(ld == null) {
					retorno = cliente_control.getformatter.format(LocalDate.now());
				}else {
					retorno = cliente_control.getformatter.format(ld);
					System.out.println("Data: " + cliente_control.getformatter.format(ld));
				}
			}else {
				System.out.println("data vazia");
			}		
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean checkValid(String text) {

		String pesquisar = "SELECT nome, cpf FROM dados_pessoais WHERE cpf = " + text;
		boolean valid = false;
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			PreparedStatement stmt = conn.prepareStatement(pesquisar);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				//false
				System.out.println("!isBeforefirst");
			}else {
				int i = 0;
				while(rs.next()) {
					possible.add(rs.getLong("cpf"));
					i++;
					
				}
				System.out.println("Elemento adicionado: " + i);
				valid = true;
			}
			
			///teste
			System.out.println("Tamanho da lista: " + possible.size());
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return valid;
	}
	
	public void getDay() {
		
	}
	
	public void setTextField(String newText, TextField tf) {
		
		System.out.println(newText);
		
		boolean valido = checkValid(newText);

		if(valido == false) {
			System.out.println("false");
			tf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color: #eb4d4b;");
		}else if(valido = true) {
			if(tf == paciente1 && !newText.equalsIgnoreCase(paciente2.getText()) && !newText.equalsIgnoreCase(paciente3.getText()) && !newText.equalsIgnoreCase(fisioterapeuta.getText())) {
				System.out.println("true paciente1");
				canRegister = true;
				tf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color: #27ae60;");
			}else if(tf == paciente2 && !newText.equalsIgnoreCase(paciente1.getText()) && !newText.equalsIgnoreCase(paciente3.getText()) && !newText.equalsIgnoreCase(fisioterapeuta.getText())) {
				System.out.println("true paciente2");
				tf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color: #27ae60;");
				canRegister = true;
			}else if(tf == paciente3 && !newText.equalsIgnoreCase(paciente1.getText()) && !newText.equalsIgnoreCase(paciente2.getText()) && !newText.equalsIgnoreCase(fisioterapeuta.getText())) {
				System.out.println("true paciente3");
				tf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color: #27ae60;");
				canRegister = true;
			}else if(tf == fisioterapeuta && !newText.equalsIgnoreCase(paciente1.getText()) && !newText.equalsIgnoreCase(paciente2.getText()) && !newText.equalsIgnoreCase(paciente3.getText())) {
				System.out.println("true fisioterapeuta");
				tf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color: #27ae60;");
				canRegister = true;
			}
		}
	}
	
	public void verifyIfExist() {
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			String verify = "SELECT * FROM consulta;";
			
			Statement st = conn.prepareStatement(verify);
			ResultSet rs = st.executeQuery(verify);
			
			if(!rs.isBeforeFirst()) {
				System.out.println("O registro não existe ainda");
			}else {
				System.out.println("Verificando se existe alguma vaga");
				while(rs.next()) {
					long paciente1 = rs.getLong("paciente1");
					long paciente2 = rs.getLong("paciente1");
					long paciente3 = rs.getLong("paciente1");
					
					if(paciente1 != 0)
						System.out.println("paciente1 está vago");
					else if(paciente2 != 0)
						System.out.println("paciente2 está vago");
					else if(paciente3 != 0)
						System.out.println("paciente3 está vago");
				}
			}
			
		}catch(SQLException e) {
			e.getSQLState();
			e.getStackTrace();
		}
	}
	
	@FXML
	private void initialize() {
		
		//Inicia a data com o dia atual
		day.getEditor().setText(cliente_control.getformatter.format(LocalDate.now()));

		cadastrar.setOnAction((ActionEvent) -> {

			setSelectedDay(setDate());
			
			System.out.println("Canregister " + canRegister);
			System.out.println("SelectedDay " + selectedDay);
			System.out.println("SelectedHour " + selectedHour);
			
			if(canRegister = true && selectedHour != null && selectedDay != null) {
				System.out.println("canRegister True");
				
				//Pega o dia
				//selectedDay = setDate();
				System.out.println("SelecttedDay: " + selectedDay);
				
				try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
					
					String pesquisar = "INSERT INTO consulta (paciente1, paciente2, paciente3, fisioterapeuta, data, horario_inicio) VALUES (?, ?, ?, ?, ?, ?)";
						
					PreparedStatement stmt = conn.prepareStatement(pesquisar);
					if(paciente1.getText() == null || paciente1.getText().isEmpty()) {
						stmt.setLong(1, 0);
					}else {
						stmt.setLong(1, Long.parseLong(paciente1.getText()));
					}
					if(paciente2.getText() == null || paciente2.getText().isEmpty()) {
						stmt.setLong(2, 0);
					}else {
						stmt.setLong(2, Long.parseLong(paciente2.getText()));
					}
					if(paciente3.getText() == null || paciente3.getText().isEmpty()) {
						stmt.setLong(3, 0);
					}else {
						stmt.setLong(3, Long.parseLong(paciente3.getText()));
					}
					if(fisioterapeuta.getText() == null || fisioterapeuta.getText().isEmpty()) {
						stmt.setLong(4, 0);
					}else {
						stmt.setLong(4, Long.parseLong(fisioterapeuta.getText()));
					}
					stmt.setString(5, selectedDay);
					stmt.setString(6, selectedHour);
					//adicionar dia e hora
					
					stmt.executeUpdate();
					
				}catch(SQLException e) {
					e.getStackTrace();
				}
			}else {
				System.out.println("Tem algum problema nos registros");
			}
		});		

		hor1.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			//change select
			selectPane(hor1);
			setSelectedHour("7:00:00");
			System.out.println("" + getSelectedHour());
		});
		
		hor2.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			selectPane(hor2);
			setSelectedHour("7:50:00");
			System.out.println("" + getSelectedHour());
		});
		
		hor3.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			selectPane(hor3);
			setSelectedHour("8:40:00");
			System.out.println("" + getSelectedHour());
		});
		
		hor4.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			selectPane(hor4);
			setSelectedHour("1:00:00");
			System.out.println("" + getSelectedHour());
		});
		
		hor5.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			selectPane(hor5);
			setSelectedHour("1:40:00");
			System.out.println("" + getSelectedHour());
		});
		
		hor6.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			selectPane(hor6);
			setSelectedHour("2:40:00");
			System.out.println("" + getSelectedHour());
		});
		
		paciente1.setOnMouseClicked((MouseEvent) -> {
			System.out.println("O mouse foi acionado em paciente1");

			//createMenu(paciente1);
			
		});
		
		//Usar o cpf para buscar
		paciente1.textProperty().addListener((obs, oldText, newText) -> {
			
				setTextField(newText, paciente1);	
			
		});
		
		
		paciente2.textProperty().addListener((obs, oldText, newText) -> {
			
			setTextField(newText, paciente2);
			
		});
		
		paciente3.textProperty().addListener((obs, oldText, newText) -> {
			
			setTextField(newText, paciente3);
			
		});
		
		fisioterapeuta.textProperty().addListener((aob, oldText, newtext) -> {
			
			setTextField(newtext, fisioterapeuta);
			
		});
	}
	
	public void selectPane(Pane hor) {	
		if(hor1 == hor)
			hor1.setStyle("-fx-border-color:#4834d4;");
		else
			hor1.setStyle("-fx-background-color: transparent;");
		if(hor2 == hor)
			hor2.setStyle("-fx-border-color:#4834d4;");
		else
			hor2.setStyle("-fx-background-color: transparent;");
		if(hor3 == hor)
			hor3.setStyle("-fx-border-color:#4834d4;");
		else
			hor3.setStyle("-fx-background-color: transparent;");	
		if(hor4 == hor)
			hor4.setStyle("-fx-border-color:#4834d4;");
		else
			hor4.setStyle("-fx-background-color: transparent;");
		if(hor5 == hor)
			hor5.setStyle("-fx-border-color:#4834d4;");
		else
			hor5.setStyle("-fx-background-color: transparent;");
		if(hor6 == hor)
			hor6.setStyle("-fx-border-color:#4834d4;");
		else
			hor6.setStyle("-fx-background-color: transparent;");
	}
	
	public void setSelectedDay(String day) {
		this.selectedDay = day;
	}
	
	public String getSelectedDay() {
		return selectedDay;
	}
	
	public void setSelectedHour(String hour) {
		this.selectedHour = hour;
	}
	
	public String getSelectedHour() {
		return selectedHour;
	}
	
}
