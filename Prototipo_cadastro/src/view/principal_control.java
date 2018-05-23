package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utility.consultas_dia;
import utility.sqlite_connect;

public class principal_control {
	
	private String selectedDia;
	private String selectedHora;
	
	@FXML
	private Presenca_control pc_control;

	Stage stage;
	
	List<consultas_dia> horarios = new ArrayList<consultas_dia>();
	
	@FXML private Label lb_name;
	@FXML private HBox cadastrar;
	@FXML private HBox paciente;
	@FXML private HBox fisioterapeuta;
	@FXML private HBox consulta;
	@FXML private HBox pesquisa;
	@FXML private HBox usuario;
	
	@FXML private TableView<consultas_dia> tv_eventos;

	@FXML
	private TableColumn<consultas_dia, String> tc_tipo;
	@FXML
	private TableColumn<consultas_dia, String> tc_hora;
	@FXML
	private TableColumn<consultas_dia, String> tc_dia;
	
	public void init(int lvl_acesso, String nome) {
		lb_name.setText(nome);
		if(lvl_acesso == 0) {
			System.out.println("Administrador");
		}else if(lvl_acesso == 1) {
			System.out.println("Atendente");
			//paciente.setDisable(true);
		}
	}
	
	public void loadScene(String location, String tipo) {
		try {
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/view/" + location + ".fxml"));
	        AnchorPane cadastroPaciente = (AnchorPane) loader.load();
	        
	        System.out.println("location = " + "/view/" + location + ".fxml");
	        
	        if(tipo.equalsIgnoreCase("Paciente")) {
	        	cc_controller = loader.getController();
	 	        cc_controller.init("Paciente");
	        }else if(tipo.equalsIgnoreCase("Fisioterapeuta")) {
	        	cc_controller = loader.getController();
	 	        cc_controller.init("Fisioterapeuta");
	        }

	        Scene scene = new Scene(cadastroPaciente);
	        stage.setScene(scene);
			stage.show();	
		}catch(IOException e) {
			e.getStackTrace();
		}
	}

	@FXML
	private cliente_control cc_controller;
	
	@FXML
	private void initialize() {
		System.out.println("lllllllllllllL");
		loadConsultasDia();
		
		
		tv_eventos.addEventFilter(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>() {
            		public void handle(MouseEvent e) {
            			
            			if(!tv_eventos.getItems().isEmpty() && tv_eventos.getItems() != null) {
            				
            				//Instancia o presenca
            				Stage stage = null;
            				try {
            					stage = new Stage();	
            					FXMLLoader loader = new FXMLLoader();
            				    loader.setLocation(getClass().getResource("/view/Presenca.fxml"));
            				    AnchorPane principal = (AnchorPane) loader.load();
            				    
            					Scene scene = new Scene(principal);
            					
            					//pega o controler
            				    pc_control = loader.getController();
            				    
            				    //stage.setScene(scene);
            					//stage.show();
            				    
            					
            				    //Pega o dia e hora guardados na tabela 
            				    setSelectedDia(tv_eventos.getSelectionModel().getSelectedItem().getDia().toString());
            				    setSelectedHora(tv_eventos.getSelectionModel().getSelectedItem().getHora().toString());
            				    
            				    //procura e pega os dados do db
            				    //chama o método do pc_control para montar 
            				    try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
            				    	
            				    	String pesquisa = "SELECT * FROM consulta WHERE data = " + "'" + getSelectedDia() + "'" + " AND horario_inicio = " + "'" + getSelectedHora() + "'" + ";";
            				    	PreparedStatement stmt = conn.prepareStatement(pesquisa);
            				    	ResultSet rs = stmt.executeQuery();
            				    	
            				    	if(!rs.isBeforeFirst()) {
            				    		//vazio
            				    	}else {
            				    		long paciente1 = 0;
            				    		long paciente2 = 0;
            				    		long paciente3 = 0;
            				    		//long fisioterapeuta = 0;
            				    		
            				    		while(rs.next()) {
            				    			paciente1 = rs.getLong("paciente1");
            				    			paciente2 = rs.getLong("paciente2");
            				    			paciente3 = rs.getLong("paciente3");
            				    			//fisioterapeuta = rs.getLong("fisioterapeuta");
            				    			System.out.println("paciente1 = " + paciente1);
            				    			System.out.println("paciente2 = " + paciente2);
            				    			System.out.println("paciente3 = " + paciente3);
            				    			
            				    			System.out.println("paciente1 = long to string" + Long.toString(paciente1));
            				    			
            				    			
            				    			pc_control.setDay(
                				    				Long.toString(paciente1), 
                				    				Long.toString(paciente2), 
                				    				Long.toString(paciente3), 
                				    				getSelectedDia(), 
                				    				getSelectedHora()
                				    		);	
            				    			
            				    			/*
            				    			pc_control.setDay(
                				    				paciente1, 
                				    				paciente2, 
                				    				paciente3, 
                				    				getSelectedDia(), 
                				    				getSelectedHora());	
            				    			*/
            				    			pc_control.printDay();
            				    		}
            				    		
            				    		//Manda os dados para montar o paciente control
            				    		/*
            				    		pc_control.setDay(
            				    				Long.toString(paciente1), 
            				    				Long.toString(paciente2), 
            				    				Long.toString(paciente3), 
            				    				getSelectedDia(), 
            				    				getSelectedHora());	
            				    		*/
            				    	}
            				    	stage.setScene(scene);
                					stage.show();
            				    	
            				    }catch(SQLException sqle) {
            				    	sqle.getStackTrace();
            				    }
            				    
            					
            					//stage.setScene(scene);
            					//stage.show();
            				} catch (IOException ioe) {
            					ioe.printStackTrace();
            				}		
  
                			System.out.println("mouse clicked on pesquisar");	
            			}else {
            				System.out.println("A tabela está vazia");
            			}
            		}
            	}
        );
        
		
		cadastrar.setOnMouseEntered((event) -> {
			//cadastrar.setStyle("-fx-cursor: hand;");
		});
		
		
		paciente.setOnMouseClicked((MouseEvent) -> {
			System.out.println("Mouse on paciente");
			loadScene("cadastro_cliente", "Paciente");
			
			/*
			try {
				stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("/view/cadastro_cliente.fxml"));
		        AnchorPane cadastroPaciente = (AnchorPane) loader.load();

		        cc_controller = loader.getController();
		        cc_controller.init("Paciente");
				
		        Scene scene = new Scene(cadastroPaciente);
		        stage.setScene(scene);
				stage.show();	
			}catch(IOException e) {
				e.getStackTrace();
			}
			*/
		});
		
		
		fisioterapeuta.setOnMouseClicked((MouseEvent) -> {
			System.out.println("Mouse on paciente");
			loadScene("cadastro_cliente", "Fisioterapeuta");
			
			/*
			try {
				stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("/view/cadastro_cliente.fxml"));
		        AnchorPane cadastroPaciente = (AnchorPane) loader.load();

		        cc_controller = loader.getController();
		        cc_controller.init("Fisioterapeuta");
				
		        Scene scene = new Scene(cadastroPaciente);
		        stage.setScene(scene);
				stage.show();	
			}catch(IOException e) {
				e.getStackTrace();
			}
			*/
		});
		
