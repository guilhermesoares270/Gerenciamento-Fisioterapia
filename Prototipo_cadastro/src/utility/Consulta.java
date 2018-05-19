package utility;

import java.sql.*;

public class Consulta {
	
	/*
	private Paciente paciente1 = null;
	private Paciente paciente2 = null;
	private Paciente paciente3 = null;
	private Fisioterapeuta fisioterapeuta = null;
	*/
	
	private long paciente1;
	private long paciente2;
	private long paciente3;
	private long fisioterapeuta;
	private String data;
	private String horario_inicio;
	
	

	public Consulta(long paciente1, long paciente2, long paciente3, long fisioterapeuta, String data,
			String horario_inicio) {
		this.paciente1 = paciente1;
		this.paciente2 = paciente2;
		this.paciente3 = paciente3;
		this.fisioterapeuta = fisioterapeuta;
		this.data = data;
		this.horario_inicio = horario_inicio;
	}

	public boolean checkData(Pessoa pessoa) {
		boolean res = false;
		try(Connection conn = DriverManager.getConnection(sqlite_connect.JDBC + "fisioterapiaSUS.db")){
			
			String pesquisar = "SELECT nome FROM dados_pessoais WHERE cpf = " + pessoa.getCpf();
			
			PreparedStatement stmt = conn.prepareStatement(pesquisar);
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				System.out.println("Não foi encontrado");
			}else {
				res = true;
			}
			
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return res;
	}
	
	//0 paciente 
	//1 Fisioterapia
	public int typeOfClass(Pessoa pessoa) {
		
		if(pessoa.getClass() == Paciente.class) {
			return 0;
		}else {
			return 1;
		}
	}	
	
	
	
	public long getPaciente1() {
		return paciente1;
	}
	public void setPaciente1(long paciente1) {
		this.paciente1 = paciente1;
	}
	public long getPaciente2() {
		return paciente2;
	}
	public void setPaciente2(long paciente2) {
		this.paciente2 = paciente2;
	}
	public long getPaciente3() {
		return paciente3;
	}
	public void setPaciente3(long paciente3) {
		this.paciente3 = paciente3;
	}
	public long getFisioterapeuta() {
		return fisioterapeuta;
	}
	public void setFisioterapeuta(long fisioterapeuta) {
		this.fisioterapeuta = fisioterapeuta;
	}
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHorario_inicio() {
		return horario_inicio;
	}

	public void setHorario_inicio(String horario_inicio) {
		this.horario_inicio = horario_inicio;
	}
}
