package utility;

import javafx.beans.property.StringProperty;

public class Paciente {
	
	private StringProperty nome;
	private StringProperty nascimento;
	private StringProperty sexo;
	private StringProperty email;
	private StringProperty telefone;
	private StringProperty celular;
	private StringProperty rua;
	private StringProperty numero;
	private StringProperty bairro;
	private StringProperty complemento;
	private StringProperty cep;
	private StringProperty uf;
	private StringProperty num_sus; 
	private StringProperty sintomas;
	private StringProperty medicacao;
	private StringProperty rg;
	private StringProperty cpf;
	
	public Paciente() {
		
	}
	
	//Construtor com apenas os valores obrigatórios 
	public Paciente(StringProperty nome, StringProperty cep, StringProperty email, StringProperty num_sus, StringProperty cpf) {
		this.setNome(nome);
		this.setCep(cep);
		this.setEmail(email);
		this.setNum_sus(num_sus);
		this.setCpf(cpf);
	}
	
	public Paciente(StringProperty nome, StringProperty nascimento, StringProperty sexo, StringProperty email,
			StringProperty telefone, StringProperty celular, StringProperty rua, StringProperty numero,
			StringProperty bairro, StringProperty complemento, StringProperty cep, StringProperty uf,
			StringProperty num_sus, StringProperty sintomas, StringProperty medicacao, StringProperty rg,
			StringProperty cpf) {
		//super();
		this.nome = nome;
		this.nascimento = nascimento;
		this.sexo = sexo;
		this.email = email;
		this.telefone = telefone;
		this.celular = celular;
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.complemento = complemento;
		this.cep = cep;
		this.uf = uf;
		this.num_sus = num_sus;
		this.sintomas = sintomas;
		this.medicacao = medicacao;
		this.rg = rg;
		this.cpf = cpf;
	}
	
	//Visualiza os dados do cliente serve para verificar se os dados estão corretos
	public void printData() {
		System.out.println(nome);
		System.out.println(nascimento);
		System.out.println(sexo);
		System.out.println(email);
		System.out.println(telefone);
		System.out.println(celular);
		System.out.println(rua);
		System.out.println(numero);
		System.out.println(bairro);
		System.out.println(complemento);
		System.out.println(cep);
		System.out.println(uf);
		System.out.println(sintomas);
		System.out.println(medicacao);
		System.out.println(rg);
		System.out.println(cpf);
	}
	
	public void printEntry() {
		sqlite_connect sq = new sqlite_connect();
		sq.select_from("fisioterapiaSUS.db", "paciente", "*");
	}
	
	//id is created automaticamente
	public void createDB() {
		sqlite_connect sq = new sqlite_connect();
		
		sq.create_table("fisioterapiaSUS.db", "paciente", 
				"name text NOT NULL",
				"nascimento datetime",
				"sexo text NOT NULL",
				"email text",
				"telefone integer",
				"celular integer",
				"rua text",
				"numero integer",
				"bairro text",
				"complemento text",
				"cep integer",
				"uf text",
				"sus integer NOT NULL",
				"sintomas text",
				"meidicacao text",
				"rg integer NOT NULL",
				"cpf integer NOT NULL",
				"PRIMARY KEY (name, nascimento, sus, cpf)"
				);
				
	}
	
	//Salva os dados do cliente no SGBD
	public void saveData() {
		//Usar classe sqliteDB
		sqlite_connect sq = new sqlite_connect();
		sq.insert_table("fisioterapiaSUS.db", "paciente", 
				nome.get(),
				nascimento.get(),
				sexo.get(),
				email.get(),
				telefone.get(),
				celular.get(),
				rua.get(),
				numero.get(),
				bairro.get(),
				complemento.get(),
				cep.get(),
				uf.get(),
				num_sus.get(), 
				sintomas.get(),
				medicacao.get(),
				rg.get(),
				cpf.get()
				);
		
	}
	
	public StringProperty getNome() {
		return nome;
	}
	public void setNome(StringProperty nome) {
		this.nome = nome;
	}
	public StringProperty getNascimento() {
		return nascimento;
	}
	public void setNascimento(StringProperty nascimento) {
		this.nascimento = nascimento;
	}
	public StringProperty getSexo() {
		return sexo;
	}
	public void setSexo(StringProperty sexo) {
		this.sexo = sexo;
	}
	public StringProperty getEmail() {
		return email;
	}
	public void setEmail(StringProperty email) {
		this.email = email;
	}
	public StringProperty getTelefone() {
		return telefone;
	}
	public void setTelefone(StringProperty telefone) {
		this.telefone = telefone;
	}
	public StringProperty getCelular() {
		return celular;
	}
	public void setCelular(StringProperty celular) {
		this.celular = celular;
	}
	public StringProperty getRua() {
		return rua;
	}
	public void setRua(StringProperty rua) {
		this.rua = rua;
	}
	public StringProperty getNumero() {
		return numero;
	}
	public void setNumero(StringProperty numero) {
		this.numero = numero;
	}
	public StringProperty getBairro() {
		return bairro;
	}
	public void setBairro(StringProperty bairro) {
		this.bairro = bairro;
	}
	public StringProperty getComplemento() {
		return complemento;
	}
	
	public StringProperty getNum_sus() {
		return num_sus;
	}

	public void setNum_sus(StringProperty num_sus) {
		this.num_sus = num_sus;
	}
	
	public void setComplemento(StringProperty complemento) {
		this.complemento = complemento;
	}
	
	public StringProperty getUf() {
		return uf;
	}

	public void setUf(StringProperty uf) {
		this.uf = uf;
	}
	
	public StringProperty getCep() {
		return cep;
	}
	public void setCep(StringProperty cep) {
		this.cep = cep;
	}
	public StringProperty getSintomas() {
		return sintomas;
	}

	public void setSintomas(StringProperty sintomas) {
		this.sintomas = sintomas;
	}
	public StringProperty getMedicacao() {
		return medicacao;
	}
	public void setMedicacao(StringProperty medicacao) {
		this.medicacao = medicacao;
	}
	public StringProperty getRg() {
		return rg;
	}
	public void setRg(StringProperty rg) {
		this.rg = rg;
	}
	public StringProperty getCpf() {
		return cpf;
	}
	public void setCpf(StringProperty cpf) {
		this.cpf = cpf;
	}
	

}
