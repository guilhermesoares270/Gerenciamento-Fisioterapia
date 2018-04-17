package view;

import utility.*;
import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class consulta_control {
	
	private int indexSearch = 0;
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	private ObservableList<Paciente> personData = FXCollections.observableArrayList();
	
	@FXML
	private TextField tf_pesquisa;
	
	@FXML
	private Button bt_pesquisar;
	
	@FXML
	private TableView<Paciente> tv_tabela;
	@FXML
	private TableColumn<Paciente, String> tc_id;
	@FXML
	private TableColumn<Paciente, String> tc_nome;
	@FXML
	private TableColumn<Paciente, String> tc_email;
	
	@FXML
	private void initialize() {
		
		bt_pesquisar.setOnAction((event) -> {
			
			//if the list have itens the list will be cleaned
			clean();
			
			String consultar = tf_pesquisa.getText();
			//sqlite_connect sq = new sqlite_connect();
			try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
				
				String search = "SELECT rowid, * FROM paciente WHERE name = " + "'" + consultar + "'" + ";";
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(search);
				
				while(rs.next()) {
					
					
					String nome = rs.getString("name");
					String cep = rs.getString("cep");
					String email = rs.getString("email");
					String sus = rs.getString("sus");
					String cpf = rs.getString("cpf");
					
					personData.add(new Paciente(
							new SimpleStringProperty(nome), 
							new SimpleStringProperty(cep), 
							new SimpleStringProperty(email),
							new SimpleStringProperty(sus), 
							new SimpleStringProperty(cpf)
							));
				}
				
				tv_tabela.getItems().addAll(personData);	
				
				//Printa a lista
				System.out.println("Tamanho da lista: " + personData.size());
				
				//indexSearch = 1;
				int i = 0;
				while(i < personData.size()) {
					/*
					System.out.println("-------------Iniciando print-------------");
					System.out.println("Nome: " + personData.get(i).getNome().get());
					System.out.println("CEP: " + personData.get(i).getCep().get());
					System.out.println("e-mail: " + personData.get(i).getEmail().get());
					System.out.println("SUS: " + personData.get(i).getNum_sus().get());
					System.out.println("CPF: " + personData.get(i).getCpf().get());
					*/
					
					System.out.println("indexSearch = " + indexSearch);
					tc_nome.setCellValueFactory(value -> value.getValue().getNome());
					tc_email.setCellValueFactory(value -> value.getValue().getEmail());
					//tc_id.setCellValueFactory(value -> new SimpleStringProperty("" + indexSearch));
					//tc_nome.setCellValueFactory(value -> new SimpleStringProperty(personData.get(indexSearch).getNome().get()));
					//tc_email.setCellValueFactory(value -> new SimpleStringProperty(personData.get(indexSearch).getEmail().get()));				
					 
					indexSearch++;
					i++;
				}
				
				//System.out.println("e-mail: " + personData.get(0).getEmail().get());
				//System.out.println("e-mail: " + personData.get(1).getEmail().get());
				
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
