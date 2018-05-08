package utility;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//Deve ser chamado o construtor com no mínimo os valores padrão
public class Paciente extends Pessoa{
	
	Logger logger = Logger.getLogger(Paciente.class.getName());
	
	//usado apenas para ler, nunca é gravado no db
	private int id;
	
	private long num_sus; 
	private String sintomas;
	private String medicacao;
	
	public Paciente(String nome, String nascimento, String sexo, String email, long telefone, long celular, long rg,
			long cpf, String rua, long numero, String bairro, String complemento, long cep, String uf,
			long num_sus, String sintomas, String medicacao) {
		super(nome, nascimento, sexo, email, telefone, celular, rg, cpf, rua, numero, bairro, complemento, cep, uf);

		this.setNum_sus(num_sus);
		this.setSintomas(sintomas);
		this.setMedicacao(medicacao);
	}
	
	//Construtor com apenas os valores obrigatórios 
	public Paciente(String nome, String email, long num_sus, long cpf) {
		super();
		this.setNome(nome);
		this.setEmail(email);
		this.setNum_sus(num_sus);
		this.setCpf(cpf);
	}
	
	@Override 
	public void printData() {
		super.printData();
		System.out.println(num_sus);
		System.out.println(sintomas);
		System.out.println(medicacao);	
	}
	
	@Override
	public void printEntry() {
		sqlite_connect sq = new sqlite_connect();
		sq.select_from("fisioterapiaSUS.db", "paciente", "*");
	}
	
	@Override
	public void saveData() {
		
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:" + "fisioterapiaSUS.db")){
			
			@SuppressWarnings("unused")
			ResultSet rs = null;
			//Importante para a transction funcionar
			conn.setAutoCommit(false);
			
			
			String endereco = "INSERT INTO endereco (rua, numero, bairro, complemento, cep, uf) "
					+ "VALUES (?, ?, ?, ?, ?, ?);";
			String contato = "INSERT INTO contato (email, telefone, celular) "
					+ "VALUES (?, ?, ?);";
			String dados_pessoais = "INSERT INTO dados_pessoais (nome, nascimento, sexo, rg, cpf, endereco_id, contato_id) "
					+ "VALUES (?, ?, ?, ?, ?, (SELECT MAX(endereco_id) FROM endereco), (SELECT MAX(contato_id) FROM contato));";
			String paciente = "INSERT INTO paciente (pessoa_id, num_sus, sintomas, medicacao) "
					+ "VALUES ((SELECT cpf FROM dados_pessoais WHERE dados_pessoais_id = (SELECT MAX(dados_pessoais_id) FROM dados_pessoais)), " 
					+ "?, ?, ?);";
			
			//Insert endereco
			PreparedStatement pst1 = conn.prepareStatement(endereco, Statement.RETURN_GENERATED_KEYS);
			pst1.setString(1, rua);
			pst1.setLong(2, numero);
			pst1.setString(3, bairro);
			pst1.setString(4, complemento);
			pst1.setLong(5, cep);
			pst1.setString(6, uf);
			
			checkInsert(conn, pst1);
			
			//Insert contato
			PreparedStatement pst2 = conn.prepareStatement(contato, Statement.RETURN_GENERATED_KEYS);
			pst2.setString(1, email);
			pst2.setLong(2, telefone);
			pst2.setLong(3, celular);
			
			checkInsert(conn, pst2);
			
			//Insert dados_pessoais
			PreparedStatement pst3 = conn.prepareStatement(dados_pessoais, Statement.RETURN_GENERATED_KEYS);
			pst3.setString(1, nome);
			pst3.setString(2, nascimento);
			pst3.setString(3, sexo);
			pst3.setLong(4, rg);
			pst3.setLong(5, cpf);
			
			checkInsert(conn, pst3);
			
			//Insert paciente
			PreparedStatement pst4 = conn.prepareStatement(paciente, Statement.RETURN_GENERATED_KEYS);
			pst4.setLong(1, num_sus);
			pst4.setString(2, sintomas);
			pst4.setString(3, medicacao);
			
			checkInsert(conn, pst4);
			
			conn.commit();
			
		}catch(SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMedicacao() {
		return medicacao;
	}
	
	public void setMedicacao(String medicacao) {
		this.medicacao = medicacao;
	}
	
	public String getSintomas() {
		return sintomas;
	}
	
	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}
	
	public long getNum_sus() {
		return num_sus;
	}
	
	public void setNum_sus(long num_sus) {
		this.num_sus = num_sus;
	}
}