		consulta.setOnMouseClicked((MouseEvent) -> {
			System.out.println("Mouse on paciente");
			loadScene("consulta", "");
			/*
			try {
				stage = new Stage();
				AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/consulta.fxml"));
				Scene scene = new Scene(pane);
				
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
		});
		
		usuario.setOnMouseClicked((MouseEvent) -> {
			System.out.println("Mouse on Usuário");
			loadScene("Cadastro_usuario", "");
		});
		
		pesquisa.setOnMouseClicked((MouseEvent) -> {
			loadScene("sistema_pesquisa", "");
			/*
			try {
				stage = new Stage();
				AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/sistema_pesquisa.fxml"));
				Scene scene = new Scene(pane);
				
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
		});
	}
	
	public void loadConsultasDia() {
		
		String dia = "";
		String hora = "";
		
		String dia_atual = cliente_control.getformatter.format(LocalDate.now());
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			String pesquisar = "SELECT data, horario_inicio FROM consulta";
			//String pesquisar = "SELECT * FROM consulta";
			
			PreparedStatement stmt = conn.prepareStatement(pesquisar);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				System.out.println("Não tem nenhuma consulta agendada para  hoje");
			}else {
				while(rs.next()) {
					dia = rs.getString("data");
					hora = rs.getString("horario_inicio");

					if(dia.equalsIgnoreCase(dia_atual)) {
						System.out.println("data = " + dia);
						System.out.println("hora = " + hora);
						
						horarios.add(new consultas_dia(
								"Consultas",
								dia,
								hora
						));
					}	
				}
				setTable();
			}		
		}catch(SQLException e) {
			e.getStackTrace();
		}
	}
	
	public void setTable() {
		//Printa a lista
		System.out.println("Tamanho da lista: " + horarios.size());
		
		int i = 0;
		while(i < horarios.size()) {
			tc_tipo.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTipo()));
			tc_dia.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDia()));
			tc_hora.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getHora()));
			i++;
		}
		//Mostra os dados na tabela
		tv_eventos.getItems().addAll(horarios);//original
	}

	
	public String getSelectedDia() {
		return selectedDia;
	}

	public void setSelectedDia(String selectedDia) {
		this.selectedDia = selectedDia;
	}

	public String getSelectedHora() {
		return selectedHora;
	}

	public void setSelectedHora(String selectedHora) {
		this.selectedHora = selectedHora;
	}
	
	
}
