package utility;

import java.sql.*;

public class Consulta {
	
	private Paciente paciente1 = null;
	private Paciente paciente2 = null;
	private Paciente paciente3 = null;
	private Fisioterapeuta fisioterapeuta = null;
	
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
	
	public Paciente getPaciente1() {
		return paciente1;
	}

	public void setPaciente1(Paciente paciente1) {
		this.paciente1 = paciente1;
	}

	public Paciente getPaciente2() {
		return paciente2;
	}

	public void setPaciente2(Paciente paciente2) {
		this.paciente2 = paciente2;
	}

	public Paciente getPaciente3() {
		return paciente3;
	}

	public void setPaciente3(Paciente paciente3) {
		this.paciente3 = paciente3;
	}

	public Fisioterapeuta getFisioterapeuta() {
		return fisioterapeuta;
	}
	
	public void setFisioterapeuta(Fisioterapeuta fisio) {
		this.fisioterapeuta = fisio;
	}
}
