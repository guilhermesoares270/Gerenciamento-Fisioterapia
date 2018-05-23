package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import utility.Paciente;
import utility.Pessoa;
import utility.sqlite_connect;

public class pesquisa_control {

	private long limit = 2;
	private long offset = 0;
	
	private long indexSearch = limit;
	private String consultar;
	private long maxNumberOfResults;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private ObservableList<Pessoa> personData = FXCollections.observableArrayList();

	private Pessoa selected_pessoa = null;
	
	@FXML private TextField tf_pesquisa;
	@FXML private Button bt_pesquisar;
	@FXML private Button bt_anterior;
	@FXML private Button bt_proximo;
	@FXML private Label lb_pageNumber;
	@FXML public TableView<Pessoa> tv_tabela;
	@FXML private TableColumn<Pessoa, String> tc_cargo;
	@FXML private TableColumn<Pessoa, String> tc_nome;
	@FXML private TableColumn<Pessoa, String> tc_email;
	@FXML private ComboBox<String> cb_busca;

	@FXML
	private void initialize() throws IOException {

		cb_busca.setPromptText("Busca");
		cb_busca.getItems().addAll("Todos","Paciente", "Fisioterapeuta");

		bt_anterior.setOnAction((event) -> {
			System.out.println("Anterior pressed");
			if(indexSearch >= 4) {//original 20
				System.out.println("index >= 4");
				indexSearch -= limit;
				
				offset -= 2;
				
				personData.clear();
				tv_tabela.getItems().clear();
				searchData(consultar);
				
				setTable();
			}
		});
		
		bt_proximo.setOnAction((event) -> {
			System.out.println("Próximo pressed");
			if(indexSearch >= 2 && indexSearch < maxNumberOfResults) {
				System.out.println("Maior que 2 e menor que maxResult");
				
				indexSearch += limit;
				
				if(indexSearch <= maxNumberOfResults) {
					offset += 2;
					
					personData.clear();
					tv_tabela.getItems().clear();
					searchData(consultar);
					
					setTable();
				}
				
			}
		});
		
		bt_pesquisar.setOnAction((event) -> {
			
			////////////teste///////////////
			personData.clear();
			tv_tabela.getItems().clear();
			///////////////////////////////
			
			consultar = tf_pesquisa.getText();
			System.out.println("Consultar  = " + consultar);
			
			searchData(consultar);
			
			setTable();
			
		});	
	}
	
	public void setTable() {

		System.out.println("Tamanho da lista: " + personData.size());

		int i = 0;
		while(i < personData.size()) {
			tc_nome.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNome()));
			tc_email.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEmail()));
			
			i++;
		}
		
		//Mostra os dados na tabela
		tv_tabela.getItems().addAll(personData);//original
	}
	
	public void searchData(String consultar) {
		
		String buscaPaciente = "paciente";
		String buscaFisioterapeuta = "fisioterapeuta";

		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			Statement stmt = null;
			ResultSet rs = null;
			
			if(consultar.matches("\\d+") | consultar.length() == 11) {
					
				String searchByCpf = "SELECT * FROM dados_pessoais dp "
						+ "LEFT JOIN endereco ed ON ed.endereco_id = dp.endereco_id  "
						+ "LEFT JOIN contato ct ON ct.contato_id = dp.contato_id "
						+ "LEFT JOIN paciente pt ON pt.pessoa_id = dp.cpf "	
						+ "WHERE dp.cpf = '" + consultar + "' LIMIT " + limit + " OFFSET "+ offset + ";";
				
				maxNumberOfResults = getMaxResults();
				System.out.println("Numero máximo de resultados: " + maxNumberOfResults);
				
				
				System.out.println("Search: " + searchByCpf);
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(searchByCpf);
				
			}else {

				String searchByName = "SELECT * FROM dados_pessoais dp "
						+ "LEFT JOIN endereco ed ON ed.endereco_id = dp.endereco_id  "
						+ "LEFT JOIN contato ct ON ct.contato_id = dp.contato_id "
						+ "LEFT JOIN paciente pt ON pt.pessoa_id = dp.cpf "
						+ "WHERE dp.nome = '" + consultar + "'" + " COLLATE NOCASE " + "LIMIT " + limit + " OFFSET "+ offset + ";";
				
				maxNumberOfResults = getMaxResults();
				System.out.println("Numero máximo de resultados: " + maxNumberOfResults);
			
				lb_pageNumber.setText("1/" + maxNumberOfResults);
				
				System.out.println("Search: " + searchByName);
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(searchByName);
			}		

			while(rs.next()) {
				
				//Dados pessoais
				String nome = rs.getString("nome");
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
			
			System.out.println("Tamanho do index " + indexSearch);
			
		}catch(SQLException e) {
			e.getSQLState();
			e.getStackTrace();
		}	
	}
	
	//Revisar
	public long getMaxResults() {
		long counter = 0;
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){

			Statement stmt = null;
			ResultSet res = null;
			
			if(consultar.matches("\\d+") | consultar.length() == 11) {
				
				stmt = conn.createStatement();
				res = stmt.executeQuery("SELECT * FROM dados_pessoais dp "  
						+ "LEFT JOIN endereco ed ON ed.endereco_id = dp.endereco_id " 
						+ "LEFT JOIN contato ct ON ct.contato_id = dp.contato_id " 
						+ "LEFT JOIN paciente pt ON pt.pessoa_id = dp.cpf "  
						+ "WHERE dp.cpf = '" + consultar + "'");
			}else {
				stmt = conn.createStatement();
				res = stmt.executeQuery("SELECT * FROM dados_pessoais dp "  
						+ "LEFT JOIN endereco ed ON ed.endereco_id = dp.endereco_id " 
						+ "LEFT JOIN contato ct ON ct.contato_id = dp.contato_id " 
						+ "LEFT JOIN paciente pt ON pt.pessoa_id = dp.cpf "  
						+ "WHERE dp.nome = '" + consultar + "'");
			}	
			
			while(res.next()) {
				counter++;
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return counter;
	}
	
		//Pessoa getters and setter
		public void setPessoa(Pessoa pessoa) {
			this.selected_pessoa = pessoa;
		}
		
		public Pessoa getPessoa() {
			return selected_pessoa;
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