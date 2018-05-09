package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utility.*;

public class showPersonDetails_control {
	
	//private Pessoa pessoa;
	
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

		lb_nascimento.setText("ttttttttttt");

	}
	
	public void setTeste() {
		lb_nome.setText("Teeeste");
		//lb_nascimento.setText("17-03-1994");
	}
	
	//Verificar se irá funcionar com os atributos privados
	public void setPersonInfo(Pessoa pessoa) {
		
		if(pessoa == null) {
			System.out.println("Nenhum objeto selecionado");
		}else if(pessoa.getClass() == Paciente.class) {
			
			System.out.println("Objeto bate com os requerimentos de Paciente");
			System.out.println("Nome = " + pessoa.getNome());
			lb_nome.setText(pessoa.getNome());
			
			basicInfo(pessoa);
			
			lb_num_sus.setText(Long.toString(((Paciente) pessoa).getNum_sus()));
			lb_sintomas.setText(((Paciente) pessoa).getSintomas());
			lb_medicacao.setText(((Paciente) pessoa).getMedicacao());
			
		}else if(pessoa.getClass() == Fisioterapeuta.class) {
			
			basicInfo(pessoa);
			lb_crefito.setText(Long.toString(((Fisioterapeuta) pessoa).getCrefito()));
		}
		
	}
	
	public void basicInfo(Pessoa pessoa) {
		System.out.println("Iniciando basicInfo");
		lb_nome.setText(pessoa.getNome());
		lb_nascimento.setText(pessoa.getNascimento());
		lb_sexo.setText(pessoa.getSexo());
		lb_rg.setText(Long.toString(pessoa.getRg()));
		lb_cpf.setText(Long.toString(pessoa.getCpf()));
		
		lb_rua.setText(pessoa.getRua());
		lb_numero.setText(Long.toString(pessoa.getNumero()));
		lb_bairro.setText(pessoa.getBairro());
		lb_complemento.setText(pessoa.getComplemento());
		lb_cep.setText(Long.toString(pessoa.getCep()));
		lb_uf.setText(pessoa.getUf());
		
		lb_email.setText(pessoa.getEmail());
		lb_telefone.setText(Long.toString(pessoa.getTelefone()));
		lb_celular.setText(Long.toString(pessoa.getCelular()));
		
	}
	
	public void setNome(String sp) {
		lb_nome.setText(sp);
	}
	
	public void setNascimento(String nascimento) {
		lb_nascimento.setText(nascimento);
	}
	
	public void setSexo(String sexo) {
		lb_sexo.setText(sexo);
	}
	
	public void setRG(String rg) {
		lb_rg.setText(rg);
	}
	
	public void setCPF(String cpf) {
		lb_cpf.setText(cpf);
	}
	
	public void setRua(String rua) {
		lb_rua.setText(rua);
	}
	
	public void setNumero(String numero) {
		lb_numero.setText(numero);
	}
	
	public void setBairro(String bairro) {
		lb_bairro.setText(bairro);
	}
}