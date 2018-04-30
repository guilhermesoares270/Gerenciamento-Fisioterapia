package view;

import utility.Paciente;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/*
 * LEMBRA DE COLCOAR A ANOTAÇÃO @FXML
 */
public class cliente_control {
	
	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public static DateTimeFormatter getformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	@FXML
	private Button bt_cadastrar;
	
	@FXML
	private TextField tf_name;
	
	@FXML
	private TextField tf_cpf;
	
	@FXML
	private ComboBox<String> cb_uf;
	
	@FXML
	private RadioButton rb_masculino;
	@FXML
	private RadioButton rb_feminino;
	
	@FXML
	public ToggleGroup gender;
	
	@FXML
	private DatePicker  dp_nascimento;
	
	@FXML
	private TextField tf_email;
	
	@FXML
	private TextField tf_telefone;
	
	@FXML
	private TextField tf_celular;
	
	@FXML
	private TextField tf_rua;
	
	@FXML
	private TextField tf_numero;
	
	@FXML
	private TextField tf_bairro;
	
	@FXML
	private TextField tf_complemento;
	
	@FXML
	private TextField tf_cep;
	
	@FXML
	private TextField tf_sus;
	
	@FXML
	private TextField tf_sintomas;
	
	@FXML
	private TextField tf_medicacao;
	 
	@FXML
	private TextField tf_rg;
	
	//Constructor
	public cliente_control() {
		
		
	}
	
	/*
	 * pega os dados e da interface e manda para o objeto paciente
	 */
	public void getDados() {
		
		/*
		 * Pega os dados básicos para criar o paciente
		 * Instancia o objeto da classe Paciente com os valore obrigatórios
		 */
		
		Paciente pc = new Paciente(
				tf_name.getText(),
				tf_email.getText(),
				Long.parseLong(tf_sus.getText()),
				Long.parseLong(tf_cpf.getText())
				);
		
		
		pc.setNascimento(setDate());
		
		if(rb_masculino.isSelected()) {
			pc.setSexo("Masculino");
		}else {
			pc.setSexo("Feminino");
		}
		
		pc.setTelefone(Long.parseLong(tf_telefone.getText()));
		
		pc.setCelular(Long.parseLong(tf_celular.getText()));
		
		pc.setRua(tf_rua.getText());
		
		pc.setNumero(Long.parseLong(tf_numero.getText()));
		
		pc.setBairro(tf_bairro.getText());
		
		pc.setComplemento(tf_complemento.getText());
		
		pc.setCep(Long.parseLong(tf_cep.getText()));
		
		pc.setUf(cb_uf.getValue());
		
		pc.setSintomas(tf_sintomas.getText());
		
		pc.setMedicacao(tf_medicacao.getText());
		
		pc.setRg(Long.parseLong(tf_rg.getText()));
		
		//imprime os dados coletados
		pc.printData();
		//SAVE
		pc.saveData(); 
	}
	
	public String setDate() {
		String retorno = null;
		try{
			//Revisar
			if(dp_nascimento.getEditor().getText() != null) {
				System.out.println("Iniciando verificação data");
				LocalDate ld = dp_nascimento.getValue();
				if(ld == null) {
					retorno = getformatter.format(LocalDate.now());
					//date = getformatter.format(ld.now());
					
					//date = getformatter.format(LocalDate.now());
					//pc.setNascimento(date);
				}else {
					retorno = getformatter.format(ld);
					System.out.println("Data: " + getformatter.format(ld));
					//date = getformatter.format(ld);
					//pc.setNascimento(ld.toString());
				}
				//System.out.println("Data: " + date);
			}else {
				System.out.println("data vazia");
			}
			
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
		return retorno;
	}
	
	public boolean obrigatoryInfo() {
		
		boolean retorno = true;
		
		if(tf_name.getText().isEmpty()) {
			tf_name.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:#eb4d4b;");
			//tf_name.requestFocus();
			retorno = false;
			
		}if(tf_email.getText().isEmpty()) {
			tf_email.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:#eb4d4b;");
			//tf_email.requestFocus();
			retorno = false;
			
		}if(tf_sus.getText().isEmpty()) {
			tf_sus.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:#eb4d4b;");
			//tf_sus.requestFocus();
			retorno = false;
			
		}if(tf_cpf.getText().isEmpty()) {
			tf_cpf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:#eb4d4b;");
			//tf_cpf.requestFocus();
			retorno = false;
		}
		System.out.println("Valor de retorno = " + retorno);
		return retorno;
	}
	
	public boolean checkCPF() {
		
		boolean retorno = true;
		//Verifica o CPF
		if(tf_cpf.getText().length() != 11) {
			//tf_cpf.setText("Formato inválido");
			tf_cpf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:#eb4d4b;");
			tf_cpf.requestFocus();
			retorno = false;
		}else {
			if(utility.cpf_checker.valid(tf_cpf.getText())) {
				//tf_cpf.setText("Is valid");
				tf_cpf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color: #039ED3;");//
				tf_cpf.requestFocus();//
			}else {
				//tf_cpf.setText("Invalid");
				tf_cpf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:#eb4d4b;");
				tf_cpf.requestFocus();
				retorno = false;
			}
		}
		return retorno;
	}
	
	@FXML
	private void initialize() {
				
		//Inicia a data com o dia atual
		dp_nascimento.getEditor().setText(getformatter.format(LocalDate.now()));
		
		cb_uf.setPromptText("Estado");
		//cb_uf.getValue();
		cb_uf.getItems().addAll(
				"AC",
				"AL",
				"AM",
				"BA",
				"CE",
				"DF",
				"ES",
				"GO",
				"MA",
				"MT",
				"MS",
				"MG",
				"PA",
				"PB",
				"PR",
				"PE",
				"PI",
				"RJ",
				"RN",
				"RS",
				"RO",
				"RR",
				"SC",
				"SP",
				"SE",
				"TO"
			);
		cb_uf.getSelectionModel().select(0);
		
		//Cadastrar
		bt_cadastrar.setOnAction((event) -> {
			
			//Verifica se as informações básicas foram preenchidas
			if(obrigatoryInfo()) {
				System.out.println("As informações básicas estão preenchidas");
				boolean allFieldsCorrect = true;
				
				
				//allFieldsCorrect = checkCPF();
				allFieldsCorrect = true;
				
				if(allFieldsCorrect == true) {
					System.out.println("Iniciando getDados()");
					getDados();
				}else {
					System.out.println("Existem erros no formulário");
				}
			}
		});
	}
}
