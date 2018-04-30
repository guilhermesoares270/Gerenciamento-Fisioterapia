package utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public abstract class Pessoa {
	
	Logger logger = Logger.getLogger(Pessoa.class.getName());
	
	protected String nome;
	protected String nascimento;
	protected String sexo;
	protected String email;
	protected long telefone;
	protected long celular;
	protected long rg;
	protected long cpf;
	
	protected String rua;
	protected long numero;
	protected String bairro;
	protected String complemento;
	protected long cep;
	protected String uf;
	
	public Pessoa(String nome, String nascimento, String sexo, String email, long telefone, long celular, long rg,
			long cpf, String rua, long numero, String bairro, String complemento, long cep, String uf) {
		this.setNome(nome);
		this.setNascimento(nascimento);
		this.setSexo(sexo);
		this.setEmail(email);
		this.setTelefone(telefone);
		this.setCelular(celular);
		this.setRg(rg);
		this.setCpf(cpf);
		this.setRua(rua);
		this.setNumero(numero);
		this.setBairro(bairro);
		this.setComplemento(complemento);
		this.setCep(cep);
		this.setUf(uf);
	}
	
	public Pessoa() {
		
	}
	
	public void entryData() {
		
	}
	
	public void printData() {
		System.out.println(nome);
		System.out.println(nascimento);
		System.out.println(sexo);
		System.out.println(email);
		System.out.println(telefone);
		System.out.println(celular);
		System.out.println(rg);
		System.out.println(cpf);
		
		System.out.println(rua);
		System.out.println(numero);
		System.out.println(bairro);
		System.out.println(complemento);
		System.out.println(cep);
		System.out.println(uf);
	}
	
	public void checkInsert(Connection conn, PreparedStatement pst) throws SQLException{
		
		int rowAffected = pst.executeUpdate();

		//rs = pst.getGeneratedKeys();
		
		/*
		id = 0;
		while(rs.next()) {
			id = rs.getInt(1);
		}
		*/
		
		if(rowAffected != 1) {
			conn.rollback();
		}
	}
	
	public abstract void printEntry();
	public abstract void saveData();
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNascimento() {
		return nascimento;
	}
	
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public long getTelefone() {
		return telefone;
	}
	
	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}
	
	public long getCelular() {
		return celular;
	}
	
	public void setCelular(long celular) {
		this.celular = celular;
	}
	
	public String getRua() {
		return rua;
	}
	
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	public long getNumero() {
		return numero;
	}
	
	public void setNumero(long numero) {
		this.numero = numero;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public long getCep() {
		return cep;
	}
	
	public void setCep(long cep) {
		this.cep = cep;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public long getRg() {
		return rg;
	}
	
	public void setRg(long rg) {
		this.rg = rg;
	}
	
	public long getCpf() {
		return cpf;
	}
	
	public void setCpf(long cpf) {
		this.cpf = cpf;
	}
}
