//Que Deus me perdoe por esse código horrível
//Pior que nível iniciante
//Café com leite total

public class Cript{

	private static String dicionario = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-+";

	public Cript(){}
	
	private int toBaseX(int dec, int base){
		int num = 0, count;
		for(count = 0; dec >= base; dec /= base, count++)
			num += (dec % base) * Math.pow(10, count);
		num += dec * Math.pow(10, count);
		return num;
	}

	private int baseXToDec(int num, int base){
		int dec = 0;
		for(int i = 0; num > 0; num /= 10, i++)
			dec += num % 10 * Math.pow(base, i);
		return dec;
	}

	private int reverseBin(int num){
		if(num < 0){
			System.out.println("reverseBin");
			System.exit(1);
		}
		int reversed = ((num % 10 == 0) ? 1 : 0) + 
						((num / 10 == 0) ? 1 : 0) * 10;
		return reversed;
	}

	public String criptografar(String texto){
		if(texto.length() < 1)
			return null;
		String codificado = "";
		boolean ctrl = false;
		int[][] matriz = new int[4][texto.length()];
		for(int i = 0, lin = 4; i < texto.length(); i++, ctrl = !ctrl){
			int aux = toBaseX(texto.charAt(i), 4);
			for(int j = 0; j < 4; j++, aux /= 10){
				if(ctrl)
					matriz[lin++][i] = reverseBin(toBaseX(aux % 10, 2));
				else
					matriz[--lin][i] = toBaseX(aux % 10, 2);
			}
		}
		int count = 0, dec = 10000;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < texto.length(); j++, dec = (((dec / 100) > 0) ? dec / 100 : 10000)){
				count += matriz[i][j] * dec;
				if(dec == 1){
					codificado += dicionario.charAt(baseXToDec(count, 2));
					count = 0;
				}
			}
		}
		if(dec != 10000){
			codificado += dicionario.charAt(baseXToDec(count, 2));
			codificado += '=';
			if(dec == 1)
				codificado += '=';
		}
		return codificado;
	}

	public String descriptografar(String texto){
		String decodificado = "";
		int tam = ((texto.indexOf('=') == -1) ? texto.length() : texto.indexOf('='));
		int tamMatriz = (tam * 3) / 4;
		String str = "";
		int[][] matriz = new int[4][tamMatriz];
		int lin = 0, col = 0;
		for(int i = 0; i < tam; i++){
			int bin = toBaseX((int)(dicionario.indexOf(texto.charAt(i))), 2);
			for(int j = 10000; j >= 1; bin %= j, j /= 100, col++){
				if(col == tamMatriz){
					col = 0;
					lin++;
					if(lin == 4)
						break;
				}
				if(col % 2 != 0)
					matriz[lin][col] = baseXToDec(reverseBin(bin / j), 2);
				else
					matriz[lin][col] = baseXToDec(bin / j, 2);
			}
			if(lin == 4)
				break;
		}
		boolean ctrl = false;
		lin = 4;
		for(int i = 0, aux = 0; i < tamMatriz; i++, ctrl = !ctrl, aux = 0){
			for(int j = 0; j < 4; j++){
				if(ctrl)
					aux += matriz[lin++][i] * Math.pow(10, j);
				else
					aux += matriz[--lin][i] * Math.pow(10, j);
			}
			decodificado += (char)baseXToDec(aux, 4);
		}
		return decodificado;
	}
}
