import java.util.LinkedList;

public class MatrizReglas 
{
	private LinkedList<String> headerArriba;
	private LinkedList<String> headerLados;
	private String nombreAtributo;
	private double[][] matriz;
	
	MatrizReglas()
	{
		this.headerArriba = null;
		this.headerLados  = null;
		this.matriz = null;
		this.nombreAtributo = "";
	}
	
	public LinkedList<String> getHeaderArriba() 
	{
		return headerArriba;
	}

	public void setHeaderArriba(LinkedList<String> headerArriba) 
	{
		this.headerArriba = headerArriba;
	}

	public LinkedList<String> getHeaderLados() 
	{
		return headerLados;
	}

	public void setHeaderLados(LinkedList<String> headerLados) 
	{
		this.headerLados = headerLados;
	}

	public double[][] getMatriz() 
	{
		return matriz;
	}

	public void setMatriz(double[][] matriz) 
	{
		this.matriz = matriz;
	}

	public String getNombreAtributo() 
	{
		return nombreAtributo;
	}

	public void setNombreAtributo(String nombreAtributo) 
	{
		this.nombreAtributo = nombreAtributo;
	}

	void crearMatriz(DatosCSV datos, String atributo)
	{	
		int posClase = datos.getPosicionAtributo(datos.getNombreClase());
		int posAtrib = datos.getPosicionAtributo(atributo);
				
		if (posClase != -1 && posAtrib != -1)
		{
			LinkedList<String> regClase = datos.getDistintos(datos.getNombreClase());
			LinkedList<String> regAtrib = datos.getDistintos(atributo);
			
			double[][] matrix = new double[regAtrib.size()][regClase.size()];

			for (int i = 0; i < regAtrib.size(); i++)
			{
				for (int j = 0; j < regClase.size(); j++)
				{
					matrix[i][j] = 0;
				}
			}
			
			for (int i = 0; i < datos.getDatos().size(); i++)
			{
				RegistroCSV<NodoCSV> reg = datos.getDatos().get(i); 
				NodoCSV nodoClase = reg.getNodos().get(posClase);
				NodoCSV nodoAtrib = reg.getNodos().get(posAtrib);
				
				//System.out.println(nodoClase);
				//System.out.println(nodoAtrib);
				
				if (!nodoClase.isEliminado() && !nodoAtrib.isEliminado())
				{
					int posValClase = regClase.indexOf(nodoClase.getValor());
					int posValAtrib = regAtrib.indexOf(nodoAtrib.getValor());
					
					/*System.out.println(posValClase);
					System.out.println(posValAtrib);
					System.out.println(matrix[posValAtrib][posValClase]);
					System.out.println(matrix[posValAtrib][posValClase]+1);
					System.out.println();*/

					if (posValClase >= 0 && posValAtrib >= 0)
					{
						matrix[posValAtrib][posValClase] = matrix[posValAtrib][posValClase]+1;
					}
				}
			}
			
			this.headerArriba = regClase;
			this.headerLados  = regAtrib;
			this.matriz = matrix;
			this.nombreAtributo = atributo;
		}		
	}
	
	public LinkedList<String> getMayorAciertos()
	{
		LinkedList<String> cadenasAciertos = new LinkedList<String>();
		
		for (int i = 0; i < this.getHeaderLados().size(); i++)
		{
			double subj = 0;
			int subjP = 0;
			
			for (int j = 0; j < this.getHeaderArriba().size(); j++)
			{
				if (this.getMatriz()[i][j] >= subj)
				{
					subjP = j;
					subj = this.getMatriz()[i][j];
				}
			}
			
			String cadena = Double.toString(subj) + "," + 
							this.getHeaderLados().get(i) + ","+
							this.getHeaderArriba().get(subjP);
			
			cadenasAciertos.add(cadena);
		}
		
		return cadenasAciertos;
	}

