package view;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import utility.Consulta;
import utility.sqlite_connect;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;

public class consulta_control {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private List<Long> possible = new ArrayList<Long>();
	
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
	private boolean canAlter = false;
	
	private String update = null;
	
	boolean paci1 = true;
	boolean paci2 = true;
	boolean paci3 = true;
	boolean fisio = true;
	
	//Pega a data selecionada no DatePicker
	public String setDate() {
		String retorno = null;
		try{
			if(day.getEditor().getText() != null) {
				//System.out.println("Iniciando verificação data");
				LocalDate ld = day.getValue();
				if(ld == null) {
					retorno = cliente_control.getformatter.format(LocalDate.now());
				}else {
					retorno = cliente_control.getformatter.format(ld);
					//System.out.println("Data: " + cliente_control.getformatter.format(ld));
				}
			}else {
				System.out.println("data vazia");
			}		
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
		return retorno;
	}

	//Verifica se o cpf passado é válido
	public boolean checkCValid(String text) {
		possible.clear();
		String pesquisar = "SELECT nome, cpf FROM dados_pessoais WHERE cpf = " + text;
		boolean valid = false;
		
		try {
			long cpf = Long.parseLong(text);
			
			//Maintain 'pesquisar' exactly like it was
		}catch(NumberFormatException e) {
			//System.out.println("Não é um numero");
			e.getMessage();
			
			//change 1pesquisar to search by name
			pesquisar = "SELECT nome, cpf FROM dados_pessoais WHERE nome = " + "'" + text + "'" + ";"; 
		}
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			PreparedStatement stmt = conn.prepareStatement(pesquisar);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				//false
				//System.out.println("!isBeforefirst");
			}else {
				int i = 0;
				while(rs.next()) {
					possible.add(rs.getLong("cpf"));
					i++;			
				}
				//System.out.println("Elemento adicionado: " + i);
				valid = true;
			}
			//System.out.println("Tamanho da lista: " + possible.size());
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		
		return valid;
	}
	
