import java.util.LinkedList;

class SubNodoLevenshtein
{
	double valor;
	String padre;
	
	SubNodoLevenshtein(double num)
	{
		this.valor = num;
	}

	public double getValor() 
	{
		return valor;
	}

	public void setValor(double valor) 
	{
		this.valor = valor;
	}

	public String getPadre() 
	{
		return padre;
	}

	public void setPadre(String padre) 
	{
		this.padre = padre;
	}
};

class NodoLevenshtein 
{
	SubNodoLevenshtein[][] estructura;
	
	public NodoLevenshtein(double num)
	{
		this.estructura = new SubNodoLevenshtein[2][2];
		
		this.estructura[0][0] = new SubNodoLevenshtein(num);
		this.estructura[0][1] = new SubNodoLevenshtein(num);
		this.estructura[1][0] = new SubNodoLevenshtein(num);
		this.estructura[1][1] = new SubNodoLevenshtein(num);
	}

	public NodoLevenshtein()
	{
		this.estructura = new SubNodoLevenshtein[2][2];
		
		this.estructura[0][0] = new SubNodoLevenshtein(0);
		this.estructura[0][1] = new SubNodoLevenshtein(0);
		this.estructura[1][0] = new SubNodoLevenshtein(0);
		this.estructura[1][1] = new SubNodoLevenshtein(0);
	}
	
	public SubNodoLevenshtein get(int fila, int columna)
	{
		if (fila < 0 || fila >= 2 || columna < 0 || columna >= 2)
		{
			return null;
		}
		
		return this.estructura[fila][columna];
	}
	
	public void set(int fila, int columna, double val)
	{
		if (fila >= 0 && fila < 2 && columna >= 0 && columna < 2)
		{
			this.estructura[fila][columna].setValor(val);
		}
	}
	
	public void calcular11()
	{
		double a = this.estructura[0][0].getValor();
		double b = this.estructura[0][1].getValor();
		double c = this.estructura[1][0].getValor();
		
		if (a <= b && a <= c)
		{
			this.estructura[1][1].setValor(a);
			this.estructura[1][1].setPadre("T,D");
		}
		else if (b <= a && b <= c)
		{
			this.estructura[1][1].setValor(b);
			this.estructura[1][1].setPadre("T,A");
		}
		else
		{
			this.estructura[1][1].setValor(c);
			this.estructura[1][1].setPadre("T,I");
		}	
	}
	
	public String toString()
	{
		String retorno = "";
		
		retorno = "___\n"; 
		retorno += "|" + this.estructura[0][0].getValor() + "|" + this.estructura[0][1].getValor() + "|\n";
		retorno += "|" + this.estructura[1][0].getValor() + "|" + this.estructura[1][1].getValor() + "|\n";
		retorno += "___\n"; 
		
		return retorno;
	}
}

public class CalculadorLevenshtein 
{
	private String palabraL;
	private String palabraA;
	private NodoLevenshtein[][] tabla;
	
	public double calcular(String palabraL, String palabraA)
	{
		String palabraLado   = palabraL.toLowerCase() ;
		String palabraArriba = palabraA.toLowerCase();
		
		this.palabraL = palabraLado;
		this.palabraA = palabraArriba;
		
		//Creamos la tabla principal
		tabla = new NodoLevenshtein[palabraLado.length()+1][palabraArriba.length()+1];  
		
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
				System.out.println(j + " " + palabraLado.charAt(j-1) + " " + i + " " + palabraArriba.charAt(i-1));
				
				NodoLevenshtein nodo = new NodoLevenshtein(0);
				
				double valorDiagonal  = tabla[j-1][i-1].get(1, 1).getValor();
				double valorIzquierdo = tabla[j-1][i].get(1, 1).getValor();
				double valorArriba    = tabla[j][i-1].get(1, 1).getValor();
				
				nodo.set(0, 1, valorArriba+1);
				nodo.set(1, 0, valorIzquierdo+1);

				nodo.get(0, 1).setPadre("F,A");
				nodo.get(1, 0).setPadre("F,I");
				nodo.get(0, 0).setPadre("F,D");

				if (palabraArriba.charAt(i-1) == palabraLado.charAt(j-1))
				{
					nodo.set(0, 0, valorDiagonal);
				}
				else
				{
					nodo.set(0, 0, valorDiagonal+1);
				}
								
				nodo.calcular11();
				
				System.out.println(nodo);
				System.out.println(nodo.get(0, 0).getPadre());
				System.out.println(nodo.get(0, 1).getPadre());
				System.out.println(nodo.get(1, 0).getPadre());
				System.out.println(nodo.get(1, 1).getPadre());
				
				tabla[j][i] = nodo;
			} 
		}
		
		System.out.println(getRecorridoInverso());
				
		//Retornamos el ultimo valor
		return tabla[palabraLado.length()][palabraArriba.length()].get(1, 1).getValor();
	}

	public String getRecorridoInverso()
	{
		boolean terminado = false;
		SubNodoLevenshtein nodoActual = this.tabla[this.palabraL.length()][this.palabraA.length()].get(1, 1);
		String cadena = "";
		int i = this.palabraL.length()-1;
		int j = this.palabraA.length()-1;
		
		while (!terminado)
		{
			System.out.println(cadena + " [" + nodoActual.getPadre() + "] (" + i + "," + j+ ")");

			cadena += nodoActual.getValor() + " ";
			
			if (nodoActual == null || nodoActual.getPadre().equals(""))
			{
				terminado = true;
			}
			else
			{				
				String [] padreTrozos = nodoActual.getPadre().split(",");

				if (padreTrozos[0].equals("T"))
				{
					if (padreTrozos[1].equals("D"))
					{
						nodoActual = this.tabla[i][j].get(0, 0);
					}
					else if (padreTrozos[1].equals("I"))
					{
						nodoActual = this.tabla[i][j].get(1, 0);
					}
					else
					{
						nodoActual = this.tabla[i][j].get(0, 1);
					}
				}
				else
				{
					if (padreTrozos[1].equals("D"))
					{
						i--;
						j--;
						nodoActual = this.tabla[i][j].get(1, 1);
					}
					else if (padreTrozos[1].equals("I"))
					{
						i--;
						nodoActual = this.tabla[i][j].get(1, 1);
					}
					else
					{
						j--;
						nodoActual = this.tabla[i][j].get(1, 1);
					}
				}
			}
		}
		
		return cadena;
	}

	public static String masParecido(String input, LinkedList<String> posibles)
	{
		String menor = "";
		double menorPeso = 10000000;
		
		for (int i = 0; i < posibles.size(); i++)
		{
			String actual = posibles.get(i);
			CalculadorLevenshtein calculador = new CalculadorLevenshtein();
			double distancia = calculador.calcular(input, actual);
			
			System.out.println(distancia);
			
			if (distancia < menorPeso)
			{
				menorPeso = distancia;
				menor = actual;
			}
			
		}
		
		return menor;
	}
}
