package view;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import utility.*;

public class showPersonDetails_control {
	
	private List<Consulta> consulta_list = new ArrayList<Consulta>();
	@FXML private ListView<String> lv;
	
	@FXML private Button bt_excluir;
	@FXML private Button bt_alterar;
	
	@FXML private Label lb_nome;
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
	
	@FXML private TextArea ta_presencas;
	
	//Opcionais
	@FXML private Label lb_num_sus;
	@FXML private Label lb_sintomas;
	@FXML private Label lb_medicacao;
	
	@FXML private Label lb_crefito;
	
	
	public void excluirRegistro(String comando) {
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			Statement st = conn.prepareStatement(comando);
			st.executeUpdate(comando);
			
		}catch(SQLException e) {
			System.out.println("Houve um erro ao apagar o registro");
			e.getStackTrace();
		}
	}
	
	public void loadConsultas() {
		
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			String pesquisa = "SELECT * FROM consulta WHERE cpf = " + "'" + lb_cpf.getText() + "'"; 
			
			Statement st = conn.prepareStatement(pesquisa);
			ResultSet rs = st.executeQuery(pesquisa);
			
			if(!rs.isBeforeFirst()) {
				//Não tem nenhum registro
			}else {
				int i = 1;
				while(rs.next()) {
					consulta_list.add(new Consulta(
							rs.getLong("paciente1"),
							rs.getLong("paciente2"),
							rs.getLong("paciente3"),
							rs.getLong("fisioterapeuta"),
							rs.getString("data"),
							rs.getString("horario_inicio")
						));
					lv.getItems().add(Long.toString(consulta_list.get(i).getPaciente1()));
					i++;
				}
			}		
		}catch(SQLException e) {
			e.getSQLState();
			e.getStackTrace();
		}
	}
	
	@FXML
	private void initialize() {
		
		//ta_presencas.setText("TESTE");
		
		loadConsultas();

		//lb_nascimento.setText("ttttttttttt");
		
		bt_excluir.setOnAction((event) -> {
			
			if(lb_crefito.isDisabled() == true) {
				String excluir = "DELETE FROM paciente WHERE pessoa_id = " + getCpf() + " ;";
				String exc = "DELETE FROM dados_pessoais WHERE cpf = " + getCpf() + " ;";
				excluirRegistro(excluir);
				excluirRegistro(exc);
			}else {
				String excluir = "DELETE FROM fisioterapeuta WHERE pessoa_id = " + getCpf() + " ;";
				String exc = "DELETE FROM dados_pessoais WHERE cpf = " + getCpf() + " ;";
				excluirRegistro(excluir);
				excluirRegistro(exc);
			}			
		});
	}
	
	public void setTeste() {
		lb_nome.setText("Teeeste");
		//lb_nascimento.setText("17-03-1994");
	}
	
	public void setConsultasMarcadas() {
		System.out.println("----------------------- INICIANDO SETCONSULTASMARCADAS -----------------------");
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			String pesquisar = "SELECT * FROM presenca WHERE paciente = " + lb_cpf.getText() + ";";
			PreparedStatement stmt = conn.prepareStatement(pesquisar);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				System.out.println("Consultas Vazio");
				//vazio
			}else {
				while(rs.next()) {
					String dia = rs.getString("dia");
					String hora = rs.getString("hora");
					int presente = rs.getInt("presente");
					System.out.println("dia = " + dia + " hora = " + hora);

					ta_presencas.appendText("Dia: " + dia + " \n");
					ta_presencas.appendText("Hora: " + hora + " \n");
					
					String present = "falta";
					if(presente == 0) {
						present = "presente";
					}
					ta_presencas.appendText("Presente: " + present + " \n");
					ta_presencas.appendText("\n\n");
					System.out.println("" + dia + "\n" + hora + "\n" + presente + "\n\n");
				}
			}
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		
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
			
			//setConsultasMarcadas();///////////////////////
		}else if(pessoa.getClass() == Fisioterapeuta.class) {
			
			basicInfo(pessoa);
			lb_crefito.setText(Long.toString(((Fisioterapeuta) pessoa).getCrefito()));
			//setConsultasMarcadas();////////////////////////
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
	
	public String getCpf() {
		return lb_cpf.getText();
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