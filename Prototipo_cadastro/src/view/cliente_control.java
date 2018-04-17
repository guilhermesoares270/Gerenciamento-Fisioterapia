package view;

//import Custom_Components.TextFieldFormatter;///////

import utility.Paciente;

//import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//import com.sun.javafx.css.converters.StringConverter;

import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
//import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import javafx.scene.control.ToggleButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
//import javafx.scene.input.KeyEvent;;
/*



/*
 * LEMBRA DE COLCOAR A ANOTAÇÃO @FXML
 */
public class cliente_control {
	
	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	DateTimeFormatter getformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
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
	 * pega os dados e da interface e manda para a classe paciente
	 */
	public void getDados() {
		
		//Instancia o objeto da classe Paciente
		Paciente pc = new Paciente();
		
		pc.setNome(new SimpleStringProperty(tf_name.getText()));
		
		String date;
		try{
			
			//Revisar
			
			if(dp_nascimento.getEditor().getText() != null) {
				System.out.println("Iniciando verificação data");
				LocalDate ld = dp_nascimento.getValue();
				if(ld == null) {
					date = getformatter.format(LocalDate.now());
					pc.setNascimento(new SimpleStringProperty(date));///////
				}else {
					//date = ld.toString();
					date = getformatter.format(ld);
					pc.setNascimento(new SimpleStringProperty(ld.toString()));//////
				}

				System.out.println("Data: " + date);
			}else {
				System.out.println("data vazia");
			}
			
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
		if(rb_masculino.isSelected()) {
			pc.setSexo(new SimpleStringProperty("Masculino"));
		}else {
			pc.setSexo(new SimpleStringProperty("Feminino"));
		}
		
		pc.setEmail(new SimpleStringProperty(tf_email.getText()));
		//String email = tf_email.getText();
		
		pc.setTelefone(new SimpleStringProperty(tf_telefone.getText()));
		//String telefone = tf_telefone.getText();
		
		pc.setCelular(new SimpleStringProperty(tf_celular.getText()));
		//String celular = tf_celular.getText();//regex
		
		pc.setRua(new SimpleStringProperty(tf_rua.getText()));
		//String rua = tf_rua.getText();
		
		pc.setNumero(new SimpleStringProperty(tf_numero.getText()));
		//String numero = tf_numero.getText();
		
		pc.setBairro(new SimpleStringProperty(tf_bairro.getText()));
		//String bairro = tf_bairro.getText();
		
		pc.setComplemento(new SimpleStringProperty(tf_complemento.getText()));
		//String complemento = tf_complemento.getText();
		
		pc.setCep(new SimpleStringProperty(tf_cep.getText()));
		//String cep = tf_cep.getText();
		
		pc.setNum_sus(new SimpleStringProperty(tf_sus.getText()));
		
		pc.setUf(new SimpleStringProperty(cb_uf.getValue()));//////////
		
		pc.setSintomas(new SimpleStringProperty(tf_sintomas.getText()));
		//String sitomas = tf_sintomas.getText();
		
		pc.setMedicacao(new SimpleStringProperty(tf_medicacao.getText()));
		//String medicacao = tf_medicacao.getText();
		
		pc.setRg(new SimpleStringProperty(tf_rg.getText()));
		//String rg = tf_rg.getText();
		
		pc.setCpf(new SimpleStringProperty(tf_cpf.getText()));
		//String cpf = tf_cpf.getText();
		
		//imprime os dados coletados
		pc.printData();
		//SAVE
		//pc.createDB();
		pc.saveData();
		//pc.printEntry();
	}
	
	@FXML
	private void initialize() {
		
		//tf_celularOnKeyReleased();/////////
		/*
		tf_telefone.setOnKeyReleased((event) -> {
			TextFieldFormatter tff = new TextFieldFormatter();
			tff.setMask("(##)####-####");
			tff.setCaracteresValidos("0123456789");
			tff.setTf(tf_telefone);
			tff.formatter();
		});
		*/
				
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
		
		bt_cadastrar.setOnAction((event) -> {
			
			boolean allFieldsCorrect = false;//////
			
			System.out.println(tf_name.getText());
			
			//Verifica o CPF
			if(tf_cpf.getText().length() != 11) {
				//tf_cpf.setText("Formato inválido");
				tf_cpf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:#eb4d4b;");
				tf_cpf.requestFocus();
			}else {
				if(utility.cpf_checker.valid(tf_cpf.getText())) {
					//tf_cpf.setText("Is valid");
					tf_cpf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color: #039ED3;");//
					tf_cpf.requestFocus();//
				}else {
					//tf_cpf.setText("Invalid");
					tf_cpf.setStyle("-fx-faint-focus-color: transparent; -fx-focus-color:#eb4d4b;");
					tf_cpf.requestFocus();
				}
			}
			
			
			
			if(allFieldsCorrect == true) {
				getDados();
			}else {
				System.out.println("Existem erros no formulário");
			}
			getDados();
		});
	}
}
