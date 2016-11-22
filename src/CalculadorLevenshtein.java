import java.util.LinkedList;

class NodoLevenshtein 
{
	int[][] estructura;
	
	public NodoLevenshtein(int num)
	{
		this.estructura = new int[2][2];
		
		this.estructura[0][0] = num;
		this.estructura[0][1] = num;
		this.estructura[1][0] = num;
		this.estructura[1][1] = num;
	}

	public NodoLevenshtein()
	{
		this.estructura = new int[2][2];
		
		this.estructura[0][0] = 0;
		this.estructura[0][1] = 0;
		this.estructura[1][0] = 0;
		this.estructura[1][1] = 0;
	}
	
	public int get(int fila, int columna)
	{
		if (fila < 0 || fila >= 2 || columna < 0 || columna >= 2)
		{
			return -1;
		}
		
		return this.estructura[fila][columna];
	}
	
	public void set(int fila, int columna, int val)
	{
		if (fila >= 0 && fila < 2 && columna >= 0 && columna < 2)
		{
			this.estructura[fila][columna] = val;
		}
	}
	
	public void calcular11()
	{
		int a = this.estructura[0][0];
		int b = this.estructura[0][1];
		int c = this.estructura[1][0];
		
		if (a <= b && a <= c)
		{
			this.estructura[1][1] = a;
		}
		else if (b <= a && b <= c)
		{
			this.estructura[1][1] = b;
		}
		else
		{
			this.estructura[1][1] = c;
		}	
	}
	
	public String toString()
	{
		String retorno = "";
		
		retorno = "___\n"; 
		retorno += "|" + this.estructura[0][0] + "|" + this.estructura[0][1] + "|\n";
		retorno += "|" + this.estructura[1][0] + "|" + this.estructura[1][1] + "|\n";
		retorno += "___\n"; 
		
		return retorno;
	}
}

public class CalculadorLevenshtein 
{
	public static int distancia(String palabraL, String palabraA)
	{
		String palabraLado   = palabraL.toLowerCase() ;
		String palabraArriba = palabraA.toLowerCase();
		
		//Creamos la tabla principal
		NodoLevenshtein[][] tabla = new NodoLevenshtein[palabraLado.length()+1][palabraArriba.length()+1];  
		
		//Llenamos los datos de la primera tabla
		for (int i = 0; i < palabraArriba.length()+1; i++)
		{
			tabla[0][i] = new NodoLevenshtein(i);
			//System.out.println(tabla[0][i].toString());
		}
		
		for (int i = 0; i < palabraLado.length()+1; i++)
		{
			tabla[i][0] = new NodoLevenshtein(i);
			//System.out.println(tabla[i][0]);
		}
		
		//Llenamos la estructura de la tabla
		for (int i = 1; i <= palabraArriba.length(); i++)
		{
			for (int j = 1; j <= palabraLado.length(); j++)
			{
				//System.out.println(j + " " + palabraLado.charAt(j-1) + " " + i + " " + palabraArriba.charAt(i-1));
				
				NodoLevenshtein nodo = new NodoLevenshtein(0);
				
				int valorDiagonal  = tabla[j-1][i-1].get(1, 1);
				int valorIzquierdo = tabla[j-1][i].get(1, 1);
				int valorArriba    = tabla[j][i-1].get(1, 1);
				
				nodo.set(0, 1, valorArriba+1);
				nodo.set(1, 0, valorIzquierdo+1);
				
				if (palabraArriba.charAt(i-1) == palabraLado.charAt(j-1))
				{
					nodo.set(0, 0, valorDiagonal);
				}
				else
				{
					nodo.set(0, 0, valorDiagonal+1);
				}
								
				nodo.calcular11();
				
				//System.out.println(nodo);
				
				tabla[j][i] = nodo;
			}
		}
		
		//Retornamos el ultimo valor
		return tabla[palabraLado.length()][palabraArriba.length()].get(1, 1);
	}
	
	public static String masParecido(String input, LinkedList<String> posibles)
	{
		String menor = "";
		int menorPeso = 100;
		
		for (int i = 0; i < posibles.size(); i++)
		{
			String actual = posibles.get(i);
			int distancia = CalculadorLevenshtein.distancia(input, actual);
			
			if (distancia < menorPeso)
			{
				menorPeso = distancia;
				menor = actual;
			}
			
		}
		
		return menor;
	}
}
