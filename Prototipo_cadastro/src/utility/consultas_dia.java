package utility;

public class consultas_dia {
	
	String tipo;
	String dia;
	String hora;
	
	public consultas_dia(String tipo, String dia, String hora) {
		setTipo(tipo);
		setDia(dia);
		setHora(hora);
	}

	public String getHora() {
		return hora;
	}
	
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public String getDia() {
		return dia;
	}
	
	public void setDia(String dia) {
		this.dia = dia;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
