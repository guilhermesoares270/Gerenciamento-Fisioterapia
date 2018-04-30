package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import utility.Paciente;
import utility.sqlite_connect;

public class pesquisa_control {
	
	private Main main;
	
	private int indexSearch = 0;
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	//private ObservableList<Paciente> personData = FXCollections.observableArrayList();
	private ObservableList<Paciente> personData = FXCollections.observableArrayList();
	
	@FXML
	private TextField tf_pesquisa;
	
	@FXML
	private Button bt_pesquisar;
	
	@FXML
	private TableView<Paciente> tv_tabela;
	@FXML
	private TableColumn<Paciente, String> tc_cargo;
	@FXML
	private TableColumn<Paciente, String> tc_nome;
	@FXML
	private TableColumn<Paciente, String> tc_email;
	
	@FXML
	private ComboBox<String> cb_busca;
	
	@FXML
	private void initialize() {
		cb_busca.setPromptText("Busca");
		cb_busca.getItems().addAll("Todos","Paciente", "Fisioterapeuta");
		
		tv_tabela.setOnMouseClicked((event) -> {
			System.out.println("Clicou");
			
			TableCell tb = new TableCell();
			//Object item = cell.getTableRow().getItem();
			System.out.println("Evento " + event.getTarget());
			System.out.println(tb.getTableRow().getItem());
			
		});
		
		bt_pesquisar.setOnAction((event) -> {
			
			//if the list have itens the list will be cleaned
			clean();
			
			String consultar = tf_pesquisa.getText();
			System.out.println("Consultar  = " + consultar);
			
			try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){

				String search = "SELECT * FROM dados_pessoais dp "
						+ "LEFT JOIN endereco ed ON ed.endereco_id = dp.endereco_id  "
						+ "LEFT JOIN contato ct ON ct.contato_id = dp.contato_id "
						+ "LEFT JOIN paciente pt ON pt.pessoa_id = dp.cpf WHERE dp.nome = '" + consultar + "';";
				
				System.out.println("Search: " + search);
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(search);
				
				while(rs.next()) {
					
					//Dados pessoais
					String nome = rs.getString("nome");
					//String nascimento = rs.getDate("nascimento").toString();
					String nascimento = rs.getString("nascimento");
					String sexo = rs.getString("sexo");
					long rg = rs.getLong("rg");
					long cpf = rs.getLong("cpf");
					
					//Endereço
					String rua = rs.getString("rua");
					long numero = rs.getLong("numero");
					String bairro = rs.getString("bairro");
					String complemento = rs.getString("complemento");
					long cep = rs.getLong("cep");
					String uf = rs.getString("uf");
					
					//Contato
					String email = rs.getString("email");
					long telefone = rs.getLong("telefone");
					long celular = rs.getLong("celular");
					
					//Paciente
					long num_sus = rs.getLong("num_sus");
					String sintomas = rs.getString("sintomas");
					String medicacao = rs.getString("medicacao");
					
					//new Paciente(nome, nascimento, sexo, email, telefone, celular, rg, cpf, rua, numero, bairro, complemento, cep, uf, num_sus, sintomas, medicacao);
					
					personData.add(new Paciente (
							nome, 
							nascimento, 
							sexo, 
							email, 
							telefone, 
							celular, 
							rg, 
							cpf, 
							rua, 
							numero, 
							bairro,
							complemento, 
							cep, 
							uf,
							num_sus,
							sintomas,
							medicacao
					));
					
				}
				
				//Printa a lista
				System.out.println("Tamanho da lista: " + personData.size());
				
				//indexSearch = 1;
				int i = 0;
				while(i < personData.size()) {
					
					System.out.println("indexSearch = " + indexSearch);
					tc_nome.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNome()));
					tc_email.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEmail()));
		
					indexSearch++;
					i++;
				}
				
				//Mostra os dados na tabela
				tv_tabela.getItems().addAll(personData);

			}catch(SQLException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		});
		
	}
	
	public void clean() {
		
		if(personData.isEmpty() == false) {
			System.out.println("personData size = " + personData.size());
			System.out.println("Limpando a lista");
			tv_tabela.getItems().clear();
			personData.clear();	
			indexSearch = 0;
			System.out.println("personData size = " + personData.size());	
		}	
	}
}