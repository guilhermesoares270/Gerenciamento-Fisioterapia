package utility;

/*
 * To do
 * Static funcions
 * one function to call the validade 1 and 2
 */

public class cpf_checker {
	
	private String cpf;
	//private int j;
	//private int  k;
	
	private static char[] cpf_part;
	
	//Construtor
	//Vai chamar as funções para validar os dois numeros
	public cpf_checker(String  cpf){
		//setCpf(cpf);
		//separate_cpf("97849979834");
		/*
		separate_cpf(cpf);
		j = validate_first();
		k = validate_second();
		
		System.out.println("Valor de j = " + j);
		System.out.println("Valor de k = " + k);
		System.out.println("Valor de cpf = " + cpf_part[(cpf_part.length - 2)]);
		System.out.println("Valor de cpf = " + cpf_part[(cpf_part.length - 1)]);
		if(j == Character.getNumericValue(cpf_part[(cpf_part.length - 2)]) && k == Character.getNumericValue(cpf_part[(cpf_part.length - 1)])) {
			
			System.out.println("O cpf é valido");
		}else {
			System.out.println("cpf inválido");
		}
		*/
		
		//valid();
	}
	
	public static boolean valid(String cpf) {
		boolean valid = false;
		
		int j;
		int k;
		
		separate_cpf(cpf);
		j = validate_first();
		k = validate_second();
		
		System.out.println("Valor de j = " + j);
		System.out.println("Valor de k = " + k);
		System.out.println("Valor de cpf = " + cpf_part[(cpf_part.length - 2)]);
		System.out.println("Valor de cpf = " + cpf_part[(cpf_part.length - 1)]);
		if(j == Character.getNumericValue(cpf_part[(cpf_part.length - 2)]) && k == Character.getNumericValue(cpf_part[(cpf_part.length - 1)])) {
			valid = true;
			System.out.println("O cpf é valido");
		}else {
			System.out.println("cpf inválido");
		}
		return valid;
	}
	
	//Separa o cpf em digitos separados e quarda num array de chars
	private static void separate_cpf(String cpf) {
		
		cpf_part = String.valueOf(cpf).toCharArray();
		
		for(int i = 11; i >= 1; i--) {
			System.out.println(cpf_part[i - 1]);
		}
	}
	
	//Multiplica os 9 primeiros nuemros do cpf começando o primeiro por 10
	//e a cada iteração diminui esse número
	//depois divide por 11 e 
	private static int validate_first() {
		int res = 0;
		int cur_mult = 10;
		
		for(int i = 0; i <= 8; i++) {
			res += Character.getNumericValue(cpf_part[i]) * cur_mult;
			cur_mult--;
		}
		
		res = res % 11;
		
		if(res == 0 || res == 1) {
			res = 0;
		}else if(res >= 2 && res <= 10) {
			res = 11 - res;
		}
		System.out.println("res = " + res);
		return res;
	}
	
	private static int validate_second() {
		int res = 0;
		int cur_mult = 11;
		
		for(int i = 0; i <= 9; i++) {
			res += Character.getNumericValue(cpf_part[i]) * cur_mult;
			System.out.println("Res = " + res + " cur_mult: " + cur_mult);
			cur_mult--;
		}
		res = res % 11;
		
		if(res == 0 || res == 1) {
			res = 0;
		}else if(res >= 2 && res <= 10) {
			res = 11 - res;
		}
		System.out.println("res = " + res);
		return res;
	}
	
}
