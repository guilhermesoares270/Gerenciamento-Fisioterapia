package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utility.*;

public class showPersonDetails_control {
	
	@FXML
	private StringProperty sp_nome = new SimpleStringProperty();
	
	@FXML
	private Label lb_nome;
	@FXML private Label lb_nascimento;
	@FXML private Label lb_sexo;
	@FXML private Label lb_rg;
	@FXML private Label lb_cpf;
	
	@FXML private Label lb_rua;
	@FXML private Label lb_numero;
	@FXML private Label lb_bairro;
	@FXML private Label lb_complemento;
	@FXML private Label lb_cep;
	@FXML private Label lb_uf;
	
	@FXML private Label lb_email;
	@FXML private Label lb_telefone;
	@FXML private Label lb_celular;
	
	//Opcionais
	@FXML private Label lb_num_sus;
	@FXML private Label lb_sintomas;
	@FXML private Label lb_medicacao;
	
	@FXML private Label lb_crefito;
	
	@FXML
	private void initialize() {
		setNome("TESTE");
		//lb_nome.setText(sp_nome.get());	
	}
	
	public void getDetails(Pessoa pessoa) {
		
	}
	
	public void setDeatails() {
		
		//lb_nome.setText();
	}
	
	public void setNome(String sp) {
		//this.sp_nome.set(sp);
		//lb_nome.setText(sp_nome.get());
		lb_nome.setText(sp);
	}
	
}