	//Muda a cor do TextField para 
	//vermelho caso não seja válido e para 
	//verde caso seja válido
	public void setTextField(String newText, TextField tf) {
		
		System.out.println(newText);

		boolean valido = checkCValid(newText);

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
	
	///////////////////
	///modificar
	//////////////////
	public void verifyIfExist(Pane hora) {
		
		Pane hora_encontrada = null;
	
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			System.out.println("Day.Value() " + selectedDay);
			String verify = "SELECT * FROM consulta WHERE data = " + "'" + selectedDay + "';";
			
			System.out.println("Verify = " + verify);
			
			PreparedStatement stmt = conn.prepareStatement(verify);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				System.out.println("O registro não existe ainda");
			}else {
				//System.out.println("Verificando se existe alguma vaga");
				while(rs.next()) {
					System.out.println("" + day.getEditor().getText());
					
					String data = rs.getString("data");
					System.out.println("Data atual sqlite " + data);
					String horario_inicio = rs.getString("horario_inicio");
					
					System.out.println("Comparação data e selecttedDay: " + data.equalsIgnoreCase(selectedDay));
					//Verifica se  existe algum cadastro nesse dia
					if(data.equalsIgnoreCase(selectedDay)) {//cliente_control.getformatter.format(LocalDate.now()))) {
						
						System.out.println("Já tem cadastrado nesse dia");
							
						//////////////////////////////////////////////////
						canAlter = true;///////////////
						canRegister = false;
						System.out.println("------------------------ canregister = false ------------------------");
							
						String horario_cadastrado = "";
						if(horario_inicio.equalsIgnoreCase("7:00:00")) {
							hora_encontrada = hor1;
							horario_cadastrado = "07:00:00";
							System.out.println("No horario: 07:00:00");
						}else if(horario_inicio.equalsIgnoreCase("7:50:00")) {
							hora_encontrada = hor2;
							System.out.println("No horario: 07:50:00");
						}else if(horario_inicio.equalsIgnoreCase("8:40:00")) {
							hora_encontrada = hor3;
							System.out.println("No horario: 08:40:00");
						}else if(horario_inicio.equalsIgnoreCase("1:00:00")) {
							hora_encontrada = hor4;
							System.out.println("No horario: 01:00:00");
						}else if(horario_inicio.equalsIgnoreCase("1:50:00")) {
							hora_encontrada = hor5;
							System.out.println("No horario: 01:50:00");
						}else if(horario_inicio.equalsIgnoreCase("2:40:00")) {
							hora_encontrada = hor6;
							System.out.println("No horario: 02:40:00");
						}else {
							System.out.println("Horário??????????");
						}
						
					long paciente1 = rs.getLong("paciente1");
					long paciente2 = rs.getLong("paciente2");
					long paciente3 = rs.getLong("paciente3");
					
					//Em teste
					long fisioterapeuta = rs.getLong("fisioterapeuta");
						
					/*
					 * Caso algum dos testes esteja ocupado irá mudar para a rotina de alterar
					 * e irá mudar o canRegister para falso
					*/
					if(hora_encontrada == hora) {
						if(Long.toString(paciente1).length() == 11) {
							System.out.println("paciente1 está cadastrado");
								
							this.paciente1.setText(Long.toString(paciente1));
							this.paciente1.setDisable(true);
							paci1 = false;
											
							System.out.println("Valor do tf = " + this.paciente1.getText());
						}else {
							System.out.println("paciente1 está vago");
						}

						if(Long.toString(paciente2).length() == 11) {
							System.out.println("paciente2 está cadastrado");
								
							this.paciente2.setText(Long.toString(paciente2));
							this.paciente2.setDisable(true);
							paci2 = false;
								
							System.out.println("Valor do tf = " + this.paciente2.getText());
						}else {
							System.out.println("paciente2 está vago");
						}

						if(Long.toString(paciente3).length() == 11) {
							System.out.println("paciente3 está cadastrado");
								
							this.paciente3.setText(Long.toString(paciente3));
							this.paciente3.setDisable(true);
							paci3 = false;
								
							System.out.println("Valor do tf = " + this.paciente3.getText());
						}else {
							System.out.println("paciente3 está vago");
						}
						if(Long.toString(fisioterapeuta).length() == 11) {
							System.out.println("fisioterapeuta está cadastrado");
							
							this.fisioterapeuta.setText(Long.toString(paciente3));
							this.fisioterapeuta.setDisable(true);
							paci3 = false;
								
							System.out.println("Valor do tf = " + this.paciente3.getText());
						}
					}	
				}
			}
		}
	}catch(SQLException e) {
		e.getSQLState();
		e.getStackTrace();
	}finally {
			System.out.println("Terminando verifyIfExist");
	}
}
	
	public String BuildUpdate(boolean paciente1, boolean paciente2, boolean paciente3, boolean fisioterapeuta) {
		System.out.println("Iniciando BuildUpdate");
		String update = "UPDATE consulta SET ";
		
		if(paciente1 == true) {
			if(!this.paciente1.getText().isEmpty()) {
				update += " paciente1 = " + this.paciente1.getText() + ", ";
			}else {
				update += "paciente1 = " + null + ",";
			}		
		}else {
			update += "paciente1 = (SELECT paciente1 FROM consulta WHERE data = " + "'" + selectedDay + "'" + " AND " + "horario_inicio = " + "'" + selectedHour + "'" + "), ";
		}
		if(paciente2 == true) {
			if(!this.paciente2.getText().isEmpty()) {
				update += " paciente2 = " + this.paciente2.getText() + ",";
			}else {
				update += " paciente2 = " + null + ",";
			}	
		}else {
			update += "paciente2 = (SELECT paciente2 FROM consulta WHERE data = " + "'" + selectedDay + "'" + " AND " + "horario_inicio = " + "'" + selectedHour + "'" + "), ";
		}
		if(paciente3 == true) {
			if(!this.paciente3.getText().isEmpty()) {
				update += " paciente3 = " + this.paciente3.getText() + ", ";
			}else {
				//System.out.println("Getting null");
				update += " paciente3 = " +  null + ", ";
			}	
		}else {
			update += "paciente3 = (SELECT paciente3 FROM consulta WHERE data = " + "'" + selectedDay + "'" + " AND " + "horario_inicio = " + "'" + selectedHour + "'" + "), ";
		}
		if(fisioterapeuta == true) {
			if(!this.fisioterapeuta.getText().isEmpty()) {
				update += " fisioterapeuta = " + this.fisioterapeuta.getText() + " ";
			}else {
				System.out.println("Getting null");
				update += " paciente3 = " +  null + " ";
			}
		}else {
			update += "fisioterapeuta = (SELECT fisioterapeuta FROM consulta WHERE data = " + "'" + selectedDay + "'" + " AND " + "horario_inicio = " + "'" + selectedHour + "'" + "), ";
		}
		update += "WHERE data = " + "'" + getSelectedDay() + "'" + " AND horario_inicio = " + "'" + getSelectedHour() + "';";
		//update += ";";
		
		return update;
	}
	
	public void commitUpdate(String builtUpdate) {
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			PreparedStatement stmt = conn.prepareStatement(builtUpdate);
			stmt.executeUpdate();
			
			//Statement stmt = conn.prepareStatement(builtUpdate);
			//stmt.executeUpdate(builtUpdate);
			
		}catch(SQLException e) {
			logger.log(Level.WARNING, e.getSQLState(), e);
			e.getStackTrace();
		}
		
	}
	
	public void commitFirst() {
		
		
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

			stmt.executeUpdate();
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	
	public void cleanAllTf() {
		paciente1.setDisable(false);
		paciente2.setDisable(false);
		paciente3.setDisable(false);
	}
	
	@FXML
	private void initialize() {
	
		//Inicia a data com o dia atual
		day.getEditor().setText(cliente_control.getformatter.format(LocalDate.now()));
		
		////////////////////////////////
		setSelectedDay(cliente_control.getformatter.format(LocalDate.now()));
		////////////////////
		
		day.valueProperty().addListener((ov, oldValue, newValue) -> {
			//cliente_control.getformatter.format(newValue);
			setSelectedDay(cliente_control.getformatter.format(newValue));
			System.out.println("Nova data: " + getSelectedDay());
            //picker2.setValue(newValue.plusDays(noOfDaysToAdd));
        });
		
		cadastrar.setOnAction((ActionEvent) -> {

			setSelectedDay(setDate());
			
			System.out.println("Canregister " + canRegister);
			System.out.println("SelectedDay " + selectedDay);
			System.out.println("SelectedHour " + selectedHour);
			
			
			if(canAlter == true) {
				System.out.println("canRegister set to false");
				canRegister = false;
			}

			if(canRegister == true && selectedHour != null && selectedDay != null) {
				System.out.println("canRegister True");

				System.out.println("SelecttedDay: " + selectedDay);
				
				//commitUpdate(update);
				commitFirst();
			}else if (canAlter == true) {
				
				System.out.println("canAlter = true");
				
				update = BuildUpdate(paci1, paci2, paci3, fisio);
				//System.out.println("update = " + update);
				commitUpdate(update);
				
			}else {
				System.out.println("Tem algum problema nos registros");
			}
			
		});		
		
		hor1.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			cleanAllTf();
			selectPane(hor1);
			setSelectedHour("7:00:00");
			//setSelectedDay(cliente_control.getformatter.format((TemporalAccessor) day.getEditor()));
			System.out.println("" + getSelectedDay());
			
			System.out.println("" + getSelectedHour());
			verifyIfExist(hor1);
		});
		
		hor2.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			cleanAllTf();
			selectPane(hor2);
			
			setSelectedHour("7:50:00");
			System.out.println("" + getSelectedHour());
			verifyIfExist(hor2);
		});
		
		hor3.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			cleanAllTf();
			selectPane(hor3);
			setSelectedHour("8:40:00");
			System.out.println("" + getSelectedHour());
			verifyIfExist(hor3);
		});
		
		hor4.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			selectPane(hor4);
			setSelectedHour("1:00:00");
			System.out.println("" + getSelectedHour());
			verifyIfExist(hor4);
		});
		
		hor5.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			selectPane(hor5);
			setSelectedHour("1:40:00");
			System.out.println("" + getSelectedHour());
			verifyIfExist(hor5);
		});
		
		hor6.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			selectPane(hor6);
			setSelectedHour("2:40:00");
			System.out.println("" + getSelectedHour());
			verifyIfExist(hor6);
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
