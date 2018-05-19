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
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utility.consultas_dia;
import utility.sqlite_connect;

public class principal_control {

	Stage stage;
	
	List<consultas_dia> horarios = new ArrayList<consultas_dia>();
	
	@FXML private Label lb_name;
	@FXML private HBox cadastrar;
	@FXML private HBox paciente;
	@FXML private HBox fisioterapeuta;
	@FXML private HBox consulta;
	@FXML private HBox pesquisa;
	
	@FXML private TableView<consultas_dia> eventos;

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
	
	
	/////////////////////////////////////////////
	@FXML
	private cliente_control cc_controller;
	
	@FXML
	private void initialize() {
		
		loadConsultasDia();
		
		cadastrar.setOnMouseEntered((event) -> {
			//cadastrar.setStyle("-fx-cursor: hand;");
		});
		
		paciente.setOnMouseClicked((MouseEvent) -> {
			System.out.println("Mouse on paciente");			
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
		});
		
		fisioterapeuta.setOnMouseClicked((MouseEvent) -> {
			System.out.println("Mouse on paciente");
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
		});
		
		consulta.setOnMouseClicked((MouseEvent) -> {
			System.out.println("Mouse on paciente");
			
			try {
				stage = new Stage();
				AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/consulta.fxml"));
				Scene scene = new Scene(pane);
				
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		pesquisa.setOnMouseClicked((MouseEvent) -> {
			
			try {
				stage = new Stage();
				AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/sistema_pesquisa.fxml"));
				Scene scene = new Scene(pane);
				
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	
	public void loadConsultasDia() {
		
		String dia = "";
		String hora = "";
		
		String dia_atual = cliente_control.getformatter.format(LocalDate.now());
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			String pesquisar = "SELECT data, horario_inicio FROM consulta";
			
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
		eventos.getItems().addAll(horarios);//original
	}
}