	public LinkedList<String> getMenorAciertos()
	{
		LinkedList<String> cadenasAciertos = new LinkedList<String>();
		
		for (int i = 0; i < this.getHeaderLados().size(); i++)
		{
			double subj = 0;
			int subjP = 0;
			
			for (int j = 0; j < this.getHeaderArriba().size(); j++)
			{
				if (this.getMatriz()[i][j] <= subj)
				{
					subjP = j;
					subj = this.getMatriz()[i][j];
				}
			}
			
			String cadena = Double.toString(subj) + "," + 
							this.getHeaderLados().get(i) + ","+
							this.getHeaderArriba().get(subjP);
			
			cadenasAciertos.add(cadena);
		}
		
		return cadenasAciertos;
	}

	public double getAciertosArriba(int pos)
	{	
		double aciertos = -1;
		
		if (pos >= 0 && pos < this.getHeaderArriba().size())
		{
			aciertos = 0;
			
			for (int i = 0; i < this.getHeaderLados().size(); i++)
			{	
				aciertos += this.getMatriz()[i][pos];
			}
		}
				
		return aciertos;
	}

	public double getAciertosLados(int pos)
	{	
		double aciertos = -1;
		
		if (pos >= 0 && pos < this.getHeaderLados().size())
		{
			aciertos = 0;
			
			for (int i = 0; i < this.getHeaderArriba().size(); i++)
			{	
				aciertos += this.getMatriz()[pos][i];
			}
		}
				
		return aciertos;
	}
	
	public double getPromedio(int pos)
	{	
		LinkedList<Double> valores = new LinkedList<Double>();
		
		if (pos >= 0 && pos < this.getHeaderLados().size())
		{
			for (int i = 0; i < this.getHeaderLados().size(); i++)
			{
				for (int j = 0; j < this.getMatriz()[i][pos]; j++)
				{
					try 
					{
						double x = Double.parseDouble(this.getHeaderLados().get(i));
						valores.add(x);
					} 
					catch (NumberFormatException e) 
					{
						return -777777777;
					}
				}
			}
		}
		
		if (valores.isEmpty())
		{
			return -777777777;
		}
		else
		{
			double suma = 0;
			
			for (int i = 0; i < valores.size(); i++)
			{
				suma += valores.get(i);
			}
						
			return suma/valores.size();
		}
	}
	
	public double getDesviacionEstandar(int pos)
	{		
		LinkedList<Double> valores = new LinkedList<Double>();
		
		if (pos >= 0 && pos < this.getHeaderLados().size())
		{
			for (int i = 0; i < this.getHeaderLados().size(); i++)
			{
				for (int j = 0; j < this.getMatriz()[i][pos]; j++)
				{
					try 
					{
						double x = Double.parseDouble(this.getHeaderLados().get(i));
						valores.add(x);
					} 
					catch (NumberFormatException e) 
					{
						return -777777777;
					}
				}
			}
		}
		
		if (valores.isEmpty())
		{
			return -777777777;
		}
		else
		{
			double promedio = this.getPromedio(pos);
			double suma = 0;
			
			for (int i = 0; i < valores.size(); i++)
			{
				suma = suma + Math.pow((valores.get(i)-promedio),2);
			}
			
			suma = (suma/(valores.size()-1));
			suma = Math.sqrt(suma);
						
			return suma;
		}
	}
	
	public String toString()
	{
		String cad = "";
		
		for (int i = 0; i < this.headerLados.size()+1; i++)
		{
			for (int j = 0; j < this.headerArriba.size()+1; j++)
			{
				if (i == 0 && j == 0)
				{
					cad += this.getNombreAtributo();
				}
				else if (i == 0) //Imprimir a los headers de arriba
				{
					cad += this.headerArriba.get(j-1);
				}
				else if (j == 0) //Imprime a los header de al lado
				{
					cad += this.headerLados.get(i-1);
				}
				else
				{
					cad += Double.toString(this.matriz[i-1][j-1]);
				}
				
				cad += "|";
			}
			
			cad += "\n";
		}
		
		return cad;
	}
}
